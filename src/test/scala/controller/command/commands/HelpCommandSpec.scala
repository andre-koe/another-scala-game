package controller.command.commands

import model.game.gamestate.{GameStateManager, IGameStateManager, GameStateStringFormatter, GameState}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class HelpCommandSpec extends AnyWordSpec {

  "The HelpCommand" should {
    "be used to get information about different aspects or components of the game" when {
      val gameStateManager: IGameStateManager = GameStateManager()
      "used without parameter the general help menu should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand("", gameStateManager)
        helpCommand.execute().toString should be(GameStateStringFormatter().helpResponse)
        helpCommand.execute().gameState should be(GameState.RUNNING)
      }
      "used with input building an information about buildings should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand("building", gameStateManager)
        helpCommand.execute()
          .toString should be("A building can impact the game in various ways, such as increasing research output, " +
        "providing energy, or increasing unit capacity.")
        helpCommand.execute().gameState should be(GameState.RUNNING)
      }
      "used with input technology an information about technologies should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand("technology", gameStateManager)
        helpCommand.execute()
          .toString should be("A technology in the game that can be researched by " +
          "players to unlock new abilities, units, or buildings.")
        helpCommand.execute().gameState should be(GameState.RUNNING)
      }
      "used with input unit an information about units should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand("unit", gameStateManager)
        helpCommand.execute()
          .toString should be("A unit can be used to fight over sectors and conquer new sectors.")
        helpCommand.execute().gameState should be(GameState.RUNNING)
      }
      "used with an specific unit name such as battleship an information about " +
        "this unit should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand("battleship", gameStateManager)
        helpCommand.execute()
          .toString should be("The Battleship is a heavily armed and armored naval unit that can deal " +
        "massive damage to enemy ships and structures from a distance.")
        helpCommand.execute().gameState should be(GameState.RUNNING)
      }
      "used with an specific technology name such as advanced materials an information about " +
        "this technology should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand("advanced materials", gameStateManager)
        helpCommand.execute()
          .toString should be("Advanced Materials")
        helpCommand.execute().gameState should be(GameState.RUNNING)
      }
      "used with an specific building name such as mine an information about " +
        "this building should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand("mine", gameStateManager)
        helpCommand.execute()
          .toString should be("The Mine extracts minerals which are used to produce alloys.")
        helpCommand.execute().gameState should be(GameState.RUNNING)
      }
      "used with an unknown input lol an information about the absence of information should loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand("lol", gameStateManager)
        helpCommand.execute()
          .toString should be("Could not find any information on 'lol'")
        helpCommand.execute().gameState should be(GameState.RUNNING)
      }
    }
  }

}
