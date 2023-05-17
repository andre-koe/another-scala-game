package controller.newInterpreter
import model.game.gamestate.GameStateManager

case class SubcommandTokenizedInput(str: String) extends AbstractTokenizedInput[String]:

  override def interpret(): String = str.substring(1)
