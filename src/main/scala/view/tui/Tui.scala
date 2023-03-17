package view.tui

import controller.Controller
import controller.gamestate.GameState
import utils.Observer

import scala.annotation.tailrec
import scala.io.StdIn

case class Tui(controller: Controller) extends Observer {
  def gameTitle: String = "TBD"
  def introductionMessage: String =
    f"""Welcome to $gameTitle
      | This is an introduction
      |""".stripMargin
  def helpMessage: String =
    f"""The following actions should be implemented:
      | > [build] <building name> | help to get an overview of all available buildings
      | > [recruit] <unit name> | help to get an overview of all available units
      | > [research] <technology name> | help to get an overview of all available technologies
      | > [exit] to quit
      | > [go back] to undo the last action
      |""".stripMargin

  def goodbyeMessage: String = f"Goodbye!"
  def undefinedUserInputResponse(input: String): String = input.length match
    case 0 => "Enter 'h' to get an overview of all commands\n"
    case _ => f"'$input' is unknown enter 'h' for an overview of all commands\n"


  def run(): Unit = {
    controller.registerObserver(this)
    print(introductionMessage)
    getInput()
    @tailrec
    def getInput(): Unit = {
      val input = StdIn.readLine(">>> ")
      controller.processInput(input)
      update()
      controller.getState match
        case GameState.RUNNING => getInput()
        case GameState.HELP_REQUEST =>
          println(helpMessage)
          getInput()
        case GameState.UNDEFINED_USER_INPUT =>
          println(undefinedUserInputResponse(input))
          getInput()
        case GameState.END_ROUND =>
        case _ =>
    }
    println(goodbyeMessage)
  }

  @Override
  def update(): Unit = {
    // TODO: Implement the necessary functionality in the Controller and call it here
  }
}
