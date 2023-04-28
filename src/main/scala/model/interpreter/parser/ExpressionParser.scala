package model.interpreter.parser

import controller.command.ICommand
import model.game.gamestate.GameStateManager
import model.interpreter.userexpressions.*
import model.interpreter.{IExpression, IExpressionParser}

case class ExpressionParser() extends IExpressionParser:

  override def parse(str: String): IExpression[GameStateManager, ICommand] = expressionMatcher(str)
  private def expressionMatcher(str: String): IExpression[GameStateManager, ICommand] =
    inputTokenizer(str) match
      case ("help" | "h") :: params => HelpExpression(params)
      case "research" :: params => ResearchExpression(params)
      case "build" :: params => BuildExpression(params)
      case "recruit" :: params => RecruitExpression(params)
      case "move" :: params => MoveExpression(params)
      case "show" :: params => ShowExpression(params)
      case "save" :: params => SaveExpression(params)
      case "load" :: params => LoadExpression(params)
      case "list" :: params => ListExpression(params)
      case "sell" :: params => SellExpression(params)
      case "" :: Nil => EmptyExpression()
      case "done" :: Nil => EndRoundExpression()
      case ("y"|"yes"|"n"|"no") :: _ => UserResponseExpression(inputTokenizer(str))
      case ("exit" | "quit") :: Nil => EndGameExpression()
      case _ => InvalidExpression(inputTokenizer(str))
  private def inputTokenizer(str: String): List[String] = str.strip().toLowerCase.split(" ").toList