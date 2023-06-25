package model.game.gamestate

import model.core.board.sector.ISector
import model.core.board.sector.impl.{IPlayerSector, PlayerSector, Sector}
import model.core.board.boardutils.{GameBoardInfoWrapper, IGameBoardInfoWrapper}
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.resources.resourcetypes.{Energy, ResearchPoints}
import model.core.mechanics.fleets.Fleet
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.interfaces.IRoundBasedConstructable
import model.core.utilities.{GameValues, ICapacity, IResourceHolder, IRound, Round}
import model.game.playervalues.PlayerValues
import sun.tools.jstat.Identifier

import utils.DefaultValueProvider.given_IGameValues

import scala.io.AnsiColor

case class GameStateStringFormatter(round: IRound = Round(),
                                    userMsg: String = "",
                                    gsm: IGameStateManager = GameStateManager()):

  private val gsmMapInfoWrapper: IGameBoardInfoWrapper = GameBoardInfoWrapper(gsm.gameMap)

  def separator(len: Int = 4): String = " |" + "-" * len + "| "
  
  def vertBar(len: Int = 30): String = "=" * len
  
  def empty: String = ""
  
  def overview(round: IRound = round, resourceHolder: IResourceHolder = gsm.currentPlayerValues.resourceHolder): String =
    header(round, resourceHolder) + gsm.gameMap.toString + "\n" + researching + "\n" +  getSectorDetails
    
  def goodbyeResponse: String = f"Goodbye!"
  
  def invalidInputResponse(msg: String): String =
    f"$msg - invalid\nEnter help to get an overview of all available commands"
    
  private def header(round: IRound = round, resourceHolder: IResourceHolder = gsm.currentPlayerValues.resourceHolder): String =
    val len: Int =
      (round.toString + separator() + resourceOverview(resourceHolder) + capacityInfo + separator()).length + 2
    vertBar(len) + "\n" + " " + round + separator()
      + resourceOverview(resourceHolder) + separator() + capacityInfo + " " + "\n" + vertBar(len) + "\n"
    
  private def capacityInfo: String =
    val usedCapacity: Int = gsmMapInfoWrapper.getUsedCapacity(gsm.currentPlayerValues.affiliation)
    s"Capacity: ${usedCapacity}/${gsm.currentPlayerValues.capacity.value + usedCapacity}"

  private def researching: String =
    groupAndMapWithRemainingRounds(gsm.currentPlayerValues.listOfTechnologiesCurrentlyResearched, "Ongoing research")

  private def getSectorDetails: String =
    "Sector Details: \n" +
    gsm.gameMap.getPlayerSectors(gsm.currentPlayerValues.affiliation).flatMap(sector => {
      s"""Sector: $sector
        | ${calculateSectorBuildSlots(sector)}
        | ${listWithIdAndCount("Buildings", sector.buildingsInSector)}
        | ${sectorFleets(sector)}
        | ${sectorProduction(sector)}\n\n""".stripMargin
    }).mkString

  private def calculateSectorBuildSlots(sector: IPlayerSector, prepend: String = "Used building slots: "): String =
    s"$prepend ${sector.buildingsInSector.length + sector.constQuBuilding.length}/${sector.sector.buildSlots.value}"
  
  private def sectorProduction(sector: IPlayerSector, prepend: String = "Current production:"): String =
    if sector.constQuUnits.isEmpty && sector.constQuBuilding.isEmpty then "No ongoing production"
    else
      s""" $prepend
        |  ${ listWithIdAndRemainingRounds("Units", sector.constQuUnits)}
        |  ${ listWithIdAndRemainingRounds("Buildings", sector.constQuBuilding)}
        |""".stripMargin
      
  private def sectorFleets(sector: ISector, prepend: String = "Fleets present:"): String =
    if sector.unitsInSector.isEmpty then "No Fleets present"
    else s"$prepend " + sector.asInstanceOf[IPlayerSector].unitsInSector.flatMap(_.name).mkString
  
  private def listWithIdAndRemainingRounds[T<:IGameObject & IRoundBasedConstructable](identifier: String, l: Seq[T]): String =
    if l.isEmpty then "" else
      s"""- $identifier:
        |   ${groupAndMapWithRemainingRounds(l, "")}
        |""".stripMargin

  private def listWithIdAndCount(identifier: String, l: Seq[IGameObject]): String =
    if l.isEmpty then "" else
      s"""- $identifier:
         |   ${groupAndMapWithCount(l, "")}
         |""".stripMargin
  
  private def resourceOverview(resourceHolder: IResourceHolder): String =
    s"Total Balance: " +
      s"[Energy: ${resourceHolder.energy.value}" +
      s"${getTrend(gsm.currentPlayerValues.income.subtract(gsm.currentPlayerValues.upkeep).energy.value)}] " +
      s"[Minerals: ${resourceHolder.minerals.value}" +
      s"${getTrend(gsm.currentPlayerValues.income.subtract(gsm.currentPlayerValues.upkeep).minerals.value)}] " +
      s"[Alloys: ${resourceHolder.alloys.value}" +
      s"${getTrend(gsm.currentPlayerValues.income.subtract(gsm.currentPlayerValues.upkeep).alloys.value)}] " +
      s"[Research Points: ${resourceHolder.researchPoints.value}" +
      s"${getTrend(gsm.currentPlayerValues.income.subtract(gsm.currentPlayerValues.upkeep).researchPoints.value)}]"
  
  private def getTrend(value: Int): String =
    if value > 0 then AnsiColor.GREEN + s" + $value" + AnsiColor.RESET
    else if value < 0 then AnsiColor.RED + s" - ${value * (-1)}" + AnsiColor.RESET
    else ""
  
  private def groupAndMapWithCount[T<:IGameObject](s: Seq[T], identifier: String): String =
    if s.nonEmpty then
      s" $identifier: " + s.groupBy(_.name).map(x => s"${x._1} x ${x._2.length}").mkString(" | ") + "\n"
    else ""
  
  private def groupAndMapWithRemainingRounds[T<:IGameObject & IRoundBasedConstructable](s: Seq[T], identifier: String): String =
    if s.nonEmpty then
      s" $identifier: " + s.sortBy(_.roundsToComplete.value).groupBy(_.roundsToComplete)
        .map(x => s"[${x._2.mkString(", ")} | Rounds to complete: ${x._1.value}]").take(5).mkString(" ") + "\n"
    else ""