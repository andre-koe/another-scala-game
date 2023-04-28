package model.game.map

import model.game.map.system.Affiliation.{ENEMY, PLAYER}
import model.game.purchasable.units.IUnit
import system.SystemType.BASE
import system.{Affiliation, System}

import scala.compiletime.ops.string
case class GameMap(sizeX: Int = 20,
                   sizeY: Int = 7,
                   content: Vector[Vector[System]] = GameMapData(20, 7).returnGameMapContent):

  def updateSystemAt(x: Int, y: Int,
                     affiliation: Affiliation, 
                     units: Vector[IUnit]): GameMap = ???
  def findSystem(str: String): Option[System] =
    validateString(str) match
      case None => None
      case Some(value) => translateToCoord(value.split("-"))
  private def translateToCoord(str: Array[String]): Option[System] =
    val y: Option[Int] = str(0).toIntOption
    val x: Option[Int] = mapToInt(str(1))
    None
  private def mapToInt(str: String): Option[Int] = ???
  private def validateString(str: String): Option[String] =
    if str.contains("-") && str.length == 3 then Option(str) else None
  private def getSystemAt(x: Int, y: Int): System = content(y)(x)
  override def toString: String = limiter + "\n" + content.map(_.mkString("-" * 3)).mkString(separator) + "\n" + limiter
  private def separator: String = "\n" + ("  | " + " " * 4) * sizeX + "\n"
  private def limiter: String = "=" * ("(1-1)" * sizeX + "===" * (sizeX - 1)).length

