package controller.command.commands

import controller.command.ICommand
import model.core.fileIO.IFileIOStrategy
import model.game.gamestate.IGameStateManager
import utils.DefaultValueProvider.given_IFileIOStrategy

case class SaveCommand(string: Option[String],
                       gameStateManager: IGameStateManager)(using fileIOStrategy: IFileIOStrategy) extends ICommand:
  override def execute(): IGameStateManager = gameStateManager.save(fileIOStrategy, string)
