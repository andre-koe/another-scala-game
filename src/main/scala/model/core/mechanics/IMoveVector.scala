package model.core.mechanics

import model.core.board.boardutils.ICoordinate
import utils.IXMLSerializable

import scala.xml.Elem

/** Interface for MoveVector functionality.
 *
 *  This trait represents a move vector with a start and a target coordinate.
 *  It provides methods to get the distance between these coordinates,
 *  change the start and target coordinates, get the next step in the vector,
 *  and check if the object is moving.
 */
trait IMoveVector extends IXMLSerializable:

  /** Getter for the start coordinate of the move vector.
   *
   *  @return ICoordinate: Start coordinate of the move vector.
   */
  def start: ICoordinate

  /** Getter for the target coordinate of the move vector.
   *
   *  @return ICoordinate: Target coordinate of the move vector.
   */
  def target: ICoordinate

  /** Method to get the distance between the start and target coordinates.
   *
   *  @return Option[Int]: The distance between the start and target coordinates.
   */
  def getDistance: Option[Int]

  /** Method to set the start coordinate of the move vector.
   *
   *  @param coordinate: ICoordinate: The new start coordinate.
   *  @return IMoveVector: The updated move vector with the new start coordinate.
   */
  def setStart(coordinate: ICoordinate): IMoveVector

  /** Method to set the target coordinate of the move vector.
   *
   *  @param coordinate: ICoordinate: The new target coordinate.
   *  @return IMoveVector: The updated move vector with the new target coordinate.
   */
  def setTarget(coordinate: ICoordinate): IMoveVector

  /** Method to get the next step in the move vector.
   *
   *  @return IMoveVector: The updated move vector after moving to the next step.
   */
  def nextStep: IMoveVector

  /** Method to check if the object is moving.
   *
   *  @return Boolean: True if the object is moving, False otherwise.
   */
  def isMoving: Boolean

  /** Method to convert the move vector to XML format.
   *
   *  @return Elem: The XML representation of the move vector.
   */
  override def toXML: Elem =
    <Movevector>
      <Start>{start.toXML}</Start>
      <Goal>{target.toXML}</Goal>
    </Movevector>

