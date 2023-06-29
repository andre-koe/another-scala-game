package model.core.gameobjects.resources

import model.utils.Increaseable
import io.circe.generic.auto._, io.circe.syntax._

/** Base Interface for Resource Types.
 *
 * This trait represents a resource that can be increased, decreased, and can hold a value.
 */
trait IResource[T] extends Increaseable[T] {

  /** Method to increase the current resource with another resource.
   *
   *  @param other: T: The other resource that should be added to the current resource.
   *  @return T: The new resource after the addition.
   */
  override def increase(other: T): T

  
  /** Getter for the value of the resource.
   *
   *  @return Int: Value of the resource.
   */
  def value: Int

  
  /** Getter for the name of the resource.
   *
   *  @return String: Name of the resource.
   */
  def name: String

  
  /** Method to decrease the current resource with another resource.
   *
   *  @param other: T: The other resource that should be subtracted from the current resource.
   *  @return Option[T]: The new resource after the subtraction, None if subtraction would result in negative value.
   */
  def decrease(other: T): Option[T]
}
