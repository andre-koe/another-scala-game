package controller

import com.google.inject.Guice
import controller.IController
import controller.adapters.TokenizedInputToCommandAdapter
import controller.command.commands.*
import controller.command.memento.{GameStateManagerMemento, UndoRedoManager}
import controller.command.{ICommand, IUndoable}
import controller.newInterpreter.TokenizedInput
import controller.validator.{IValidator, InputValidator, ValidationHandler}
import model.core.fileIO.IFileIOStrategy
import model.game.gamestate.gamestates.ExitedState
import model.game.gamestate.{GameStateManager, GameStateManagerWrapper, IGameState, IGameStateManager, IGameStateManagerWrapper}
import utils.{Observable, Observer}
import utils.DefaultValueProvider.{given_IGameValues, given_IFileIOStrategy}

import scala.annotation.tailrec
import scala.util.{Failure, Success}

class Controller() extends IController :

  private var gameStateManager: IGameStateManager = GameStateManager()
  private val undoRedoManager: UndoRedoManager = UndoRedoManager()

  def processInput(toDo : TokenizedInput | ICommand): Boolean =

    val executable = toDo match
      case x: TokenizedInput => TokenizedInputToCommandAdapter(x, gameStateManager).getCommand
      case x : ICommand => x
    
    gameStateManager = executable match
      case _: IUndoable => undoRedoManager.addMemento(executable)
      case _: UndoCommand => undoRedoManager.undo.getOrElse(gameStateManager.extCopy(message="undo failed"))
      case _: RedoCommand => undoRedoManager.redo.getOrElse(gameStateManager.extCopy(message="redo failed"))
      case _ => executable.execute()

    notifyObservers()
    handleGameState(gameStateManager)

  def getState: IGameStateManagerWrapper = GameStateManagerWrapper(gameStateManager)

  private def handleGameState(gsm: IGameStateManager): Boolean =
    gsm.gameState match
      case _ : ExitedState => false
      case _ => true

  override def toString: String = gameStateManager.toString


