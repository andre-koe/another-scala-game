package controller.newInterpreter

import model.game.purchasable.IGameObject

sealed trait InterpretedInputToken

case class InterpretedCommand(commandType: CommandType) extends InterpretedInputToken
case class InterpretedGameObject(gameObject: IGameObject) extends InterpretedInputToken
case class InterpretedKeyword(keywordType: KeywordType) extends InterpretedInputToken
case class InterpretedQuantity(quantity: Int) extends InterpretedInputToken
case class InterpretedSubcommand(subcommand: String) extends InterpretedInputToken
case class InterpretedUnidentified(unidentified: String) extends InterpretedInputToken