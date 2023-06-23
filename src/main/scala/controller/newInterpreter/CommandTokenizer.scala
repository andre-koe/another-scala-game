package controller.newInterpreter

import controller.newInterpreter.InterpretedInput
import controller.newInterpreter.InterpretedUnidentified
import controller.newInterpreter.tokens.{CommandToken, KeywordToken, SubcommandToken, CoordinateToken}
import model.core.gameobjects.purchasable.IGameObject
import model.utils.GameObjectUtils

import scala.annotation.tailrec
import scala.collection.immutable

case class CommandTokenizer() extends ICommandTokenizer:

  private val keywords = Set(">", "<", "to", "into", "from", "with")

  private val coordinate = "\\d+-\\d+"

  def parseInput(str: String): TokenizedInput =
    val tokens = tokenizer(str)
    if (tokens.isEmpty) {
      TokenizedInput(Vector.empty, str)
    } else {
      val firstExpression = stringToFirstExpression(tokens.head)
      val remainingExpressions = tokens.tail.map(stringToExpression)
      val expressions = firstExpression +: remainingExpressions

      val (mapped, unidentified) = expressions.partition(_.isInstanceOf[InterpretedUnidentified]).swap
      if unidentified.isEmpty then TokenizedInput(mapped, str)
      else TokenizedInput(mapped ++ Vector(findGameObject(unidentified.asInstanceOf[Vector[InterpretedUnidentified]])), str)
    }

  private def tokenizer(str: String):
  Vector[String] = str.toLowerCase.trim.split(" ").toVector.filterNot(_.isBlank)

  private def findGameObject(input: Vector[InterpretedUnidentified]): InterpretedInput =
    @tailrec
    def findGameObjectHelper(accumulator: String, remaining: Vector[InterpretedUnidentified]): InterpretedInput =
      GameObjectUtils().findInLists(accumulator) match
        case Some(gameObject) => InterpretedGameObject(gameObject)
        case None if remaining.isEmpty => InterpretedUnidentified(accumulator)
        case None => findGameObjectHelper(accumulator + " " + remaining.head.unidentified, remaining.tail)

    findGameObjectHelper(input.head.unidentified, input.tail)

  private def stringToFirstExpression(str: String): InterpretedInput =
    InterpretedCommand(CommandToken(str).interpret())

  private def stringToExpression(str: String): InterpretedInput =
    if keywords.contains(str) then InterpretedKeyword(KeywordToken(str).interpret())
    else if str.startsWith("-") then InterpretedSubcommand(SubcommandToken(str).interpret())
    else if str.toIntOption.isDefined then InterpretedQuantity(str.toInt)
    else if str matches coordinate then InterpretedCoordinate(CoordinateToken(str).interpret())
    else InterpretedUnidentified(str)

