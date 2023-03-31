package view.tui

import controller.Controller
import model.game.gamestate.GameState.{EXITED, INIT}
import model.game.gamestate.GameState
import utils.Observer

import scala.annotation.tailrec
import scala.io.StdIn

case class Tui(controller: Controller) extends Observer {

  def gameTitle: String = "TBD"

  def introductionMessage: String =
    f"""Welcome to $gameTitle
       | This is an introduction
       |""".stripMargin

  def run(): GameState = {
    controller.registerObserver(this)

    println(gameTitle + "\n" + introductionMessage)

    @tailrec
    def runGameLoop(gameState: GameState): GameState = {
      gameState match
        case EXITED => endGame(gameState)
        case _ =>
          val input = StdIn.readLine(">>> ")
          val state = controller.processInput(input)
          runGameLoop(state)
    }
    runGameLoop(INIT)
  }

  private def endGame(exitState: GameState): GameState =
    controller.removeObserver(this)
    exitState

  override def update(): Unit = println(controller.toString)

}
