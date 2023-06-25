package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

import scala.io.AnsiColor

case class InvalidCommand(params: String, gameStateManager: IGameStateManager) extends ICommand:
  override def execute(): IGameStateManager = 
    gameStateManager.invalid(AnsiColor.RED + s"Invalid Input: '$params'" + AnsiColor.RESET)
