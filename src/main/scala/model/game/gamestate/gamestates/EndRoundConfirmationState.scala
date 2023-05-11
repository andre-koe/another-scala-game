package model.game.gamestate.gamestates

import model.game.Capacity
import model.game.gamestate.{GameStateManager, GameStateStringFormatter, IGameState}
import model.game.purchasable.{IGameObject, IUpkeep}
import model.game.purchasable.building.IBuilding
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit
import model.game.purchasable.utils.Output
import model.game.resources.ResourceHolder
import model.utils.Increaseable

case class EndRoundConfirmationState() extends IGameState:

  override def update(gsm: GameStateManager): GameStateManager = nextRound(gsm)

  private def nextRound(gsm: GameStateManager): GameStateManager =
    val newRound = gsm.round.next

    val buildings = handleList[IBuilding](gsm.playerValues.listOfBuildingsUnderConstruction)
    val units = handleList[IUnit](gsm.playerValues.listOfUnitsUnderConstruction)
    val tech = handleList[ITechnology](gsm.playerValues.listOfTechnologiesCurrentlyResearched)

    val upkeepObj = buildings._2 ++ units._2 ++ gsm.playerValues.listOfUnits ++ gsm.playerValues.listOfBuildings
    val newUpkeep =
      returnAccumulated(upkeepObj, (x: IUpkeep) => x.upkeep, (x: ResourceHolder, y: ResourceHolder) => x.increase(y))
        .getOrElse(ResourceHolder())

    val buildingsCompleted = buildings._2 ++ gsm.playerValues.listOfBuildings
    val output: Output =
      returnAccumulated(buildingsCompleted, (x: IBuilding) => x.output, (x: Output, y: Output) => x.increase(y))
        .getOrElse(Output())

    val income = output.rHolder
    val newBalance = gsm.playerValues.resourceHolder.increase(income).decrease(newUpkeep).get
    val buildingsUnderConstruction = buildings._1
    val unitsCompleted = units._2 ++ gsm.playerValues.listOfUnits
    val unitsUnderConstruction = units._1
    val techCompleted = tech._2 ++ gsm.playerValues.listOfTechnologies
    val techCurrentlyResearched = tech._1

    val newPlayerValues = gsm.playerValues.copy(
      resourceHolder = newBalance,
      listOfUnits = unitsCompleted,
      listOfBuildings = buildingsCompleted,
      listOfTechnologies = techCompleted,
      listOfTechnologiesCurrentlyResearched = techCurrentlyResearched,
      listOfUnitsUnderConstruction = unitsUnderConstruction,
      listOfBuildingsUnderConstruction = buildingsUnderConstruction,
      income = income,
      upkeep = newUpkeep
    )
    gsm.copy(
      round = newRound,
      playerValues = newPlayerValues,
      message = GameStateStringFormatter(round = newRound, gsm = gsm.copy(playerValues = newPlayerValues)).overview(),
      gameState = RunningState()
    )

  private def returnAccumulated[T, R](list: List[T], f: T => R, combiner: (R, R) => R): Option[R] =
    if list.length > 1 then Option(list.map(f).reduce(combiner)) else list.map(f).headOption

  private def handleList[A <: IGameObject](list: List[A]): (List[A], List[A]) =
    val (completed, inProgress) = list.map(_.decreaseRoundsToComplete).partition(_.roundsToComplete.value != 0)
    (completed.asInstanceOf[List[A]], inProgress.asInstanceOf[List[A]])
