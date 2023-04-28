package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.GameStateManager

import scala.io.AnsiColor

case class InvalidCommand(params: String, gsm: GameStateManager) extends ICommand:
  override def execute(): GameStateManager = 
    gsm.invalid(AnsiColor.RED + s"Invalid Input: '${params.mkString(" ")}'" + AnsiColor.RESET)
