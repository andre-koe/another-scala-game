package model.game.gamestate.gamestates

import io.circe.*
import io.circe.generic.auto.*
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.gameobjects.purchasable.utils.{IOutput, Output}
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.interfaces.{IRoundBasedConstructable, IUpkeep}
import model.core.utilities.{Capacity, IResourceHolder, ResourceHolder, SeqOperations}
import model.game.gamestate.{GameStateStringFormatter, IGameState, IGameStateManager}
import model.utils.Increaseable

case class EndRoundConfirmationState() extends IGameState:

  override def update(gsm: IGameStateManager): IGameStateManager = nextRound(gsm)

  private def nextRound(gsm: IGameStateManager): IGameStateManager =
    val newRound = gsm.round.next

    val buildings = handleSeq[IBuilding](gsm.gameMap.getPlayerSectors.flatMap(_.constQuBuilding))
    val units = handleSeq[IUnit](gsm.gameMap.getPlayerSectors.flatMap(_.constQuUnits))
    val tech = handleSeq[ITechnology](gsm.playerValues.listOfTechnologiesCurrentlyResearched)

    val upkeepObj = buildings._2 ++ units._2 ++ gsm.gameMap.getPlayerSectors.flatMap(_.unitsInSector) ++ gsm.gameMap.getPlayerSectors.flatMap(_.buildingsInSector)
    val newUpkeep =
      returnAccumulated(upkeepObj, (x: IUpkeep) => x.upkeep, (x: IResourceHolder, y: IResourceHolder) => x.increase(y))
        .getOrElse(ResourceHolder())

    val buildingsCompleted = buildings._2 ++ gsm.gameMap.getPlayerSectors.flatMap(_.buildingsInSector)
    val output: IOutput =
      returnAccumulated(buildingsCompleted, (x: IBuilding) => x.output, (x: IOutput, y: IOutput) => x.increase(y))
        .getOrElse(Output())

    val income = output.rHolder
    val newBalance = gsm.playerValues.resourceHolder.increase(income).decrease(newUpkeep).get
    val techCompleted = tech._2 ++ gsm.playerValues.listOfTechnologies
    val techCurrentlyResearched = tech._1

    val newPlayerValues = gsm.playerValues.extCopy(
      resourceHolder = newBalance,
      listOfTechnologies = techCompleted.toVector,
      listOfTechnologiesCurrentlyResearched = techCurrentlyResearched.toVector,
      income = income,
      upkeep = newUpkeep
    )

    val nGameMap = gsm.gameMap.update;
    val updatedGSM = gsm.extCopy(gameMap = nGameMap, playerValues = newPlayerValues)
    val newMessage = GameStateStringFormatter(round = newRound, gsm = updatedGSM).overview()
    val finalGSM = updatedGSM.extCopy(message = newMessage, round = newRound, gameState = RunningState())

    finalGSM

  private def returnAccumulated[T, R](s: Seq[T], f: T => R, combiner: (R, R) => R): Option[R] =
    if s.length > 1 then Option(s.map(f).reduce(combiner)) else s.map(f).headOption

  private def handleSeq[A <: IRoundBasedConstructable](s: Seq[A]): (Seq[A], Seq[A]) =
    SeqOperations().partitionOnRounds0[A](s)
