package model.core.utilities

/** Interface for all objects representing capacity in the game.
 *
 *  All objects that act as a holder for capacity must implement this interface.
 */
trait ICapacity:

  /** Retrieves the current capacity value.
   *
   *  @return Int: The current capacity value.
   */
  def value: Int

  /** Increases the capacity by the specified amount.
   *
   *  @param other The capacity value to add.
   *  @return ICapacity: A new ICapacity instance with increased value.
   */
  def increase(other: ICapacity): ICapacity

  /** Decreases the capacity by the specified amount.
   *
   *  @param other The capacity value to subtract.
   *  @return Option[ICapacity]: A new ICapacity instance with decreased value, 
   *                             or None if the operation would result in negative capacity.
   */
  def decrease(other: ICapacity): Option[ICapacity]

  /** Calculates the lacking capacity compared to another capacity.
   *
   *  @param other The capacity value to compare with.
   *  @return ICapacity: A new ICapacity instance representing the lacking capacity.
   */
  def lacking(other: ICapacity): ICapacity

  /** Multiplies the capacity by a given multiplier.
   *
   *  @param multiplier The factor to multiply the capacity with.
   *  @return ICapacity: A new ICapacity instance with multiplied value.
   */
  def multiplyBy(multiplier: Int): ICapacity

  /** Calculates how many times one capacity can hold another.
   *
   *  @param other The capacity value to check against.
   *  @return Option[Int]: The number of times this capacity can hold the other, or None if it can't hold it at all.
   */
  def holds(other: ICapacity): Option[Int]

  /** Resets the capacity value to zero.
   *
   *  @return ICapacity: A new ICapacity instance with zero value.
   */
  def empty(): ICapacity

  