package model.core.utilities

/** Interface for all objects representing build slots in the game.
 *
 *  All objects that act as a holder for build slots must implement this interface.
 */
trait IBuildSlots:

  /** Retrieves the current number of build slots.
   *
   *  @return Int: The current number of build slots.
   */
  def value: Int

  /** Increases the number of build slots by the specified amount.
   *
   *  @param other The number of build slots to add.
   *  @return IBuildSlots: A new IBuildSlots instance with increased value.
   */
  def increase(other: IBuildSlots): IBuildSlots

  /** Decreases the number of build slots by the specified amount.
   *
   *  @param other The number of build slots to subtract.
   *  @return Option[IBuildSlots]: A new IBuildSlots instance with decreased value, 
   *                               or None if the operation would result in negative slots.
   */
  def decrease(other: IBuildSlots): Option[IBuildSlots]

  /** Decreases the number of build slots by one.
   *
   *  @return Option[IBuildSlots]: A new IBuildSlots instance with decreased value, 
   *                               or None if the operation would result in negative slots.
   */
  def decrement: Option[IBuildSlots]
