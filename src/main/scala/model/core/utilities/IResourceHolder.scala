package model.core.utilities

import model.core.gameobjects.resources.IResource
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import utils.IXMLSerializable

import scala.annotation.tailrec
import scala.xml.Elem

/** Interface representing a resource holder, capable of holding different types of resources.
 *  The resource holder is serializable to XML.
 */
trait IResourceHolder extends IXMLSerializable:

  /** Retrieves the descriptor of the resource holder.
   *
   *  @return String: The descriptor of the resource holder.
   */
  def descriptor: String

  /** Retrieves the energy resource.
   *
   *  @return Energy: The energy resource.
   */
  def energy: Energy

  /** Retrieves the minerals resource.
   *
   *  @return Minerals: The minerals resource.
   */
  def minerals: Minerals

  /** Retrieves the alloys resource.
   *
   *  @return Alloys: The alloys resource.
   */
  def alloys: Alloys

  /** Retrieves the research points resource.
   *
   *  @return ResearchPoints: The research points resource.
   */
  def researchPoints: ResearchPoints

  /** Increases the resource holder by adding the values of another resource holder.
   *
   *  @param other The other resource holder to be added.
   *  @return IResourceHolder: The resulting resource holder after the addition.
   */
  def increase(other: IResourceHolder): IResourceHolder

  /** Decreases the resource holder by subtracting the values of another resource holder.
   *
   *  @param other The other resource holder to be subtracted.
   *  @return Option[IResourceHolder]: Some(resourceHolder) if the subtraction was successful,
   *                                  None if the subtraction resulted in negative values.
   */
  def decrease(other: IResourceHolder): Option[IResourceHolder]

  /** Divides the resource holder by a divisor.
   *
   *  @param divisor The divisor value.
   *  @return IResourceHolder: The resulting resource holder after the division.
   */
  def divideBy(divisor: Int): IResourceHolder

  /** Multiplies the resource holder by a multiplier.
   *
   *  @param multiplier The multiplier value.
   *  @return IResourceHolder: The resulting resource holder after the multiplication.
   */
  def multiplyBy(multiplier: Int): IResourceHolder


  def lacking(other: IResourceHolder): IResourceHolder

  def holds(other: IResourceHolder): Option[Int]

  def subtract(other: IResourceHolder): IResourceHolder

  def resourcesAsVector: Vector[IResource[_]]

  def onlyNegative(): IResourceHolder

  override def toXML: scala.xml.Elem =
    <ResourceHolder>
      <Descriptor>{descriptor}</Descriptor>
      <Energy>{energy.value}</Energy>
      <Minerals>{minerals.value}</Minerals>
      <ResearchPoints>{researchPoints.value}</ResearchPoints>
      <Alloys>{alloys.value}</Alloys>
    </ResourceHolder>