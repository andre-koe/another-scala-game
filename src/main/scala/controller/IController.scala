package controller

import controller.command.ICommand
import controller.newInterpreter.TokenizedInput
import model.game.gamestate.IGameStateManagerWrapper
import utils.{Observable, Observer}

trait IController extends Observable:

  def processInput(i: TokenizedInput | ICommand): Boolean

  def getState: IGameStateManagerWrapper

