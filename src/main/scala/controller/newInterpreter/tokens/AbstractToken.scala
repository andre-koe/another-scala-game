package controller.newInterpreter.tokens

import controller.command.ICommand

/**
 * Base Class for all TokenClasses
 * @tparam T TokenClass
 */
trait AbstractToken[T]:

  /**
   * @return the validated Token
   */
  def interpret(): T
