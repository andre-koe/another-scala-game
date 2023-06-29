package controller.validator


/**
 * Validate the player input based on syntax
 */
trait InputValidator:

  /**
   * @param str player input
   * @return an IValidator instance
   */
  def validate(str: String): IValidator

