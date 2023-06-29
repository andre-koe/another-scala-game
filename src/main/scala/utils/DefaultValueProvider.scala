package utils

import controller.newInterpreter.{CommandTokenizer, ICommandTokenizer}
import controller.{Controller, IController}
import model.core.board.sector.impl.{IPlayerSector, PlayerSector}
import model.core.board.{GameBoardBuilder, IGameBoard}
import model.core.fileIO.{IFileIOStrategy, JSONStrategy, XMLStrategy}
import model.core.utilities.{GameValues, IGameValues, IResourceHolder, IRound, ResourceHolder}
import model.game.gamestate.gamestates.RunningState
import model.game.gamestate.{GameStateManager, IGameState, IGameStateManager}
import model.game.playervalues.{IPlayerValues, PlayerValues}

object DefaultValueProvider:

  given IController = Controller()
  given IGameValues = GameValues()
  given ICommandTokenizer = CommandTokenizer()
  given IFileIOStrategy = JSONStrategy()

