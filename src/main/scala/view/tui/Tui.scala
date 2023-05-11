package view.tui

import controller.Controller
import controller.newInterpreter.ExpressionParser
import controller.validator.ValidationHandler
import utils.Observer

import scala.annotation.tailrec
import scala.io.StdIn

case class Tui(controller: Controller) extends Observer {

  def gameTitle: String = "TBD"

  def introductionMessage: String =
    f"""Welcome to $gameTitle
       | This is an introduction
       |""".stripMargin

  def run(): Boolean = {
    controller.registerObserver(this)
    println(gameTitle + "\n" + introductionMessage)
    
    @tailrec
    def runGameLoop(running: Boolean): Boolean = {
      if running then
        val input = StdIn.readLine(">>> ")
        val state = controller.processInput(ExpressionParser().parseInput(input))
        runGameLoop(state)
      else
        endGame(false)
    }
    runGameLoop(true)
  }

  private def endGame(isRunning: Boolean): Boolean =
    controller.removeObserver(this)
    isRunning

  override def update(): Unit = println(controller.toString)

}
