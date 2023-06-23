package controller.validator.validatorutils

import controller.newInterpreter.{CommandType, InterpretedCommand, InterpretedCoordinate, InterpretedGameObject, InterpretedInput, InterpretedKeyword, InterpretedQuantity, InterpretedSubcommand, InterpretedUnidentified, KeywordType}
import model.core.board.boardutils.ICoordinate
import model.core.gameobjects.purchasable.IGameObject

import scala.reflect.ClassTag

class ValidatorUtils:

  def findCommandFirst(vector: Vector[InterpretedInput]): Option[CommandType] =
    vector match
      case Vector(x, _*) => Some(x.asInstanceOf[InterpretedCommand].commandType)
      case Vector() => None

  def findSubcommands(vector: Vector[InterpretedInput]): Option[Vector[String]] =
    filterTypeAll[InterpretedSubcommand](vector).map(_.map(_.subcommand))

  def findQuantity(vector: Vector[InterpretedInput]): Int =
    val found = filterTypeAll[InterpretedQuantity](vector)
    if found.isDefined then found.get.map(_.quantity).sum else 1

  def findCoordinate(vector: Vector[InterpretedInput]): Option[ICoordinate] =
    filterTypeAll[InterpretedCoordinate](vector).map(_.map(_.coordinate).head)

  def findKeyWords(vector: Vector[InterpretedInput]): Option[Vector[KeywordType]] =
    filterTypeAll[InterpretedKeyword](vector).map(_.map(_.keywordType))

  def findUnidentified(vector: Vector[InterpretedInput]): Option[Vector[String]] =
    filterTypeAll[InterpretedUnidentified](vector).map(_.map(_.unidentified))

  def findGameObjects(vector: Vector[InterpretedInput]): Option[Vector[IGameObject]] =
    val found = filterTypeAll[InterpretedGameObject](vector)
    if found.isDefined then Some(found.get.map(_.gameObject)) else None

  private def filterTypeAll[T](vector: Vector[InterpretedInput])(implicit ct: ClassTag[T]): Option[Vector[T]] =
    val filtered = vector.collect { case x: T => x }
    if (filtered.isEmpty) None else Some(filtered)
