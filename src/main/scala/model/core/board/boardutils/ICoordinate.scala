package model.core.board.boardutils

import utils.IXMLSerializable

import scala.xml.Elem

trait ICoordinate extends IXMLSerializable:

  def xPos: Int

  def yPos: Int

  def getDistance(other: ICoordinate): Int

  override def toXML: Elem =
    <Coordinate>
      <xPos>{xPos}</xPos>
      <yPos>{yPos}</yPos>
    </Coordinate>
