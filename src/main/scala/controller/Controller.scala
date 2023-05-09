package controller

import controller.command.ICommand
import controller.validator.{IValidator, InputValidator, ValidationHandler}
import controller.newInterpreter.CombinedExpression
import model.game.gamestate.{GameStateManager, IGameState}
import model.game.gamestate.gamestates.ExitedState
import utils.{Observable, Observer}

import scala.annotation.tailrec

class Controller() extends Observable {

  private var gameStateManager: GameStateManager = GameStateManager()

  def processInput(toDo : CombinedExpression | ICommand): Boolean =

    val executable = toDo match
      case x: CombinedExpression => CombinedExpressionAdapter(x, gameStateManager)
      case x : ICommand => x

    gameStateManager = executable.execute()

    notifyObservers()
    handleGameState(gameStateManager)

  private def handleGameState(gsm: GameStateManager): Boolean =
    gsm.gameState match
      case _ : ExitedState => false
      case _ => true

  override def toString: String = gameStateManager.toString

}
