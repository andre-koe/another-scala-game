package model.game.map

case class Coordinate(posX: Int = 0, posY: Int = 0):

  private val offset: Int = 65
  def getDistance(other: Coordinate): Int = Math.abs(this.posX - other.posX) + Math.abs(this.posY - other.posY)
  override def toString: String = s"$posY-${mapIntToString(posX)}"
  private def mapIntToString(value: Int): Char = ((value + offset) % (offset + 26)).toChar

