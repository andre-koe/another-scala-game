package utils

import io.circe.*
import utils.CirceImplicits.*
import io.circe.generic.semiauto
import io.circe.syntax.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.board.{GameBoard, IGameBoard}
import model.core.gameobjects.purchasable.building.{EnergyGrid, Factory, Hangar, IBuilding, Mine, ResearchLab, Shipyard}
import model.core.gameobjects.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, ITechnology, NanoRobotics, Polymer}
import model.core.gameobjects.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer}
import model.core.gameobjects.resources.IResource
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.core.mechanics.{IMoveVector, MoveVector}
import model.core.mechanics.fleets.{Fleet, IFleet}
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{BuildSlots, Capacity, GameValues, IBuildSlots, ICapacity, IGameValues, IResourceHolder, IRound, ResourceHolder, Round}
import model.game.gamestate.{GameStateManager, IGameStateManager}
import model.game.playervalues.{IPlayerValues, PlayerValues}

object CirceImplicits {

  given IGameValues = GameValues()


  implicit val encodeIBuilding: Encoder[IBuilding] = Encoder.instance {
    case mine: Mine => Json.obj(
      ("type", Json.fromString("Mine")),
      ("round", Json.fromInt(mine.roundsToComplete.value)),
      ("location", mine.location.asJson)
    )
    case energyGrid: EnergyGrid => Json.obj(
      ("type", Json.fromString("EnergyGrid")),
      ("round", Json.fromInt(energyGrid.roundsToComplete.value)),
      ("location", energyGrid.location.asJson)
    )
    case hangar: Hangar => Json.obj(
      ("type", Json.fromString("Hangar")),
      ("round", Json.fromInt(hangar.roundsToComplete.value)),
      ("location", hangar.location.asJson)
    )
    case researchLab: ResearchLab => Json.obj(
      ("type", Json.fromString("ResearchLab")),
      ("round", Json.fromInt(researchLab.roundsToComplete.value)),
      ("location", researchLab.location.asJson)
    )
    case shipyard: Shipyard => Json.obj(
      ("type", Json.fromString("Shipyard")),
      ("round", Json.fromInt(shipyard.roundsToComplete.value)),
      ("location", shipyard.location.asJson)
    )
    case factory: Factory => Json.obj(
      ("type", Json.fromString("Factory")),
      ("round", Json.fromInt(factory.roundsToComplete.value)),
      ("location", factory.location.asJson)
    )
  }

  implicit val decodeIBuilding: Decoder[IBuilding] = (c: HCursor) => {
    for {
      buildingType <- c.downField("type").as[String]
      round <- c.downField("round").as[Int]
      l <- c.downField("location").as[ICoordinate]
      building <- buildingType match {
        case "Mine" => Right(Mine(roundsToComplete = Round(round), location = l))
        case "EnergyGrid" => Right(EnergyGrid(roundsToComplete = Round(round), location = l))
        case "Hangar" => Right(Hangar(roundsToComplete = Round(round), location = l))
        case "ResearchLab" => Right(ResearchLab(roundsToComplete = Round(round), location = l))
        case "Shipyard" => Right(Shipyard(roundsToComplete = Round(round), location = l))
        case "Factory" => Right(Factory(roundsToComplete = Round(round), location = l))
        case _ => Left(DecodingFailure("Unknown unit type", c.history))
      }
    } yield building
  }

  implicit val encodeIUnit: Encoder[IUnit] = Encoder.instance {
    case corvette: Corvette => Json.obj(
      ("type", Json.fromString("Corvette")),
      ("round", Json.fromInt(corvette.roundsToComplete.value)),
    )
    case destroyer: Destroyer => Json.obj(
      ("type", Json.fromString("Destroyer")),
      ("round", Json.fromInt(destroyer.roundsToComplete.value)),
    )
    case cruiser: Cruiser => Json.obj(
      ("type", Json.fromString("Cruiser")),
      ("round", Json.fromInt(cruiser.roundsToComplete.value)),
    )
    case battleship: Battleship => Json.obj(
      ("type", Json.fromString("Battleship")),
      ("round", Json.fromInt(battleship.roundsToComplete.value)),
    )
  }

  implicit val decodeIUnit: Decoder[IUnit] = (c: HCursor) => {
    for {
      unitType <- c.downField("type").as[String]
      round <- c.downField("round").as[Int]
      unit <- unitType match {
        case "Corvette" => Right(Corvette(roundsToComplete = Round(round)))
        case "Destroyer" => Right(Destroyer(roundsToComplete = Round(round)))
        case "Cruiser" => Right(Cruiser(roundsToComplete = Round(round)))
        case "Battleship" => Right(Battleship(roundsToComplete = Round(round)))
        case _ => Left(DecodingFailure("Unknown unit type", c.history))
      }
    } yield unit
  }


  implicit val encodeIResource: Encoder[IResource[_]] = Encoder.instance {
    case energy: Energy => Json.obj(
      ("type", Json.fromString("Energy")),
      ("value", energy.asJson)
    )
    case alloys: Alloys => Json.obj(
      ("type", Json.fromString("Alloys")),
      ("value", alloys.asJson)
    )
    case researchPoints: ResearchPoints => Json.obj(
      ("type", Json.fromString("Researchpoints")),
      ("value", researchPoints.asJson)
    )
    case minerals: Minerals => Json.obj(
      ("type", Json.fromString("Minerals")),
      ("value", minerals.asJson)
    )
  }

  implicit val decodeIResource: Decoder[IResource[_]] = (c: HCursor) =>
    for {
      tpe <- c.downField("type").as[String]
      value <- c.downField("value").as[Int]
      resource <- tpe match {
        case "Energy" => Right(Energy(value))
        case "Alloys" => Right(Alloys(value))
        case "Researchpoints" => Right(ResearchPoints(value))
        case "Minerals" => Right(Minerals(value))
        case _ => Left(DecodingFailure("IResource", c.history))
      }
    } yield resource




  implicit val encodeITechnology: Encoder[ITechnology] = Encoder.instance {
    case polymer: Polymer => Json.obj(
      ("type", Json.fromString("Polymer")),
      ("value", polymer.asJson)
    )
    case advancedPropulsion: AdvancedPropulsion => Json.obj(
      ("type", Json.fromString("AdvancedPropulsion")),
      ("value", advancedPropulsion.asJson)
    )
    case advancedMaterials: AdvancedMaterials => Json.obj(
      ("type", Json.fromString("AdvancedMaterials")),
      ("value", advancedMaterials.asJson)
    )
    case nanoRobotics: NanoRobotics => Json.obj(
      ("type", Json.fromString("NanoRobotics")),
      ("value", nanoRobotics.asJson)
    )
  }

  implicit val decodeITechnology: Decoder[ITechnology] = (c: HCursor) => {

    c.downField("type").as[String].flatMap {
      case "Polymer" => c.downField("value").as[Polymer]
      case "AdvancedPropulsion" => c.downField("value").as[AdvancedPropulsion]
      case "AdvancedMaterials" => c.downField("value").as[AdvancedMaterials]
      case "NanoRobotics" => c.downField("value").as[NanoRobotics]
      case _ => Left(DecodingFailure("ITechnology", c.history))
    }
  }

  implicit val encodeIResourceHolder: Encoder[IResourceHolder] = Encoder.instance {
    case resourceHolder: ResourceHolder => Json.obj(
      ("Descriptor", Json.fromString(resourceHolder.descriptor)),
      ("Energy", resourceHolder.energy.asJson),
      ("Minerals", resourceHolder.minerals.asJson),
      ("Alloys", resourceHolder.alloys.asJson),
      ("ResearchPoints", resourceHolder.researchPoints.asJson)
    )
  }

  implicit val decodeIResourceHolder: Decoder[IResourceHolder] = (c: HCursor) => {
    for {
      descriptor <- c.downField("Descriptor").as[String]
      energy <- c.downField("Energy").as[Energy]
      minerals <- c.downField("Minerals").as[Minerals]
      researchPoints <- c.downField("ResearchPoints").as[ResearchPoints]
      alloys <- c.downField("Alloys").as[Alloys]
    } yield {
      ResourceHolder(descriptor, energy, minerals, alloys, researchPoints)
    }
  }


  implicit val encodeICapacity: Encoder[ICapacity] = Encoder.instance {
    case capacity:Capacity => Json.obj(
      ("value", Json.fromInt(capacity.value)),
    )
  }

  implicit val decodeICapacity: Decoder[ICapacity] = (c: HCursor) => {
    for {
      value <- c.downField("value").as[Int]
    } yield {
      Capacity(value)
    }
  }

  implicit val encodeIBuildSlots: Encoder[IBuildSlots] = Encoder.instance {
    case buildSlots: BuildSlots => Json.obj(
      ("value", Json.fromInt(buildSlots.value)),
    )
  }

  implicit val decodeIBuildSlots: Decoder[IBuildSlots] = (c: HCursor) => {
    for {
      value <- c.downField("value").as[Int]
    } yield {
      BuildSlots(value)
    }
  }

  implicit val encodeIRound: Encoder[IRound] = Encoder.instance {
    case round: Round => Json.obj(
      ("value", Json.fromInt(round.value)),
    )
  }

  implicit val decodeIRound: Decoder[IRound] = (c: HCursor) => {
    for {
      value <- c.downField("value").as[Int]
    } yield {
      Round(value)
    }
  }

  implicit val encodeIPlayerValues: Encoder[IPlayerValues] = Encoder.instance {
    case playerValues: PlayerValues => Json.obj(
      ("resources", playerValues.resourceHolder.asJson),
      ("income", playerValues.income.asJson),
      ("upkeep", playerValues.upkeep.asJson),
      ("capacity", Json.fromInt(playerValues.capacity.value)),
      ("technologies", playerValues.listOfTechnologies.asJson),
      ("research", playerValues.listOfTechnologiesCurrentlyResearched.asJson)
    )
  }

  implicit val decodeIPlayerValues: Decoder[IPlayerValues] = (c: HCursor) => {
    for {
      resources <- c.downField("resources").as[IResourceHolder]
      income <- c.downField("income").as[IResourceHolder]
      upkeep <- c.downField("upkeep").as[IResourceHolder]
      capacity <- c.downField("capacity").as[Int]
      technologies <- c.downField("technologies").as[Vector[ITechnology]]
      research <- c.downField("research").as[Vector[ITechnology]]
    } yield {
      PlayerValues(resourceHolder = resources,
        listOfTechnologies = technologies,
        listOfTechnologiesCurrentlyResearched = research,
        capacity = Capacity(capacity),
        upkeep = upkeep,
        income = income)
    }
  }

  implicit val encodeIGameStateManager: Encoder[IGameStateManager] = Encoder.instance {
    case gsm: GameStateManager => Json.obj(
      ("Round", gsm.round.asJson),
      ("GameBoard", gsm.gameMap.asJson),
      ("CurrentPlayerIndex", Json.fromInt(gsm.currentPlayerIndex)),
      ("PlayerValues", gsm.playerValues.asJson)
    )
  }

  implicit val decodeIGameStateManager: Decoder[IGameStateManager] = (c: HCursor) => {
    for {
      round <- c.downField("Round").as[IRound]
      gameBoard <- c.downField("GameBoard").as[IGameBoard]
      currentPlayerIndex  <- c.downField("CurrentPlayerIndex").as[Int]
      playerValues <- c.downField("PlayerValues").as[Vector[IPlayerValues]]
    } yield {
      GameStateManager(round = round,
        gameMap = gameBoard,
        currentPlayerIndex = currentPlayerIndex,
        playerValues = playerValues)
    }
  }

  implicit val encodeIGameBoard : Encoder[IGameBoard] = Encoder.instance {
    case gameBoard: GameBoard => Json.obj(
      ("data", gameBoard.data.asJson)
    )
  }

  implicit val decodeIGameBoard: Decoder[IGameBoard] = (c: HCursor) => {
    for {
      data <- c.downField("data").as[Vector[Vector[ISector]]]
    } yield {
      GameBoard(data.size, data(0).size, data)
    }
  }

  implicit val encodeICoordinate: Encoder[ICoordinate] = Encoder.instance {
    case coordinate: Coordinate => Json.obj(
      ("x", Json.fromInt(coordinate.xPos)),
      ("y", Json.fromInt(coordinate.yPos))
    )
  }

  implicit val decodeICoordinate: Decoder[ICoordinate] = (c: HCursor) => {
    for {
      x <- c.downField("x").as[Int]
      y <- c.downField("y").as[Int]
    } yield {
      Coordinate(x, y)
    }
  }

  implicit val encodeIMoveVector: Encoder[IMoveVector] = Encoder.instance {
    case coordinate:MoveVector => Json.obj(
      ("start", coordinate.start.asJson),
      ("end", coordinate.target.asJson)
    )
  }

  implicit val decodeIMoveVector: Decoder[IMoveVector] = (c: HCursor) => {
    for {
      start <- c.downField("start").as[ICoordinate]
      end <- c.downField("end").as[ICoordinate]
    } yield {
      MoveVector(start, end)
    }
  }

  implicit val encodeSectorType: Encoder[SectorType] = Encoder.instance {
    case SectorType.BASE => Json.obj("SectorType" -> Json.fromString("BASE"))
    case SectorType.REGULAR => Json.obj("SectorType" -> Json.fromString("REGULAR"))
  }

  implicit val decodeSectorType: Decoder[SectorType] = (c: HCursor) =>
    for {
      sType <- c.downField("SectorType").as[String]
    } yield {
      sType match {
        case "BASE" => SectorType.BASE
        case _ => SectorType.REGULAR
      }
    }

  implicit val encodeIFleet: Encoder[IFleet] = Encoder.instance {
    case fleet: Fleet => Json.obj(
      ("name", Json.fromString(fleet.name)),
      ("movement", fleet.moveVector.asJson),
      ("location", fleet.location.asJson),
      ("components", fleet.fleetComponents.asJson)
    )
  }

  implicit val decodeIFleet: Decoder[IFleet] = (c: HCursor) => {
    for {
      name <- c.downField("name").as[String]
      moveVector <- c.downField("movement").as[MoveVector]
      location <- c.downField("location").as[ICoordinate]
      components <- c.downField("components").as[Vector[IUnit]]
    } yield {
      Fleet(name = name, fleetComponents = components, location = location, moveVector = moveVector)
    }
  }

  implicit val decodeISector: Decoder[ISector] = (c: HCursor) => c.downField("Type").as[String].flatMap {
    case "Sector" => c.as[Sector]
    case "PlayerSector" => c.as[PlayerSector]
  }

  implicit val decodeSector: Decoder[Sector] = (c: HCursor) =>
    for {
      sectorType <- c.downField("Sector").downField("Sectortype").as[SectorType]
      location <- c.downField("Sector").downField("Location").as[ICoordinate]
      fleets <- c.downField("Sector").downField("Fleets").as[Vector[IFleet]]
      affiliation <- c.downField("Sector").downField("Affiliation").as[Affiliation]
    } yield {
      Sector(location, affiliation, sectorType, fleets)
    }

  implicit val decodePlayerSector: Decoder[PlayerSector] = (c: HCursor) =>
    for {
      sector <- c.downField("Sector").as[Sector]
      unitConstruction <- c.downField("Unitconstruction").as[Vector[IUnit]]
      buildingConstruction <- c.downField("Buildingconstruction").as[Vector[IBuilding]]
      buildings <- c.downField("Buildings").as[Vector[IBuilding]]
    } yield {
      PlayerSector(sector, buildingConstruction, unitConstruction, buildings)
    }

  implicit val encodeISector: Encoder[ISector] = Encoder.instance {
    case sector: Sector => Json.obj(
      ("Type", Json.fromString("Sector")),
      ("Sector", Json.obj(
        ("Sectortype", sector.sectorType.asJson),
        ("Location", sector.location.asJson),
        ("Fleets", sector.unitsInSector.asJson),
        ("Affiliation", sector.affiliation.asJson)
      ))
    )
    case playerSector: PlayerSector => Json.obj(
      ("Type", Json.fromString("PlayerSector")),
      ("Sector", playerSector.sector.asJson),
      ("Unitconstruction", playerSector.constQuUnits.asJson),
      ("Buildingconstruction", playerSector.constQuBuilding.asJson),
      ("Buildings", playerSector.buildingsInSector.asJson)
    )
  }

}
