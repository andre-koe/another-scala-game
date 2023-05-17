package controller.command.commands

import model.game.{Capacity, GameValues, playervalues}
import model.game.gamestate.{GameStateManager, GameStateStringFormatter}
import model.game.purchasable.building.ResearchLab
import model.game.purchasable.technology.AdvancedMaterials
import model.game.purchasable.units.Corvette
import model.game.resources.ResourceHolder
import model.game.gamestate.enums.ListParams
import model.game.gamestate.enums.ListParams.*
import model.game.playervalues.PlayerValues
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import scala.io.AnsiColor

class ListCommandSpec extends AnyWordSpec {

  "The ListCommand" should {
    "return a new toString representation depending on the input" when {
      val gameValues: GameValues = GameValues()

      "asked for list building(s) should list all Buildings" in {
        val gameStateManager: GameStateManager = GameStateManager()
        val listBuilding: ListCommand = ListCommand(BUILDING, gameStateManager)

        listBuilding.execute()
          .toString.split("\n").length should be(gameValues.listOfBuildings.length + 1)
      }

      "asked for list technology/tech/technologies should list all Technologies" in {
        val gameStateManager: GameStateManager = GameStateManager()
        val listTech: ListCommand = ListCommand(TECHNOLOGY, gameStateManager)

        listTech.execute().toString.split("\n").length should be(gameValues.listOfTechnologies.length + 1)
      }

      "asked for list unit(s) should list all Units" in {
        val gameStateManager: GameStateManager = GameStateManager()
        val listUnit: ListCommand = ListCommand(UNITS, gameStateManager)

        listUnit.execute().toString.split("\n").length should be(gameValues.listOfUnits.length + 1)
      }

      "asked for list with empty input should list everything" in {
        val gameStateManager: GameStateManager = GameStateManager()
        val listAll: ListCommand = ListCommand(ALL, gameStateManager)

        listAll.execute()
          .toString.split("\n").length should be(gameValues.listOfTechnologies.length
          + gameValues.listOfUnits.length + gameValues.listOfBuildings.length + 3)
      }
    }

    "return a building list with red colored items if the player has insufficient funds for any of them" +
        "appended by the amount of resources lacking" in {
        val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder())
        val gV: GameValues = GameValues(
          listOfBuildings = List(ResearchLab()),
          listOfUnits = List(Corvette()),
          listOfTechnologies = List(AdvancedMaterials()))
        val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
        val listBuilding: ListCommand = ListCommand(BUILDING, gsM)

        listBuilding.execute()
          .toString should be (s"==== BUILDINGS ====\n - ${AnsiColor.RED}Research Lab " +
          s"Total Lacking: [Energy: 100] [Minerals: 100] [Alloys: 100]${AnsiColor.RESET}\n")
      }

    "return a building list with uncolored items if the player has sufficient funds for any of them" in {
      val plV: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          energy = Energy(200),
          minerals = Minerals(200),
          alloys = Alloys(200)
      ))
      val gV: GameValues = GameValues(
        listOfBuildings = List(ResearchLab()))
      val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
      val listBuilding: ListCommand = ListCommand(BUILDING, gsM)

      listBuilding.execute()
        .toString should be(s"==== BUILDINGS ====\n - " +
        s"Research Lab\n")
    }

    "return a technology list with red colored items if the player has insufficient funds for any of them" +
      "appended by the amount of resources lacking" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder())
      val gV: GameValues = GameValues(
        listOfTechnologies = List(AdvancedMaterials()))
      val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
      val listTech: ListCommand = ListCommand(TECHNOLOGY, gsM)

      listTech.execute()
        .toString should be(s"==== TECHNOLOGIES ====\n - ${AnsiColor.RED}Advanced Materials " +
        s"Total Lacking: [Research Points: 200]${AnsiColor.RESET}\n")
    }

    "return a technology list with uncolored items if the player has sufficient funds" in {
      val plV: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          researchPoints = ResearchPoints(200)
        ))
      val gV: GameValues = GameValues(
        listOfTechnologies = List(AdvancedMaterials()))
      val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
      val listBuilding: ListCommand = ListCommand(TECHNOLOGY, gsM)

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
        listOfTechnologies = List(AdvancedMaterials())
      )
      val gV: GameValues = GameValues(
        listOfTechnologies = List(AdvancedMaterials()))
      val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
      val listBuilding: ListCommand = ListCommand(TECHNOLOGY, gsM)

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
        listOfTechnologies = List(AdvancedMaterials())
      )
      val gV: GameValues = GameValues(
        listOfTechnologies = List(AdvancedMaterials()))
      val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
      val listBuilding: ListCommand = ListCommand(TECHNOLOGY, gsM)

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
        listOfTechnologiesCurrentlyResearched = List(AdvancedMaterials())
      )
      val gV: GameValues = GameValues(
        listOfTechnologies = List(AdvancedMaterials()))
      val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
      val listBuilding: ListCommand = ListCommand(TECHNOLOGY, gsM)

      listBuilding.execute()
        .toString should be(s"==== TECHNOLOGIES ====\n - " +
        s"${AnsiColor.CYAN}Advanced Materials (already researched)${AnsiColor.RESET}\n")
    }

    "return a unit list with red colored items if the player has insufficient funds for any of them" +
      "appended by the amount of resources lacking" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder())
      val gV: GameValues = GameValues(
        listOfUnits = List(Corvette()))
      val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
      val listUnit: ListCommand = ListCommand(UNITS, gsM)

      listUnit.execute()
        .toString should be(s"==== UNITS ====\n - ${AnsiColor.RED}Corvette " +
        s"Total Lacking: [Energy: 70] [Minerals: 30]${AnsiColor.RESET}\n")
    }

    "return a unit list with red colored items if the player has sufficient funds but insufficient capacity" +
      " for any of them appended by the amount of resources lacking" in {
      val plV: PlayerValues = playervalues.PlayerValues(
        resourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(100)), capacity = Capacity(0))
      val gV: GameValues = GameValues(
        listOfUnits = List(Corvette()))
      val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
      val listUnit: ListCommand = ListCommand(UNITS, gsM)

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
      val gV: GameValues = GameValues(
        listOfUnits = List(Corvette()))
      val gsM: GameStateManager = GameStateManager(playerValues = plV, gameValues = gV)
      val listBuilding: ListCommand = ListCommand(UNITS, gsM)

      listBuilding.execute()
        .toString should be(s"==== UNITS ====\n - " +
        s"Corvette (3)\n")
    }
  }
}
