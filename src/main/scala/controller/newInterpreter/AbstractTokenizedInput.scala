package controller.newInterpreter

import controller.command.ICommand
import model.game.gamestate.GameStateManager

trait AbstractTokenizedInput[T]:

  def interpret(): T
