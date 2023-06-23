package utils

import model.core.board.GameBoard
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.board.sector.ISector
import model.core.board.sector.impl.{IPlayerSector, PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.building.{EnergyGrid, Factory, Hangar, IBuilding, Mine, ResearchLab, Shipyard}
import model.core.gameobjects.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, ITechnology, NanoRobotics, Polymer}
import model.core.gameobjects.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer}
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.core.mechanics.MoveVector
import model.core.mechanics.fleets.{Fleet, IFleet}
import model.core.mechanics.fleets.components.Component
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{Capacity, GameValues, IGameValues, IRound, ResourceHolder, Round}
import model.game.gamestate.gamestates.RunningState
import model.game.gamestate.{GameStateManager, IGameStateManager}
import model.game.playervalues.PlayerValues

import scala.util.{Failure, Try}
import scala.xml.{Elem, Node}
import scala.xml.NodeSeq.seqToNodeSeq

object XMLConverter {

  given gameValues: IGameValues = GameValues()

  private def xmlToFleet(elem: Node): IFleet =
    val name = (elem \ "Name").text.trim
    val fleetComponentsXML = elem \ "FleetComponents"
    val location = xmlToSector((elem \ "Location").head)
    val moveVector = xmlToMoveVector((elem \ "MoveVector").head)
    val fleetComponents = fleetComponentsXML.map(x => xmlToUnit(x)).toVector
    Fleet(name = name, fleetComponents = fleetComponents, location = location, moveVector = moveVector)


  private def xmlToMoveVector(elem: Node): MoveVector =
    val start = xmlToCoordinate((elem \ "Start").head)
    val goal = xmlToCoordinate((elem \ "Goal").head)
    MoveVector(start = start, target = goal)


  def xmlToGameStateManager(elem: Node): IGameStateManager =
    val round = (elem \ "Round").text.trim.toInt
    val gameMap = xmlToGameBoard((elem \ "GameBoard").head)
    val playerValues = xmlToPlayerValues((elem \ "PlayerValues").head)
    GameStateManager(round = Round(round), gameMap = gameMap, playerValues = playerValues, gameState = RunningState())

  private def xmlToUnit(elem: Node): IUnit =
    val name = (elem \ "Name").text.trim
    val rounds = xmlToRound((elem \ "Round").head)
    val location = xmlToSector((elem \ "Sector").head)

    name match
      case "Battleship" => Battleship(roundsToComplete = rounds, location = location)
      case "Cruiser" => Cruiser(roundsToComplete = rounds, location = location)
      case "Destroyer" => Destroyer(roundsToComplete = rounds, location = location)
      case "Corvette" => Corvette(roundsToComplete = rounds, location = location)

  private def xmlToBuilding(elem: Node): IBuilding =
    val name = (elem \ "Name").text.trim
    val rounds = xmlToRound((elem \ "Round").head)
    val location = xmlToSectorType((elem \\ "Sector").head)

    name match
      case "Energy Grid" => EnergyGrid(roundsToComplete = rounds, location = location)
      case "Factory" => Factory(roundsToComplete = rounds, location = location)
      case "Mine" => Mine(roundsToComplete = rounds, location = location)
      case "Hangar" => Hangar(roundsToComplete = rounds, location = location)
      case "Research Lab" => ResearchLab(roundsToComplete = rounds, location = location)
      case "Shipyard" => Shipyard(roundsToComplete = rounds, location = location)


  private def xmlToComponent(elem: Node): Component =
    (elem \ "Type").text.trim match
      case "Fleet" => xmlToFleet(elem)
      case _ => xmlToUnit(elem)

  private def xmlToCoordinate(elem: Node): ICoordinate =
    val xPos = (elem \\ "xPos").text.toInt
    val yPos = (elem \\ "yPos").text.toInt

    Coordinate(xPos, yPos)

  private def xmlToSector(elem: Node): ISector = {
    val location = xmlToCoordinate((elem \ "Location").head)
    val affiliationText = (elem \ "Affiliation").text.trim
    val sectorTypeText = (elem \ "SectorType").text.trim
    val unitDataXML = elem \ "UnitsInSector" \\ "Fleet"

    val unitsInSector: Vector[IFleet] = unitDataXML.map(x => xmlToFleet(x)).toVector
    val affiliation = affiliationText match {
      case "ENEMY" => Affiliation.ENEMY
      case "INDEPENDENT" => Affiliation.INDEPENDENT
      case "PLAYER" => Affiliation.PLAYER
    }

    val sectorType = sectorTypeText match {
      case "BASE" => SectorType.BASE
      case "REGULAR" => SectorType.REGULAR
    }

    Sector(location = location, affiliation = affiliation, sectorType = sectorType, unitsInSector = unitsInSector)
  }

  private def xmlToPlayerSector(elem: Node): IPlayerSector = {
    val sectorXml = (elem \ "Sector").head
    val constBuildingXml = elem \ "ConstQuBuilding" \ "Building"
    val constUnitsXml = elem \ "ConstQuUnits" \ "Unit"
    val buildingsXml = elem \ "Buildings" \ "Building"

    val sector = xmlToSector(sectorXml)
    val constBuilding: Vector[IBuilding] = constBuildingXml.map(x => xmlToBuilding(x)).toVector
    val constUnits: Vector[IUnit] = constUnitsXml.map(x => xmlToUnit(x)).toVector
    val buildings: Vector[IBuilding] = buildingsXml.map(x => xmlToBuilding(x)).toVector

    PlayerSector(sector = sector,
      constQuBuilding = constBuilding,
      constQuUnits = constUnits,
      buildingsInSector = buildings)
  }

  private def xmlToSectorType(elem: Node): ISector = {
    (elem \ "Type").text.trim match {
      case "PlayerSector" => xmlToPlayerSector(elem)
      case "Sector" => xmlToSector(elem)
    }
  }

  private def xmlToGameBoard(elem: Node): GameBoard = {
    val sizeX = (elem \ "SizeX").text.trim.toInt
    val sizeY = (elem \ "SizeY").text.trim.toInt
    val sectorsXML = (elem \ "Data" \ "Sector").toVector

    val sectors: Vector[Vector[ISector]] = sectorsXML.map(x =>
      xmlToSectorType(x)).grouped(sizeX).toVector
    GameBoard(sizeX, sizeY, sectors)
  }



  private def xmlToRound(elem: Node): Round =
    val round = (elem \\ "Round").text.toInt
    Round(value = round)


  private def xmlToResourceHolder(elem: Node): ResourceHolder =
    val descriptor = (elem \\ "Descriptor").text
    val energy = (elem \\ "Energy").text.toInt
    val minerals = (elem \\ "Minerals").text.toInt
    val researchPoints = (elem \\ "ResearchPoints").text.toInt
    val alloys = (elem \\ "Alloys").text.toInt

    ResourceHolder(descriptor, Energy(energy), Minerals(minerals), Alloys(alloys), ResearchPoints(researchPoints))


  private def xmlToPlayerValues(elem: Node): PlayerValues =
    val balance = xmlToResourceHolder((elem \\ "ResourceHolder").head)
    val technologiesXml = elem \\ "Technologies" \ "Technology"
    val researchXml = elem \\ "TechnologiesCurrentlyResearched" \ "Technology"
    val capacity = (elem \\ "Capacity").text.toInt
    val upkeep = xmlToResourceHolder((elem \\ "Upkeep").head)
    val income = xmlToResourceHolder((elem \\ "Income").head)

    val tech = technologiesXml.map(x => xmlToTechnology(x)).toVector
    val research = researchXml.map(x => xmlToTechnology(x)).toVector

    PlayerValues(balance, tech, research, Capacity(capacity), upkeep, income)


  private def xmlToTechnology(elem: Node): ITechnology =
    val name = (elem \\ "Name").text
    val roundsToComplete = xmlToRound((elem \\ "Round").head)
    name match
      case "Polymer" => Polymer(roundsToComplete = roundsToComplete)
      case "AdvancedPropulsion" => AdvancedPropulsion(roundsToComplete = roundsToComplete)
      case "AdvancedMaterials" => AdvancedMaterials(roundsToComplete = roundsToComplete)
      case "NanoRobotics" => NanoRobotics(roundsToComplete = roundsToComplete)

}
