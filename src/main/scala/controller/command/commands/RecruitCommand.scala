package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.{GameValues, IValues}
import model.game.gamestate.IGameStateManager

case class RecruitCommand(string: String,
                          gameStateManager: IGameStateManager,
                          gameValues: IValues = GameValues()) extends ICommand, IUndoable:
  override def execute(): IGameStateManager =
    formatInput(string) match
      case Some((str, q)) => gameStateManager.recruit(str, q)
      case _ =>
        string match
          case "help" | "" => gameStateManager.message(this.toString)
          case _ => gameStateManager.invalid(s"recruit: '$string'")
  override def toString: String =
    "recruit <unit name> (quantity) if quantity is omitted default 1 will be used" +
      " - Enter list units for an overview of all available units"

  private def formatInput(str: String): Option[(String, Int)] =
    val input = if str != null then str.split(" ").toList else null
    input match
      case str :: tail =>
        val unitExists = gameValues.listOfUnits.exists(_.name.toLowerCase == str.toLowerCase())
        if (unitExists)
          val quantity = tail match
            case q :: Nil if q.toIntOption.isDefined => q.toInt
            case Nil => 1
            case _ => return None
          Some((str, quantity))
        else
          None
      case _ => None
