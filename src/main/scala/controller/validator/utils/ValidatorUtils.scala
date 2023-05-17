package controller.validator.utils

import controller.newInterpreter.{CommandType, InterpretedCommand, InterpretedInputToken, InterpretedGameObject, InterpretedKeyword, InterpretedQuantity, InterpretedSubcommand, InterpretedUnidentified, KeywordType}
import model.game.purchasable.IGameObject

import scala.reflect.ClassTag

class ValidatorUtils:

  def findCommandFirst(vector: Vector[InterpretedInputToken]): Option[CommandType] =
    vector match
      case Vector(x, _*) => Some(x.asInstanceOf[InterpretedCommand].commandType)
      case Vector() => None

  def findSubcommands(vector: Vector[InterpretedInputToken]): Option[Vector[String]] =
    val found = filterTypeAll[InterpretedSubcommand](vector)
    found.map(_.map(_.subcommand))

  def findQuantity(vector: Vector[InterpretedInputToken]): Int =
    val found = filterTypeAll[InterpretedQuantity](vector)
    if found.isDefined then found.get.map(_.quantity).sum else 1

  def findKeyWords(vector: Vector[InterpretedInputToken]): Option[Vector[KeywordType]] =
    val found = filterTypeAll[InterpretedKeyword](vector)
    found.map(_.map(_.keywordType))

  def findUnidentified(vector: Vector[InterpretedInputToken]): Option[Vector[String]] =
    val found = filterTypeAll[InterpretedUnidentified](vector)
    found.map(_.map(_.unidentified))

  def findGameObjects(vector: Vector[InterpretedInputToken]): Option[Vector[IGameObject]] =
    val found = filterTypeAll[InterpretedGameObject](vector)
    if found.isDefined then Some(found.get.map(_.gameObject)) else None

  private def filterTypeAll[T](vector: Vector[InterpretedInputToken])(implicit ct: ClassTag[T]): Option[Vector[T]] =
    val filtered = vector.collect { case x: T => x }
    if (filtered.isEmpty) None else Some(filtered)
