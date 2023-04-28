package model.interpreter

import controller.command.ICommand
import model.game.gamestate.GameStateManager

trait IExpressionParser {
  def parse(str: String): IExpression[GameStateManager, ICommand]
}
