package model.game.gamestate

import model.game.purchasable.IGameObject
import model.game.purchasable.units.IUnit
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, ResearchPoints}
import model.game.{Capacity, GameValues, PlayerValues, Round}

import scala.io.AnsiColor

case class GameStateStringFormatter(round: Round = Round(),
                                    playerValues: PlayerValues = PlayerValues(),
                                    userMsg: String = "",
                                    gameValues: GameValues = GameValues(),
                                    gameStateManager: GameStateManager = GameStateManager()):

  def separator(len: Int = 4): String = " |" + "-" * len + "| "
  
  def vertBar(len: Int = 30): String = "=" * len
  
  def empty: String = ""
  
  def overview(round: Round = round, resourceHolder: ResourceHolder = playerValues.resourceHolder): String =
    header(round, resourceHolder) + underConstruction + inventory + gameStateManager.gameMap.toString
    
  def goodbyeResponse: String = f"Goodbye!"
  
  def invalidInputResponse(msg: String): String =
    f"$msg - invalid\nEnter help to get an overview of all available commands"
    
  private def header(round: Round = round, resourceHolder: ResourceHolder = playerValues.resourceHolder): String =
    val len: Int = (round.toString + separator() + resourceOverview(resourceHolder) + capacityInfo + separator()).length + 2
    vertBar(len) + "\n" + " " + round + separator()
      + resourceOverview(resourceHolder) + separator() + capacityInfo + " " + "\n" + vertBar(len) + "\n"
    
  private def capacityInfo: String =
    val usedCapacity: Capacity =
      getUsedCapacity(playerValues.listOfUnits).increase(getUsedCapacity(playerValues.listOfUnitsUnderConstruction))
    s"Capacity: ${usedCapacity.value}/${playerValues.capacity.value + usedCapacity.value}"
    
  private def getUsedCapacity(list: List[IUnit]): Capacity = list match
    case Nil => Capacity()
    case some :: Nil => some.capacity
    case _ => list.map(_.capacity).reduce((x, y) => x.increase(y))
    
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
      || playerValues.listOfUnits.nonEmpty
      || playerValues.listOfTechnologies.nonEmpty
    
  private def resourceOverview(resourceHolder: ResourceHolder): String =
    s"Total Balance: " +
      s"[Energy: ${resourceHolder.energy.value}" +
      s"${getTrend(playerValues.income.subtract(playerValues.upkeep).energy.value)}] " +
      s"[Minerals: ${resourceHolder.minerals.value}" +
      s"${getTrend(playerValues.income.subtract(playerValues.upkeep).minerals.value)}] " +
      s"[Alloys: ${resourceHolder.alloys.value}" +
      s"${getTrend(playerValues.income.subtract(playerValues.upkeep).alloys.value)}] " +
      s"[Research Points: ${resourceHolder.researchPoints.value}" +
      s"${getTrend(playerValues.income.subtract(playerValues.upkeep).researchPoints.value)}]"
    
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