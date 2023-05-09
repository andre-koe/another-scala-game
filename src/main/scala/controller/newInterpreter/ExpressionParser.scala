package controller.newInterpreter

import controller.newInterpreter.InterpretedExpression
import controller.newInterpreter.InterpretedUnidentified
import model.game.gamestate.GameStateManager
import model.game.purchasable.IGameObject
import model.utils.GameObjectUtils

import scala.annotation.tailrec
import scala.collection.immutable

case class ExpressionParser():

  private val keywords = Set(">", "<", "to", "into", "from", "with")

  private val coordinate = "\\d-[a-zA-Z]"

  def parseInput(str: String): CombinedExpression =
    val tokens = tokenizer(str)
    if (tokens.isEmpty) {
      CombinedExpression(Vector.empty, str)
    } else {
      val firstExpression = stringToFirstExpression(tokens.head)
      val remainingExpressions = tokens.tail.map(stringToExpression)
      val expressions = firstExpression +: remainingExpressions

      val (mapped, unidentified) = expressions.partition(_.isInstanceOf[InterpretedUnidentified]).swap
      if unidentified.isEmpty then CombinedExpression(mapped, str)
      else CombinedExpression(mapped ++ Vector(findGameObject(unidentified.asInstanceOf[Vector[InterpretedUnidentified]])), str)
    }

  private def tokenizer(str: String):
  Vector[String] = str.toLowerCase.trim.split(" ").toVector.filterNot(_.isBlank)

  private def findGameObject(input: Vector[InterpretedUnidentified]): InterpretedExpression =
    @tailrec
    def findGameObjectHelper(accumulator: String, remaining: Vector[InterpretedUnidentified]): InterpretedExpression =
      GameObjectUtils().findInLists(accumulator) match
        case Some(gameObject) => InterpretedGameObject(gameObject)
        case None if remaining.isEmpty => InterpretedUnidentified(accumulator)
        case None => findGameObjectHelper(accumulator + " " + remaining.head.unidentified, remaining.tail)

    findGameObjectHelper(input.head.unidentified, input.tail)

  private def stringToFirstExpression(str: String): InterpretedExpression =
    InterpretedCommand(CommandExpression(str).interpret())

  private def stringToExpression(str: String): InterpretedExpression =
    if keywords.contains(str) then InterpretedKeyword(KeywordExpression(str).interpret())
    else if str.startsWith("-") then InterpretedSubcommand(SubcommandExpression(str).interpret())
    else if str.toIntOption.isDefined then InterpretedQuantity(str.toInt)
    else InterpretedUnidentified(str)

