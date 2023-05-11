package controller.validator

import model.game.gamestate.GameStateManager

trait InputValidator:
  def validate(str: String): IValidator

