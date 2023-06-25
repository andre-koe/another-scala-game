package controller.command.commands

import controller.command.ICommand
import model.core.gameobjects.purchasable.IGameObject
import model.core.utilities.GameValues
import model.game.gamestate.IGameStateManager
import model.game.gamestate.enums.help.HelpContext
import model.game.gamestate.enums.help.HelpContext._
import model.utils.GameObjectUtils

case class HelpCommand(context: HelpContext, gameStateManager: IGameStateManager, value: Option[IGameObject] = None) extends ICommand:
  override def execute(): IGameStateManager =
    context match
      case BUILDING => gameStateManager.message(helpBuilding)
      case TECHNOLOGY => gameStateManager.message(helpTech)
      case UNIT => gameStateManager.message(helpUnit)
      case GENERAL => gameStateManager.message(defaultHelpResponse)
      case SPECIFIC => gameStateManager.message(value.get.toString)

  private def helpBuilding: String = "A building can impact the game in various ways, " +
    "such as increasing research output, providing energy, or increasing unit capacity."

  private def helpTech: String = "A technology in the game that can be researched by " +
    "players to unlock new abilities, units, or buildings."

  private def helpUnit: String = "A unit can be used to fight over sectors and conquer new sectors."

  private def defaultHelpResponse: String =
    f"""The following actions should be implemented:
       | > [overview] | to get an overview of your current account balance/research points
       | > [build] <building name> | to begin construction of a building
       | > [recruit] <unit name> (quantity) | to begin recruitment of 1 or (quantity) units
       | > [research] <technology name> | to begin researching a technology
       | > [done] | to end the current round
       | > [help | h] | to show this menu
       | > [exit | quit] to quit
       | > [undo] to undo the last action
       | > [redo] to undo the last redo""".stripMargin

  
