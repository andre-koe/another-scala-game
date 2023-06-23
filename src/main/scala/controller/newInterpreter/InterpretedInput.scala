package controller.newInterpreter

import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.IGameObject

sealed trait InterpretedInput

case class InterpretedCommand(commandType: CommandType) extends InterpretedInput
case class InterpretedGameObject(gameObject: IGameObject) extends InterpretedInput
case class InterpretedKeyword(keywordType: KeywordType) extends InterpretedInput
case class InterpretedQuantity(quantity: Int) extends InterpretedInput
case class InterpretedSubcommand(subcommand: String) extends InterpretedInput
case class InterpretedUnidentified(unidentified: String) extends InterpretedInput
case class InterpretedCoordinate(coordinate: Coordinate) extends InterpretedInput