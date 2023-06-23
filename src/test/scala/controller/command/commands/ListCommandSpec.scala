package controller.command.commands

import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.building.ResearchLab
import model.core.gameobjects.purchasable.technology.AdvancedMaterials
import model.core.gameobjects.purchasable.units.Corvette
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.core.utilities.{Capacity, GameValues, ResourceHolder}
import model.game.gamestate.enums.ListParams
import model.game.gamestate.enums.ListParams.*
import model.game.gamestate.{GameStateManager, GameStateStringFormatter}
import model.game.playervalues
import model.game.playervalues.PlayerValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.io.AnsiColor

class ListCommandSpec extends AnyWordSpec {

  val location: ISector = Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR)

  "The ListCommand" should {
    "return a new toString representation depending on the input" when {

      "asked for list building(s) should list all Buildings" in {
        val gameStateManager: GameStateManager = GameStateManager()
        val listBuilding: ListCommand = ListCommand(BUILDING, gameStateManager)

        listBuilding.execute()
          .toString.split("\n").length should be(GameValues().buildings.length + 1)
      }

      "asked for list technology/tech/technologies should list all Technologies" in {
        val gameStateManager: GameStateManager = GameStateManager()
        val listTech: ListCommand = ListCommand(TECHNOLOGY, gameStateManager)

        listTech.execute().toString.split("\n").length should be(GameValues().tech.length + 1)
      }

      "asked for list unit(s) should list all Units" in {
        val gameStateManager: GameStateManager = GameStateManager()
        val listUnit: ListCommand = ListCommand(UNITS, gameStateManager)

        listUnit.execute().toString.split("\n").length should be(GameValues().units.length + 1)
      }

      "asked for list with empty input should list everything" in {
        val gameStateManager: GameStateManager = GameStateManager()
        val listAll: ListCommand = ListCommand(ALL, gameStateManager)

        listAll.execute().toString.split("\n").length should be(GameValues().tech.length
          + GameValues().units.length + GameValues().buildings.length + 3)
      }
    }

    "return a building list with red colored items if the player has insufficient funds for any of them " +
        "appended by the amount of resources lacking" in {
        val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder())
        val gsM: GameStateManager = GameStateManager(playerValues = plV)
        val listBuilding: ListCommand = ListCommand(BUILDING, gsM, GameValues(buildings = Vector(ResearchLab())))

        listBuilding.execute()
          .toString should be(s"==== BUILDINGS ====\n - ${AnsiColor.RED}Research Lab " +
          s"Total Lacking: [Energy: 100] [Minerals: 100] [Alloys: 100]${AnsiColor.RESET}\n")
      }

    "return a building list with uncolored items if the player has sufficient funds for any of them" in {
      val plV: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          energy = Energy(200),
          minerals = Minerals(200),
          alloys = Alloys(200)
      ))
      val gsM: GameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand =
        ListCommand(BUILDING, gsM, gameValues = GameValues(buildings = Vector(ResearchLab())))

      listBuilding.execute()
        .toString should be(s"==== BUILDINGS ====\n - " +
        s"Research Lab\n")
    }

    "return a technology list with red colored items if the player has insufficient funds for any of them" +
      "appended by the amount of resources lacking" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder())
      val gsM: GameStateManager = GameStateManager(playerValues = plV,
        gameValues = GameValues(tech = Vector(AdvancedMaterials())))
      val listTech: ListCommand =
        ListCommand(TECHNOLOGY, gsM, gameValues = GameValues(tech = Vector(AdvancedMaterials())))

      listTech.execute()
        .toString should be(s"==== TECHNOLOGIES ====\n - ${AnsiColor.RED}Advanced Materials " +
        s"Total Lacking: [Research Points: 200]${AnsiColor.RESET}\n")
    }

    "return a technology list with uncolored items if the player has sufficient funds" in {
      val plV: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          researchPoints = ResearchPoints(200)
        ))
      val gsM: GameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand =
        ListCommand(TECHNOLOGY, gsM, gameValues = GameValues(tech = Vector(AdvancedMaterials())))

      listBuilding.execute()
        .toString should be(s"==== TECHNOLOGIES ====\n - " +
        s"Advanced Materials\n")
    }

    "return a technology list with a cyan colored item if the player has sufficient funds" +
      " but already researched the technology" in {
      val plV: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          researchPoints = ResearchPoints(200)
        ),
        listOfTechnologies = Vector(AdvancedMaterials())
      )
      val gsM: GameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand =
        ListCommand(TECHNOLOGY, gsM, gameValues = GameValues(tech = Vector(AdvancedMaterials())))

      listBuilding.execute()
        .toString should be(s"==== TECHNOLOGIES ====\n - " +
        s"${AnsiColor.CYAN}Advanced Materials (already researched)${AnsiColor.RESET}\n")
    }

    "return a technology list with a cyan colored item if the player has insufficient funds" +
      " but already researched the technology" in {
      val plV: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          researchPoints = ResearchPoints(10)
        ),
        listOfTechnologies = Vector(AdvancedMaterials())
      )
      val gsM: GameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand =
        ListCommand(TECHNOLOGY, gsM, gameValues = GameValues(tech = Vector(AdvancedMaterials())))

      listBuilding.execute()
        .toString should be(s"==== TECHNOLOGIES ====\n - " +
        s"${AnsiColor.CYAN}Advanced Materials (already researched)${AnsiColor.RESET}\n")
    }

    "return a technology list with a cyan colored item if the player has insufficient funds" +
      " but is in process of researching the technology" in {
      val plV: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          researchPoints = ResearchPoints(10)
        ),
        listOfTechnologiesCurrentlyResearched = Vector(AdvancedMaterials())
      )
      val gsM: GameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand =
        ListCommand(TECHNOLOGY, gsM, gameValues = GameValues(tech = Vector(AdvancedMaterials())))
      listBuilding.execute()
        .toString should be(s"==== TECHNOLOGIES ====\n - " +
        s"${AnsiColor.CYAN}Advanced Materials (already researched)${AnsiColor.RESET}\n")
    }

    "return a unit list with red colored items if the player has insufficient funds for any of them" +
      "appended by the amount of resources lacking" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder())
      val gsM: GameStateManager = GameStateManager(playerValues = plV)
      val listUnit: ListCommand =
        ListCommand(UNITS, gsM, gameValues = GameValues(units = Vector(Corvette())))

      listUnit.execute()
        .toString should be(s"==== UNITS ====\n - ${AnsiColor.RED}Corvette " +
        s"Total Lacking: [Energy: 70] [Minerals: 30]${AnsiColor.RESET}\n")
    }

    "return a unit list with red colored items if the player has sufficient funds but insufficient capacity" +
      " for any of them appended by the amount of resources lacking" in {
      val plV: PlayerValues = playervalues.PlayerValues(
        resourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(100)), capacity = Capacity(0))
      val gsM: GameStateManager = GameStateManager(playerValues = plV)
      val listUnit: ListCommand =
        ListCommand(UNITS, gsM, gameValues = GameValues(units = Vector(Corvette())))


      listUnit.execute()
        .toString should be(s"==== UNITS ====\n - ${AnsiColor.RED}Corvette " +
        s"Total Lacking: [Capacity: 1]${AnsiColor.RESET}\n")
    }

    "return a unit list with uncolored items appended by the amount the player can recruit" +
      " if the player has sufficient funds" in {
      val plV: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          energy = Energy(210),
          minerals = Minerals(90)
        ))
      val gsM: GameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand = ListCommand(UNITS, gsM, gameValues = GameValues(units = Vector(Corvette())))

      listBuilding.execute()
        .toString should be(s"==== UNITS ====\n - " +
        s"Corvette (3)\n")
    }
  }
}
