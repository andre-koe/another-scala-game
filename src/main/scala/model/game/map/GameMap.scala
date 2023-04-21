package model.game.map

import model.purchasable.units.IUnit
import system.SystemType.BASE
import system.{Affiliation, System}

case class GameMap(sizeX: Int = 20, sizeY: Int = 7):
  def getSystemAt(x: Int, y: Int): System = content(y-1)(x-1)
  def updateSystemAt(x: Int, y: Int, 
                     affiliation: Affiliation, 
                     units: Vector[IUnit]): GameMap = ???
  override def toString: String = limiter + "\n" + content.map(_.mkString("-" * 3)).mkString(separator) + "\n" + limiter
  private def separator: String = "\n" + (" | " + " " * 3) * sizeX + "\n"
  private def limiter: String = "=" * ("(1 - 1)" * sizeX + "===" * (sizeX - 1)).length
  private def content: Vector[Vector[System]] = Vector.fill(sizeY)(Vector.fill(sizeX)(System()))
