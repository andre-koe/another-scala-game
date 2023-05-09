package model.game.resources

import model.utils.Increaseable

trait IResource[T] extends Increaseable[T] {

  override def increase(other: T): T
  
  def value: Int

  def name: String
  
  def decrease(other: T): Option[T]
}
