package controller.newInterpreter
import model.game.gamestate.GameStateManager

case class SubcommandExpression(str: String) extends AbstractExpression[String]:

  override def interpret(): String = str.substring(1)
