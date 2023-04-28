package model.interpreter

import controller.command.ICommand
import model.game.gamestate.GameStateManager

trait IExpression[T,A]:
  def interpret(gsm: T): A