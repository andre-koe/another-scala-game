package model.utils

trait Increaseable[T]:
  
  def increase(other: T): T


