package model.game.map

import scala.util.Random

case class Coordinate(posX: Int = 0, posY: Int = 0):

  private val offset: Int = 65

  def getDistance(other: Coordinate): Int = Math.abs(this.posX - other.posX) + Math.abs(this.posY - other.posY)

  def addHorizontal(toAddX: Int): Coordinate =
    this.copy(posX = posX + toAddX)

  def subtractHorizontal(toSubX: Int): Coordinate =
    this.copy(posX = posX - toSubX)

  def addVertical(toAddY: Int): Coordinate =
    this.copy(posY = posY + toAddY)

  def subtractVertical(toSubY: Int): Coordinate =
    this.copy(posY = posY - toSubY)

  override def toString: String = s"$posY-${mapIntToString(posX)}"

  private def mapIntToString(value: Int): Char = ((value + offset) % (offset + 26)).toChar

