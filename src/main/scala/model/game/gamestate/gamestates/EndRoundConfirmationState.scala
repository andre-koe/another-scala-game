package model.game.gamestate.gamestates

import model.game.gamestate.{GameStateManager, GameStateStringFormatter, IGameState}
import model.game.purchasable.{IGameObject, IUpkeep}
import model.game.purchasable.building.IBuilding
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit
import model.game.purchasable.utils.Output
import model.game.resources.ResourceHolder

case class EndRoundConfirmationState() extends IGameState:

  override def update(gsm: GameStateManager): GameStateManager = nextRound(gsm)

  def nextRound(gsm: GameStateManager): GameStateManager =
    val newRound = gsm.round.next

    val buildings = handleList(gsm.playerValues.listOfBuildingsUnderConstruction)
    val units = handleList(gsm.playerValues.listOfUnitsUnderConstruction)
    val tech = handleList(gsm.playerValues.listOfTechnologiesCurrentlyResearched)

    val newUpkeep = calcUpkeep(
      (buildings._2 ++ units._2 ++ gsm.playerValues.listOfUnits ++ gsm.playerValues.listOfBuildings)
        .asInstanceOf[List[IUpkeep]])

    val buildingsCompleted = buildings._2.asInstanceOf[List[IBuilding]] ++ gsm.playerValues.listOfBuildings
    val output: Output = calcOutput(buildingsCompleted)
    val income = output.resourceHolder

    // TODO: Add handler if current balance negative reduce Unit strength
    val newBalance = gsm.playerValues.resourceHolder.increase(income).decrease(newUpkeep).get
    val buildingsUnderConstruction = buildings._1.asInstanceOf[List[IBuilding]]
    val unitsCompleted = units._2.asInstanceOf[List[IUnit]] ++ gsm.playerValues.listOfUnits
    val unitsUnderConstruction = units._1.asInstanceOf[List[IUnit]]
    val techCompleted = tech._2.asInstanceOf[List[ITechnology]] ++ gsm.playerValues.listOfTechnologies
    val techCurrentlyResearched = tech._1.asInstanceOf[List[ITechnology]]

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
      message = GameStateStringFormatter(round = newRound, playerValues = newPlayerValues).overview(),
      gameState = RunningState()
    )

  private def calcOutput(list: List[IBuilding]): Output =
    list match
      case Nil => Output()
      case value :: Nil => value.output
      case _ => list.map(_.output).reduce((x, y) => x.increaseOutput(y))

  private def calcUpkeep(list: List[IUpkeep]): ResourceHolder =
    list match
      case Nil => ResourceHolder()
      case value :: Nil => value.upkeep
      case _ => list.map(_.upkeep).reduce((x, y) => x.increase(y))

  private def handleList(list: List[IGameObject]): (List[IGameObject], List[IGameObject]) =
    list.map(_.decreaseRoundsToComplete).partition(_.roundsToComplete.value != 0)
