package controller.newInterpreter.tokens

import controller.command.ICommand

trait AbstractToken[T]:

  def interpret(): T
