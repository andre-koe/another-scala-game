package controller

import model.game.gamestate.{GameStateManager, IGameState}
import model.game.gamestate.gamestates.ExitedState
import model.interpreter.IExpressionParser
import model.interpreter.parser.ExpressionParser
import utils.{Observable, Observer}

import scala.annotation.tailrec

class Controller extends Observable {

  private var gameStateManager: GameStateManager = GameStateManager()
  private val expressionParser: IExpressionParser = ExpressionParser()

  def processInput(str: String): Boolean =
    gameStateManager = expressionParser.parse(str).interpret(gameStateManager).execute()
    notifyObservers()
    gameStateManager.gameState.isInstanceOf[ExitedState]

  override def toString: String = gameStateManager.toString

}
