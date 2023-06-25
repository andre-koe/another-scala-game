package model.core.utilities

trait ICapacity:

  def value: Int
  
  def increase(other: ICapacity): ICapacity
  
  def decrease(other: ICapacity): Option[ICapacity]
  
  def lacking(other: ICapacity): ICapacity
  
  def multiplyBy(multiplier: Int): ICapacity  
  
  def holds(other: ICapacity): Option[Int]

  def empty(): ICapacity
  