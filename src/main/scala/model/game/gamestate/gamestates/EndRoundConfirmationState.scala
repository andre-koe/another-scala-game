package model.game.gamestate.gamestates

import io.circe.*
import io.circe.generic.auto.*
import model.core.board.IGameBoard
import model.core.board.boardutils.ICoordinate
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.gameobjects.purchasable.utils.{IOutput, Output}
import model.core.mechanics.MoveVector
import model.core.mechanics.fleets.IFleet
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.interfaces.{IRoundBasedConstructable, IUpkeep}
import model.core.utilities.{Capacity, IResourceHolder, ResourceHolder, SeqOperations}
import model.game.gamestate.{GameStateStringFormatter, IGameState, IGameStateManager}
import model.utils.Increaseable

case class EndRoundConfirmationState() extends IGameState:

  override def update(gsm: IGameStateManager): IGameStateManager = nextRound(gsm)

  private def nextRound(gsm: IGameStateManager): IGameStateManager =
    val newRound = gsm.round.next

    val playerSectors = gsm.gameMap.getPlayerSectors(gsm.currentPlayerValues.affiliation)

    val buildings = handleSeq[IBuilding](playerSectors.flatMap(_.constQuBuilding))
    val units = handleSeq[IUnit](playerSectors.flatMap(_.constQuUnits))
    val tech = handleSeq[ITechnology](gsm.currentPlayerValues.listOfTechnologiesCurrentlyResearched)

    val upkeepObj = buildings._2 ++ units._2 ++ playerSectors.flatMap(_.unitsInSector) ++ playerSectors.flatMap(_.buildingsInSector)
    val newUpkeep =
      returnAccumulated(upkeepObj, (x: IUpkeep) => x.upkeep, (x: IResourceHolder, y: IResourceHolder) => x.increase(y))
        .getOrElse(ResourceHolder())

    val buildingsCompleted = buildings._2 ++ playerSectors.flatMap(_.buildingsInSector)
    val output: IOutput =
      returnAccumulated(buildingsCompleted, (x: IBuilding) => x.output, (x: IOutput, y: IOutput) => x.increase(y))
        .getOrElse(Output())

    val income = output.rHolder
    val newBalance = gsm.currentPlayerValues.resourceHolder.increase(income).decrease(newUpkeep).get
    val techCompleted = tech._2 ++ gsm.currentPlayerValues.listOfTechnologies
    val techCurrentlyResearched = tech._1

    val newCurrentPlayerValues = gsm.currentPlayerValues.extCopy(
      resourceHolder = newBalance,
      listOfTechnologies = techCompleted.toVector,
      listOfTechnologiesCurrentlyResearched = techCurrentlyResearched.toVector,
      income = income,
      upkeep = newUpkeep
    )

    val indexOfPlayerValToUpdate = gsm.playerValues.indexWhere(_.affiliation == newCurrentPlayerValues.affiliation)
    val newPlayerValues = gsm.playerValues.updated(indexOfPlayerValToUpdate, newCurrentPlayerValues)


    val nGameMap = moveFleets(gsm.gameMap).update

    val updatedGSM = gsm.extCopy(gameMap = nGameMap, playerValues = newPlayerValues)
    val newMessage = GameStateStringFormatter(round = newRound, gsm = updatedGSM).overview()
    val finalGSM = updatedGSM.extCopy(message = newMessage,
      round = newRound,
      gameState = RunningState(),
      currentPlayerIndex = (gsm.currentPlayerIndex + 1) % gsm.playerValues.length)

    finalGSM


  private def moveFleet(fleet: IFleet, gameBoard: IGameBoard): IGameBoard =
    val moveVector = fleet.moveVector
    if (!moveVector.isMoving) {
      return gameBoard
    }

    val nextMoveVector = moveVector.nextStep
    val nFleetUpdated = fleet.extCopy(location = moveVector.start, moveVector = nextMoveVector)

    val updatedGameBoardAfterDeparture = handleDeparture(nFleetUpdated, gameBoard)
    val updatedGameBoardAfterArrival = handleArrival(nFleetUpdated, updatedGameBoardAfterDeparture)

    updatedGameBoardAfterArrival


  private def handleDeparture(fleet: IFleet, gameBoard: IGameBoard): IGameBoard =
    val currentSector = gameBoard.getSectorAtCoordinate(fleet.location).get
    val newUnitsInCurrentSector = currentSector.unitsInSector.filterNot(_.name == fleet.name)
    val updatedCurrentSector = returnUpdatedSector(currentSector, newUnitsInCurrentSector)

    gameBoard.updateSector(updatedCurrentSector)


  private def handleArrival(fleet: IFleet, gameBoard: IGameBoard): IGameBoard =
    val targetSector = gameBoard.getSectorAtCoordinate(fleet.moveVector.start).get
    val enemyFleets = targetSector.unitsInSector.filter(f => f.affiliation != fleet.affiliation)
    if enemyFleets.nonEmpty then handleBattle(fleet, enemyFleets, gameBoard) else handlePeacefulArrival(fleet, gameBoard)


  private def handleBattle(fleet: IFleet, enemyFleets: Vector[IFleet], gameBoard: IGameBoard): IGameBoard =
    if (battle(fleet, enemyFleets)) {
      val s = gameBoard.getSectorAtCoordinate(fleet.moveVector.target)
      s.get.unitsInSector.filterNot(x => enemyFleets.map(_.name).contains(x.name))
      gameBoard.toPlayerSector(fleet.moveVector.target, fleet)
    } else {
      val sectorToUpdate = gameBoard.getSectorAtCoordinate(fleet.moveVector.target).get
      val nFleets = sectorToUpdate.unitsInSector.filterNot(_.name.toLowerCase == fleet.name)
      gameBoard.updateSector(returnUpdatedSector(sectorToUpdate, nFleets))
    }

  private def handlePeacefulArrival(fleet: IFleet, gameBoard: IGameBoard): IGameBoard =
    val targetSector = gameBoard.getSectorAtCoordinate(fleet.moveVector.start).get
    gameBoard.toPlayerSector(targetSector.location, fleet)


  private def moveFleets(gameBoard: IGameBoard): IGameBoard =
    val fleets = gameBoard.data.flatMap(_.flatMap(_.unitsInSector))
    fleets.foldLeft(gameBoard) { (currentGameBoard, fleet) =>
      moveFleet(fleet, currentGameBoard)
    }


  private def battle(fleet: IFleet, value: Vector[IFleet]): Boolean =
    fleet.units.map(_.firepower).sum > value.flatMap(_.fleetComponents).map(_.firepower).sum


  private def returnUpdatedSector(sector: ISector, newUnits: Vector[IFleet]): ISector =
    sector match
      case pS: IPlayerSector => pS.extCopy(sector = pS.sector.cloneWith(unitsInSector = newUnits))
      case x: ISector => x.cloneWith(unitsInSector = newUnits)


  private def returnAccumulated[T, R](s: Seq[T], f: T => R, combiner: (R, R) => R): Option[R] =
    if s.length > 1 then Option(s.map(f).reduce(combiner)) else s.map(f).headOption


  private def handleSeq[A <: IRoundBasedConstructable](s: Seq[A]): (Seq[A], Seq[A]) =
    SeqOperations().partitionOnRounds0[A](s)