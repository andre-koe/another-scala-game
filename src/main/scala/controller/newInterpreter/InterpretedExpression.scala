package controller.newInterpreter

import model.game.purchasable.IGameObject

sealed trait InterpretedExpression

case class InterpretedCommand(commandType: CommandType) extends InterpretedExpression
case class InterpretedGameObject(gameObject: IGameObject) extends InterpretedExpression
case class InterpretedKeyword(keywordType: KeywordType) extends InterpretedExpression
case class InterpretedQuantity(quantity: Int) extends InterpretedExpression
case class InterpretedSubcommand(subcommand: String) extends InterpretedExpression
case class InterpretedUnidentified(unidentified: String) extends InterpretedExpression