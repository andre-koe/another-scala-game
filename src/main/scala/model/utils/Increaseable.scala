package model.utils

/** Interface representing an increaseable value.
 *  Provides a method to increase the value by another value of the same type.
 *
 *  @tparam T The type of the increaseable value.
 */
trait Increaseable[T]:

  /** Increases the value by another value of the same type.
   *
   *  @param other The value to increase by.
   *  @return T: The increased value.
   */
  def increase(other: T): T


