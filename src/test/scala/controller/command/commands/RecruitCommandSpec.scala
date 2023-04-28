package controller.command.commands

import model.game.{Capacity, PlayerValues}
import model.game.gamestate.GameStateManager
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class RecruitCommandSpec extends AnyWordSpec {
  "The RecruitCommand" should {
    val gameStateManager: GameStateManager = GameStateManager()
    "return a new GameState" when {
      val commandEmpty: RecruitCommand = RecruitCommand("", gameStateManager)
      val commandHelp: RecruitCommand = RecruitCommand("help", gameStateManager)
      "initialized with an empty or 'help' input string should " +
        "return GameStateManager with 'recruit help' string rep" in {

        commandEmpty.execute() should be(commandHelp.execute())
        commandEmpty.execute()
          .toString should be("recruit <unit name> (quantity) if quantity is omitted default 1 will be used" +
          " - Enter list units for an overview of all available units")
        commandHelp.execute()
          .toString should be("recruit <unit name> (quantity) if quantity is omitted default 1 will be used" +
          " - Enter list units for an overview of all available units")
      }
      "initialized with a valid input string without quantity should return a GameStateManager " +
        "with nonempty player listOfUnitsUnderConstruction and string output" in {
        val plV: PlayerValues = PlayerValues(
          resourceHolder = ResourceHolder(
            energy = Energy(200),
            minerals = Minerals(200),
            alloys = Alloys(200)
          ), capacity = Capacity(100))
        val gsM: GameStateManager = GameStateManager(playerValues = plV)
        val commandValid: RecruitCommand = RecruitCommand("Cruiser", gsM)
        commandValid.execute().playerValues.listOfUnitsUnderConstruction should not be empty
        commandValid.execute().toString should be("Beginning construction of 1 x Cruiser " +
          "for Total Cost: [Energy: 150] [Minerals: 100] [Alloys: 75], completion in 4 rounds.")
      }
      "initialized with a valid input string with quantity should return a GameStateManager " +
        "with nonempty player listOfUnitsUnderConstruction and string output" in {
        val plV: PlayerValues = PlayerValues(
          resourceHolder = ResourceHolder(
            energy = Energy(800),
            minerals = Minerals(800),
            alloys = Alloys(800)
          ), capacity = Capacity(100))
        val gsM: GameStateManager = GameStateManager(playerValues = plV)
        val commandValid: RecruitCommand = RecruitCommand("Cruiser 4", gsM)
        commandValid.execute().playerValues.listOfUnitsUnderConstruction should not be empty
        commandValid.execute().playerValues.listOfUnitsUnderConstruction.length should be(4)
        commandValid.execute().toString should be("Beginning construction of 4 x Cruiser " +
          "for Total Cost: [Energy: 600] [Minerals: 400] [Alloys: 300], completion in 4 rounds.")
      }
      "initialized with an valid input string but insufficient player funds return the appropriate response" in {
        val playerValue: PlayerValues =
          PlayerValues(resourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(100), alloys = Alloys(10)), capacity = Capacity(100))
        val gameStateManager: GameStateManager = GameStateManager(playerValues = playerValue)
        val commandInvalid: RecruitCommand = RecruitCommand("Cruiser 4", gameStateManager)
        commandInvalid.execute()
          .toString should be("Insufficient Funds --- Total Lacking: [Energy: 500] [Minerals: 300] [Alloys: 290].")
      }
      "initialized with a valid unit but invalid quantity" +
        " input string should return an invalid response as GameStateManager.toString" in {
        val commandInvalid: RecruitCommand = RecruitCommand("Cruiser drei", gameStateManager)
        commandInvalid.execute()
          .toString should be("recruit: 'Cruiser drei' - invalid\nEnter help to get an overview of all available commands")
      }
      "initialized with an invalid input string should return an invalid response as GameStateManager.toString" in {
        val commandInvalid: RecruitCommand = RecruitCommand("Testunit", gameStateManager)
        commandInvalid.execute()
          .toString should be("A unit with name 'Testunit' does not exist, use 'list units' to get an overview of all available units")
      }
      "initialized with an invalid input and too many parameters should " +
        "return an invalid response as GameStateManager.toString" in {
        val commandInvalid: RecruitCommand = RecruitCommand(null, gameStateManager)
        commandInvalid.execute()
          .toString should be("recruit: 'null' - " +
          "invalid\nEnter help to get an overview of all available commands")
      }

    }
  }
}
