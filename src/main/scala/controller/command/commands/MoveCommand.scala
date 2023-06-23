package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.core.board.sector.ISector
import model.core.board.boardutils.Coordinate
import model.core.mechanics.fleets.components.units.IUnit
import model.game.gamestate.IGameStateManager

case class MoveCommand(string: List[String], gameStateManager: IGameStateManager) extends ICommand, IUndoable:

  override def toString: String = "move <unit name> | <fleet name> to <sector coordinates>" +
    " - Move the specified unit or fleet to the specified coordinate arrival time (in rounds) depends on the" +
    " distance between your base and the target"

  override def execute(): IGameStateManager =
    string match
      case ("help" | "") :: Nil => gameStateManager.message(this.toString)
      case _ => gameStateManager.move("", Coordinate(0,0))
/*
private def handleInput(list: List[String]): IGameStateManager =
  val unit: Option[IUnit] = findUnit(list.head)
  val sector: Option[Sector] = findSector(list.tail.head)
  gameStateManager.message("Not implemented")
private def findUnit(name: String): Option[IUnit] =
  gameStateManager.playerValues.listOfUnits.find(_.name == name)
private def findSector(name: String): Option[Sector] =
  gameStateManager.gameMap.findSector(name)
*/