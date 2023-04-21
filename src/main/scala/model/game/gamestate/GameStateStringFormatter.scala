package model.game.gamestate

import model.game.{GameValues, IValues, PlayerValues, Round}
import model.purchasable.IGameObject
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}

import scala.io.AnsiColor

case class GameStateStringFormatter(round: Round = Round(),
                                    playerValues: PlayerValues = PlayerValues(),
                                    userMsg: String = "",
                                    gameValues: GameValues = GameValues(),
                                    gameStateManager: IGameStateManager = GameStateManager()):

  def separator(len: Int = 4): String = " |" + "-" * len + "| "
  def vertBar(len: Int = 30): String = "=" * len
  def empty: String = ""
  def overview(round: Round = round, resourceHolder: ResourceHolder = playerValues.resourceHolder): String =
    header(round, resourceHolder) + underConstruction + inventory + gameStateManager.gameMap.toString
  def goodbyeResponse: String = f"Goodbye!"
  def invalidInputResponse(msg: String): String =
    f"$msg - invalid\nEnter help to get an overview of all available commands"
  private def header(round: Round = round, resourceHolder: ResourceHolder = playerValues.resourceHolder): String =
    val len: Int = (round.toString + separator() + resourceOverview(resourceHolder) + separator()).length + 2
    vertBar(len) + "\n" + " " + round + separator()
      + resourceOverview(resourceHolder) + separator() + " " + "\n" + vertBar(len) + "\n"
  private def underConstruction: String = 
    if producing then "Ongoing Production:\n" + recruiting + researching + construction else "\n"
  private def inventory: String =
    if inventoryNotEmpty then "Inventory:\n" + units + tech + buildings else "\n"
  private def units: String =
    groupAndMapToStringWithCount(playerValues.listOfUnits, "Units")
  private def tech: String =
    groupAndMapToStringWithCount(playerValues.listOfTechnologies, "Technologies")
  private def buildings: String =
    groupAndMapToStringWithCount(playerValues.listOfBuildings, "Buildings")
  private def recruiting: String =
    mapToStringWithRemainingRounds(playerValues.listOfUnitsUnderConstruction, "Ongoing recruitment")
  private def researching: String =
    mapToStringWithRemainingRounds(playerValues.listOfTechnologiesCurrentlyResearched, "Ongoing research")
  private def construction: String =
    mapToStringWithRemainingRounds(playerValues.listOfBuildingsUnderConstruction, "Ongoing construction")
  private def producing: Boolean =
    playerValues.listOfUnitsUnderConstruction.nonEmpty 
      || playerValues.listOfBuildingsUnderConstruction.nonEmpty 
      || playerValues.listOfTechnologiesCurrentlyResearched.nonEmpty
  private def inventoryNotEmpty: Boolean =
    playerValues.listOfBuildings.nonEmpty
      || playerValues.listOfBuildings.nonEmpty
      || playerValues.listOfTechnologies.nonEmpty
  private def resourceOverview(resourceHolder: ResourceHolder): String =
    s"Total Balance: " +
      s"[Energy: ${resourceHolder.energy.value}" +
      s"${getTrend(playerValues.income.subtract(playerValues.upkeep).energy.value)}] " +
      s"[Minerals: ${resourceHolder.minerals.value}" +
      s"${getTrend(playerValues.income.subtract(playerValues.upkeep).minerals.value)}] " +
      s"[Alloys: ${resourceHolder.alloys.value}" +
      s"${getTrend(playerValues.income.subtract(playerValues.upkeep).alloys.value)}] " +
      s"[Research Points: ${resourceHolder.alloys.value}" +
      s"${getTrend(playerValues.income.subtract(playerValues.upkeep).researchPoints.value)}] "
  private def getTrend(value: Int): String =
    if value > 0 then AnsiColor.GREEN + s" + $value" + AnsiColor.RESET
    else if value < 0 then AnsiColor.RED + s" - ${value * (-1)}" + AnsiColor.RESET
    else ""
  private def groupAndMapToStringWithCount(list: List[IGameObject], identifier: String): String =
    if list.nonEmpty then
      s" $identifier: " + list.groupBy(_.name).map(x => s"${x._1} x ${x._2.length}").mkString(" | ") + "\n"
    else ""
  private def mapToStringWithRemainingRounds(list: List[IGameObject], identifier: String): String =
    if list.nonEmpty then
      s" $identifier: " + list.sortBy(_.roundsToComplete.value)
        .map(x => s"[${x.name} | Rounds to complete: ${x.roundsToComplete.value}]").mkString(" ") + "\n"
    else ""