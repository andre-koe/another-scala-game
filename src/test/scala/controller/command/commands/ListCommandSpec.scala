package controller.command.commands

import model.game.{GameValues, PlayerValues}
import model.game.gamestate.{GameState, GameStateManager, GameStateStringFormatter, IGameStateManager}
import model.purchasable.building.ResearchLab
import model.purchasable.technology.AdvancedMaterials
import model.purchasable.units.Corvette
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import scala.io.AnsiColor

class ListCommandSpec extends AnyWordSpec {

  "The ListCommand" should {
    "return a new toString representation depending on the input" when {
      val gameValues: GameValues = GameValues()
      "asked for list building(s) should list all Buildings" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listBuilding: ListCommand = ListCommand("building", gameStateManager)

        listBuilding.execute().gameState should be(GameState.RUNNING)
        listBuilding.execute()
          .toString.split("\n").length should be(gameValues.listOfBuildings.length + 1)
      }
      "asked for list technology/tech/technologies should list all Technologies" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listTech: ListCommand = ListCommand("tech", gameStateManager)

        listTech.execute().gameState should be(GameState.RUNNING)
        listTech.execute().toString.split("\n").length should be(gameValues.listOfTechnologies.length + 1)
      }
      "asked for list unit(s) should list all Units" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listUnit: ListCommand = ListCommand("unit", gameStateManager)

        listUnit.execute().gameState should be(GameState.RUNNING)
        listUnit.execute().toString.split("\n").length should be(gameValues.listOfUnits.length + 1)
      }
      "asked for list help should return an explanatory string as gamestate.tostring" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listHelp: ListCommand = ListCommand("help", gameStateManager)

        listHelp.execute().gameState should be(GameState.RUNNING)
        listHelp.execute()
          .toString should be("The list command with optional parameter (units/buildings/technologies) " +
          "will list all available Game Objects according to input, if omitted everything will be listed." +
          "\nEnter list help to see this message")
      }
      "asked for list with empty input should list everything" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listAll: ListCommand = ListCommand("", gameStateManager)

        listAll.execute().gameState should be(GameState.RUNNING)
        listAll.execute()
          .toString.split("\n").length should be(gameValues.listOfTechnologies.length
          + gameValues.listOfUnits.length + gameValues.listOfBuildings.length + 3)
      }
      "asked for list invalid should return an invalid input response as GameState.toString" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listInvalid: ListCommand = ListCommand("test", gameStateManager)

        listInvalid.execute().gameState should be(GameState.RUNNING)
        listInvalid.execute().toString should be(GameStateStringFormatter().invalidInputResponse("list: 'test'"))
      }
    }
    "return a building list with red colored items if the player has insufficient funds for any of them" +
        "appended by the amount of resources lacking" in {
        val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder())
        val gV: GameValues = GameValues(
          listOfBuildings = List(ResearchLab()),
          listOfUnits = List(Corvette()),
          listOfTechnologies = List(AdvancedMaterials()))
        val gsM: IGameStateManager = GameStateManager(playerValues = plV)
        val listBuilding: ListCommand = ListCommand("building", gsM, gV)

        listBuilding.execute()
          .toString should be (s"==== Buildings ====\n - ${AnsiColor.RED}Research Lab " +
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
      val gsM: IGameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand = ListCommand("building", gsM, gV)

      listBuilding.execute()
        .toString should be(s"==== Buildings ====\n - " +
        s"Research Lab\n")
    }
    "return a technology list with red colored items if the player has insufficient funds for any of them" +
      "appended by the amount of resources lacking" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder())
      val gV: GameValues = GameValues(
        listOfTechnologies = List(AdvancedMaterials()))
      val gsM: IGameStateManager = GameStateManager(playerValues = plV)
      val listTech: ListCommand = ListCommand("tech", gsM, gV)

      listTech.execute()
        .toString should be(s"==== Technologies ====\n - ${AnsiColor.RED}Advanced Materials " +
        s"Total Lacking: [Research Points: 200]${AnsiColor.RESET}\n")
    }
    "return a technology list with uncolored items if the player has sufficient funds" in {
      val plV: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          researchPoints = ResearchPoints(200)
        ))
      val gV: GameValues = GameValues(
        listOfTechnologies = List(AdvancedMaterials()))
      val gsM: IGameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand = ListCommand("tech", gsM, gV)

      listBuilding.execute()
        .toString should be(s"==== Technologies ====\n - " +
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
      val gsM: IGameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand = ListCommand("tech", gsM, gV)

      listBuilding.execute()
        .toString should be(s"==== Technologies ====\n - " +
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
      val gsM: IGameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand = ListCommand("tech", gsM, gV)

      listBuilding.execute()
        .toString should be(s"==== Technologies ====\n - " +
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
      val gsM: IGameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand = ListCommand("tech", gsM, gV)

      listBuilding.execute()
        .toString should be(s"==== Technologies ====\n - " +
        s"${AnsiColor.CYAN}Advanced Materials (already researched)${AnsiColor.RESET}\n")
    }
    "return a unit list with red colored items if the player has insufficient funds for any of them" +
      "appended by the amount of resources lacking" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder())
      val gV: GameValues = GameValues(
        listOfUnits = List(Corvette()))
      val gsM: IGameStateManager = GameStateManager(playerValues = plV)
      val listUnit: ListCommand = ListCommand("unit", gsM, gV)

      listUnit.execute()
        .toString should be(s"==== Units ====\n - ${AnsiColor.RED}Corvette " +
        s"Total Lacking: [Energy: 70] [Minerals: 30]${AnsiColor.RESET}\n")
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
      val gsM: IGameStateManager = GameStateManager(playerValues = plV)
      val listBuilding: ListCommand = ListCommand("units", gsM, gV)

      listBuilding.execute()
        .toString should be(s"==== Units ====\n - " +
        s"Corvette (3)\n")
    }
  }
}
