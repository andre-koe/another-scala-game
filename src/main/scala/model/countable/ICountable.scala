package model.countable

trait ICountable[T] {
  def increase(other: T): T
  def decrease(other: T): Option[T]
}
