package controller.newInterpreter

import controller.command.ICommand
import model.game.gamestate.GameStateManager

trait AbstractExpression[T]:

  def interpret(): T
