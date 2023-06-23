package model.core.mechanics

import model.core.board.boardutils.ICoordinate
import utils.IXMLSerializable

import scala.xml.Elem

trait IMoveVector extends IXMLSerializable:

  def start: ICoordinate

  def target: ICoordinate

  def getDistance: Option[Int]

  def setStart(coordinate: ICoordinate): IMoveVector

  def setTarget(coordinate: ICoordinate): IMoveVector

  override def toXML: Elem =
    <Movevector>
      <Start>{start.toXML}</Start>
      <Goal>{target.toXML}</Goal>
    </Movevector>

