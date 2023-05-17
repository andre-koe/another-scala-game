package model.game.gamestate

import model.game.playervalues.PlayerValues
import model.game.purchasable.IGameObject
import model.game.purchasable.units.IUnit
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, ResearchPoints}
import model.game.{Capacity, GameValues, Round}

import scala.io.AnsiColor

case class GameStateStringFormatter(round: Round = Round(),
                                    userMsg: String = "",
                                    gameValues: GameValues = GameValues(),
                                    gsm: GameStateManager = GameStateManager()):

  def separator(len: Int = 4): String = " |" + "-" * len + "| "
  
  def vertBar(len: Int = 30): String = "=" * len
  
  def empty: String = ""
  
  def overview(round: Round = round, resourceHolder: ResourceHolder = gsm.playerValues.resourceHolder): String =
    header(round, resourceHolder) + production + inventory + gsm.gameMap.toString
    
  def goodbyeResponse: String = f"Goodbye!"
  
  def invalidInputResponse(msg: String): String =
    f"$msg - invalid\nEnter help to get an overview of all available commands"
    
  private def header(round: Round = round, resourceHolder: ResourceHolder = gsm.playerValues.resourceHolder): String =
    val len: Int =
      (round.toString + separator() + resourceOverview(resourceHolder) + capacityInfo + separator()).length + 2
    vertBar(len) + "\n" + " " + round + separator()
      + resourceOverview(resourceHolder) + separator() + capacityInfo + " " + "\n" + vertBar(len) + "\n"
    
  private def capacityInfo: String =
    val usedCapacity: Capacity =
      getUsedCapacity(gsm.playerValues.listOfUnits).increase(getUsedCapacity(gsm.playerValues.listOfUnitsUnderConstruction))
    s"Capacity: ${usedCapacity.value}/${gsm.playerValues.capacity.value + usedCapacity.value}"
    
  private def getUsedCapacity(list: List[IUnit]): Capacity = list match
    case Nil => Capacity()
    case some :: Nil => some.capacity
    case _ => list.map(_.capacity).reduce((x, y) => x.increase(y))
    
  private def production: String =
    if producing then "Ongoing Production:\n" + recruiting + researching + construction else "\n"
    
  private def inventory: String =
    if inventoryNotEmpty then "Inventory:\n" +
      groupAndMapWithCount(gsm.playerValues.listOfUnits, "Units") +
      groupAndMapWithCount(gsm.playerValues.listOfTechnologies, "Technologies") +
      groupAndMapWithCount(gsm.playerValues.listOfBuildings, "Buildings") else "\n"
    
  private def recruiting: String =
    groupAndMapWithRemainingRounds(gsm.playerValues.listOfUnitsUnderConstruction, "Ongoing recruitment")
    
  private def researching: String =
    groupAndMapWithRemainingRounds(gsm.playerValues.listOfTechnologiesCurrentlyResearched, "Ongoing research")
    
  private def construction: String =
    groupAndMapWithRemainingRounds(gsm.playerValues.listOfBuildingsUnderConstruction, "Ongoing construction")
    
  private def producing: Boolean =
    gsm.playerValues.listOfUnitsUnderConstruction.nonEmpty
      || gsm.playerValues.listOfBuildingsUnderConstruction.nonEmpty
      || gsm.playerValues.listOfTechnologiesCurrentlyResearched.nonEmpty
    
  private def inventoryNotEmpty: Boolean =
    gsm.playerValues.listOfBuildings.nonEmpty
      || gsm.playerValues.listOfUnits.nonEmpty
      || gsm.playerValues.listOfTechnologies.nonEmpty
    
  private def resourceOverview(resourceHolder: ResourceHolder): String =
    s"Total Balance: " +
      s"[Energy: ${resourceHolder.energy.value}" +
      s"${getTrend(gsm.playerValues.income.subtract(gsm.playerValues.upkeep).energy.value)}] " +
      s"[Minerals: ${resourceHolder.minerals.value}" +
      s"${getTrend(gsm.playerValues.income.subtract(gsm.playerValues.upkeep).minerals.value)}] " +
      s"[Alloys: ${resourceHolder.alloys.value}" +
      s"${getTrend(gsm.playerValues.income.subtract(gsm.playerValues.upkeep).alloys.value)}] " +
      s"[Research Points: ${resourceHolder.researchPoints.value}" +
      s"${getTrend(gsm.playerValues.income.subtract(gsm.playerValues.upkeep).researchPoints.value)}]"
    
  private def getTrend(value: Int): String =
    if value > 0 then AnsiColor.GREEN + s" + $value" + AnsiColor.RESET
    else if value < 0 then AnsiColor.RED + s" - ${value * (-1)}" + AnsiColor.RESET
    else ""
    
  private def groupAndMapWithCount[T<:IGameObject](list: List[T], identifier: String): String =
    if list.nonEmpty then
      s" $identifier: " + list.groupBy(_.name).map(x => s"${x._1} x ${x._2.length}").mkString(" | ") + "\n"
    else ""
    
  private def groupAndMapWithRemainingRounds[T<:IGameObject](list: List[T], identifier: String): String =
    if list.nonEmpty then
      s" $identifier: " + list.sortBy(_.roundsToComplete.value).groupBy(_.roundsToComplete)
        .map(x => s"[${x._2.mkString(", ")} | Rounds to complete: ${x._1.value}]").take(5).mkString(" ") + "\n"
    else ""