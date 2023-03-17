package controller

import utils.{Observable, Observer}
import controller.playeractions.ActionType.{BUILD, EMPTY, EXIT, INVALID, RECRUIT, RESEARCH}
import controller.GameManager
import controller.gamestate.GameState
import controller.gamestate.GameState.{EXITED, RUNNING, UNDEFINED_USER_INPUT}
import controller.playeractions.ActionType

class Controller extends Observable {
  private var state: GameState = GameState.RUNNING
  private val gameManager: GameManager = GameManager(0)

  def processInput(str: String): Unit = {
    val currentInput: Array[String] = str.strip().toLowerCase.split(" ")
    val action: ActionType = mapToAction(currentInput)
    action match
      case ActionType.EXIT => state = GameState.EXITED
      case ActionType.INVALID => state = GameState.UNDEFINED_USER_INPUT
      case ActionType.HELP => state = GameState.HELP_REQUEST
      case ActionType.FINISH_ROUND => 
        state = GameState.END_ROUND
        gameManager.nextRound()
      case _ =>
        state = GameState.RUNNING
  }

  private def mapToAction(str: Array[String]): ActionType = {
    str match
      case Array("build", arg) => ActionType.BUILD(arg)
      case Array("research", arg) => ActionType.RESEARCH(arg)
      case Array("recruit", arg1, arg2) => ActionType.RECRUIT(arg1, Option(arg2))
      case Array("exit") => ActionType.EXIT
      case Array("help"|"h") => ActionType.HELP
      case Array("done") => ActionType.FINISH_ROUND
      case _ => ActionType.INVALID
  }

  def getState: GameState = state

}
