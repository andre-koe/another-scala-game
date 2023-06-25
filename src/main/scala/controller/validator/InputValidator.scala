package controller.validator


trait InputValidator:
  def validate(str: String): IValidator

