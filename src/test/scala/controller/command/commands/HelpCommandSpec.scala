package controller.command.commands

import model.core.gameobjects.purchasable.building.Mine
import model.core.gameobjects.purchasable.technology.AdvancedMaterials
import model.core.gameobjects.purchasable.units.Battleship
import utils.DefaultValueProvider.given_IGameValues
import model.game.gamestate.{GameStateManager, GameStateStringFormatter}
import model.game.gamestate.enums.help.HelpContext
import model.game.gamestate.enums.help.HelpContext
import model.game.gamestate.enums.help.HelpContext.{BUILDING, GENERAL, SPECIFIC, TECHNOLOGY, UNIT}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class HelpCommandSpec extends AnyWordSpec {

  "The HelpCommand" should {
    "be used to get information about different aspects or components of the game" when {

      val gameStateManager: GameStateManager = GameStateManager()

      "used without parameter the general help menu should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand(GENERAL, gameStateManager)
        helpCommand.execute().toString should be(
          f"""The following actions should be implemented:
             | > [overview] | to get an overview of your current account balance/research points
             | > [build] <building name> | to begin construction of a building
             | > [recruit] <unit name> (quantity) | to begin recruitment of 1 or (quantity) units
             | > [research] <technology name> | to begin researching a technology
             | > [done] | to end the current round
             | > [help | h] | to show this menu
             | > [exit | quit] to quit
             | > [undo] to undo the last action
             | > [redo] to undo the last redo""".stripMargin)
      }

      "used with input building an information about buildings should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand(BUILDING, gameStateManager)
        helpCommand.execute()
          .toString should be("A building can impact the game in various ways, " +
          "such as increasing research output, providing energy, or increasing unit capacity.")
      }

      "used with input technology an information about technologies should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand(TECHNOLOGY, gameStateManager)
        helpCommand.execute()
          .toString should be("A technology in the game that can be researched by " +
          "players to unlock new abilities, units, or buildings.")
      }

      "used with input unit an information about units should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand(UNIT, gameStateManager)
        helpCommand.execute()
          .toString should be("A unit can be used to fight over sectors and conquer new sectors.")
      }

      "used with a specific unit name such as battleship an information about " +
        "this unit should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand(SPECIFIC, gameStateManager, Option(Battleship()))
        helpCommand.execute()
          .toString should be("Battleship")
      }

      "used with a specific technology name such as advanced materials an information about " +
        "this technology should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand(SPECIFIC, gameStateManager, Option(AdvancedMaterials()))
        helpCommand.execute()
          .toString should be("Advanced Materials")
      }

      "used with a specific building name such as mine an information about " +
        "this building should be loaded as GameState" in {
        val helpCommand: HelpCommand = HelpCommand(SPECIFIC, gameStateManager, Option(Mine()))
        helpCommand.execute()
          .toString should be("Mine")
      }
    }
  }

}
