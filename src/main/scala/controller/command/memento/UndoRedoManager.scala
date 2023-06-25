package controller.command.memento

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

import scala.util.{Failure, Success}

case class UndoRedoManager():

  private var undoMementos = List.empty[ICommand]
  private var redoMementos = List.empty[ICommand]

  def addMemento(c: ICommand): IGameStateManager =
    undoMementos ::= c
    redoMementos = List()
    c.execute()


  def undo: Option[IGameStateManager] = undoMementos match
    case head :: tail =>
      undoMementos = tail
      redoMementos ::= head
      Some(head.gameStateManager)
    case _ => None


  def redo: Option[IGameStateManager] = redoMementos match
    case head :: tail =>
      redoMementos = tail
      undoMementos ::= head
      Some(head.execute())
    case _ => None




