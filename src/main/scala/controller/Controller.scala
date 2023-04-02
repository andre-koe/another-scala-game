package controller

import controller.command.ICommand
import controller.command.commands.{BuildCommand, EmptyCommand, EndGameCommand, EndRoundCommand, HelpCommand, InvalidCommand, ListCommand, LoadCommand, MoveCommand, RecruitCommand, ResearchCommand, SaveCommand, SellCommand, ShowCommand, UserResponseCommand}
import utils.{Observable, Observer}
import controller.playeractions.ActionType.*
import controller.playeractions.ActionType
import model.game.gamestate.{GameState, GameStateManager, IGameStateManager}

class Controller extends Observable {
  private var gameStateManager: IGameStateManager = GameStateManager()

  def inputSanitation(str: String): List[String] =
    str.strip().toLowerCase.split(" ").toList
  def processInput(str: String): GameState =
    val command: ICommand = mapToCommand(inputSanitation(str), str)
    gameStateManager = command.execute()
    notifyObservers()
    gameStateManager.gameState

  def mapToCommand(str: List[String], string: String): ICommand =
    str match
      case "build" :: tail => BuildCommand(tail.mkString(" "), gameStateManager)
      case "research" :: tail => ResearchCommand(tail.mkString(" "), gameStateManager)
      case "recruit" :: tail => RecruitCommand(tail.mkString(" "), gameStateManager)
      case "move" :: tail => MoveCommand(tail.mkString(" "), gameStateManager)
      case "save" :: tail => SaveCommand(tail.mkString(" "), gameStateManager)
      case "load" :: tail => LoadCommand(tail.mkString(" "), gameStateManager)
      case "show" :: tail => ShowCommand(tail.mkString(" "), gameStateManager)
      case "list" :: tail => ListCommand(tail.mkString(" "), gameStateManager)
      case "sell" :: tail => SellCommand(tail.mkString(" "), gameStateManager)
      case ("help" | "h") :: tail => HelpCommand(tail.mkString(" "), gameStateManager)
      case ("no" | "n" | "y" | "yes") :: Nil => UserResponseCommand(string, gameStateManager)
      case "done" :: Nil => EndRoundCommand(gameStateManager)
      case "" :: Nil => EmptyCommand(gameStateManager)
      case ("exit" | "quit") :: Nil => EndGameCommand(gameStateManager)
      case _ => InvalidCommand(string, gameStateManager)
  override def toString: String = gameStateManager.toString

}
