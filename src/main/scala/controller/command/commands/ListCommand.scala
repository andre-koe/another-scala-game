package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager
import model.purchasable.types.EntityType.{TECHNOLOGY, UNIT, BUILDING}

case class ListCommand(string: String, gameStateManager: IGameStateManager) extends ICommand:
    override def toString: String =
        "The list command with optional parameter (units/buildings/technologies) will list all available Game Objects " +
          "according to input, if omitted everything will be listed.\nEnter list help to see this message"

    override def execute(): IGameStateManager =
        if string.isBlank then gameStateManager.list(None) else
            string match
                case "technology" | "technologies" | "tech" => gameStateManager.list(Option(TECHNOLOGY))
                case "units" | "unit" => gameStateManager.list(Option(UNIT))
                case "buildings" | "building" => gameStateManager.list(Option(BUILDING))
                case "help" => gameStateManager.message(this.toString)
                case _ => gameStateManager.invalid(s"list: '$string'")