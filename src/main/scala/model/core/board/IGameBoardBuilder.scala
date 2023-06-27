package model.core.board

import model.core.board.sector.ISector

trait IGameBoardBuilder:

  def cols: Int

  def rows: Int

  def data: Vector[Vector[ISector]]

  def withSizeX(cols: Int): IGameBoardBuilder

  def withSizeY(rows: Int): IGameBoardBuilder

  def withData: IGameBoardBuilder

  def build: IGameBoard