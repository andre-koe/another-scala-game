package model.game.resources

trait IResource[T] {
  def name: String
  def increase(other: T): T
  def decrease(other: T): Option[T]
}
