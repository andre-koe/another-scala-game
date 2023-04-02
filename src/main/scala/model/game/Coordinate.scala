package model.game

case class Coordinate(posX: Int = 0, posY: Int = 0):
  override def toString: String = s"[$posX - $posY]"
      