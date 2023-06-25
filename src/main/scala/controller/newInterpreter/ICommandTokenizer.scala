package controller.newInterpreter

trait ICommandTokenizer:

  def parseInput(str: String): TokenizedInput