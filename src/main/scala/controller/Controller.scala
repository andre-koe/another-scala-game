package controller

import utils.{Observable, Observer}
import controller.playeractions.ActionType.*
import controller.playeractions.ActionType
import model.game.gamestate.{GameState, GameStateManager}

class Controller extends Observable {
  private var gameStateManager: GameStateManager = GameStateManager()

  def inputSanitation(str: String): List[String] = str.strip().toLowerCase.split(" ").toList
  def processInput(str: String): GameState =
    val action: ActionType = mapToAction(inputSanitation(str), str)
    gameStateManager = gameStateManager.handlePlayerAction(action)
    notifyObservers()
    gameStateManager.gameState

  def mapToAction(str: List[String], input: String): ActionType =
    str match
      case "build" :: tail => buildHandler(tail, input)
      case "research" :: tail => researchHandler(tail, input)
      case "recruit" :: tail => recruitmentHandler(tail, input)
      case "move" :: tail => playerMovementHandler(tail, input)
      case "save" :: tail => saveGameHandler(tail, input)
      case "load" :: tail => loadGameHandler(tail, input)
      case "show" :: tail => showHandler(tail, input)
      case "list" :: tail => listHandler(tail, input)
      case "sell" :: tail => sellHandler(tail, input)
      case "done" :: Nil => FINISH_ROUND_REQUEST
      case ("no" | "n") :: Nil => USER_DECLINE
      case ("yes" | "y") :: Nil => USER_ACCEPT
      case "" :: Nil => EMPTY
      case ("help" | "h") :: Nil => HELP
      case ("exit" | "quit") :: Nil => EXIT
      case _ => invalidInputHandler(None, input, "")

  private def buildHandler(args: List[String], input: String): ActionType =
    args match
      case arg :: Nil => BUILD(Option(arg))
      case _ =>
        val message: String = "build requires exactly 1 argument <building name>"
        invalidInputHandler(Option("build"), input, message)

  private def researchHandler(args: List[String], input: String): ActionType =
    args match
      case arg :: Nil => RESEARCH(Option(arg))
      case _ =>
        val message: String = "research requires exactly 1 argument <technology name>"
        invalidInputHandler(Option("research"), input, message)

  private def recruitmentHandler(args: List[String], input: String): ActionType =
    args match
      case arg1 :: Nil => RECRUIT(Option(arg1), None)
      case arg1 :: arg2 :: Nil => RECRUIT(Option(arg1), Option(arg2))
      case _ =>
        val message: String
        = "recruit requires exactly 1 argument <unit name> and takes 1 optional parameter " +
          "<quantity> if quantity is omitted the default of 1 will be used"
        invalidInputHandler(Option("recruit"), input, message)

  private def playerMovementHandler(args: List[String], input: String): ActionType =
    args match
      case arg1 :: arg2 :: Nil => MOVE(Option(arg1), Option(arg2))
      case _ =>
        val message: String = "move requires exactly 2 positional arguments <x> <y>"
        invalidInputHandler(Option("move"), input, message)

  private def saveGameHandler(args: List[String], input: String): ActionType =
    args match
      case Nil => SAVE(None)
      case arg :: Nil => SAVE(Option(arg))
      case _ =>
        val message: String = "save can have exactly 1 optional parameter <filename>"
        invalidInputHandler(Option("save"), input, message)

  private def loadGameHandler(args: List[String], input: String): ActionType =
    args match
      case Nil => LOAD(None)
      case arg :: Nil => LOAD(Option(arg))
      case _ =>
        val message: String = "load can have exactly 1 optional parameter <filename>"
        invalidInputHandler(Option("load"), input, message)

  private def showHandler(args: List[String], input: String): ActionType =
    args match
      case arg :: Nil => SHOW(Option(arg))
      case _ =>
        val message: String = "show has exactly 1 parameter <overview | buildings | units | technology>"
        invalidInputHandler(Option("show"), input, message)

  private def listHandler(args: List[String], input: String): ActionType =
    args match
      case arg :: Nil => LIST(Option(arg))
      case _ =>
        val message: String = "list has exactly 1 parameter <buildings | units | technology>"
        invalidInputHandler(Option("list"), input, message)

  private def sellHandler(args: List[String], input: String): ActionType = {
    args match
      case arg :: Nil => SELL(Option(arg))
      case _ =>
        val message: String = "sell has exactly 1 parameter <name>"
        invalidInputHandler(Option("sell"), input, message)
  }

  private def invalidInputHandler(name: Option[String], input: String, message: String): ActionType =
    if name.isDefined
    then INVALID(Option(f"${name.get}: $message\nprovided: '$input'"))
    else INVALID(Option(f"Unknown Input: '$input'"))

  override def toString: String = gameStateManager.toString

}
