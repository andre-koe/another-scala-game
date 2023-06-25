package model.core.board

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.logic.GameBoardData
import model.core.board.sector.ISector

case class GameBoardBuilder(cols: Int = 7, rows: Int = 7,
                            data: Vector[Vector[ISector]] = GameBoardData(7,7).returnGameBoardData) extends IGameBoardBuilder:

  def withSizeX(cols: Int): IGameBoardBuilder = this.copy(cols = cols)

  def withSizeY(rows: Int): IGameBoardBuilder = this.copy(rows = rows)

  def withData: IGameBoardBuilder = this.copy(data = GameBoardData(rows, cols).returnGameBoardData)

  def build: GameBoard = GameBoard(cols, rows, data)


