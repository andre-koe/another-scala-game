package model.core.utilities

/** Interface representing a round.
 *  The round holds a value and provides operations to manipulate it.
 */
trait IRound:

  /** Retrieves the value of the round.
   *
   *  @return Int: The value of the round.
   */
  def value: Int

  /** Retrieves the next round.
   *
   *  @return IRound: The next round.
   */
  def next: IRound

  /** Decreases the round by one.
   *
   *  @return Option[IRound]: Some(round) if the decrease was successful and the resulting round is positive,
   *                         None if the resulting round would be negative.
   */
  def decrease: Option[IRound]
