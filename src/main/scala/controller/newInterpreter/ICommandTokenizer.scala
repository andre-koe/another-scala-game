package controller.newInterpreter

/**
 * Tokenize an input string
 */
trait ICommandTokenizer:

  /**
   * 
   * @param str user input
   * @return TokenizedInput
   */
  def parseInput(str: String): TokenizedInput