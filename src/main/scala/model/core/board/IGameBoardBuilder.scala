package model.core.board

trait IGameBoardBuilder:
  def withSizeX(cols: Int): IGameBoardBuilder

  def withSizeY(rows: Int): IGameBoardBuilder

  def withData: IGameBoardBuilder

  def build: IGameBoard