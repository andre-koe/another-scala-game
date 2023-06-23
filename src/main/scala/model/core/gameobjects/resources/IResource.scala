package model.core.gameobjects.resources

import model.utils.Increaseable
import io.circe.generic.auto._, io.circe.syntax._

trait IResource[T] extends Increaseable[T] {

  override def increase(other: T): T
  
  def value: Int

  def name: String
  
  def decrease(other: T): Option[T]
}
