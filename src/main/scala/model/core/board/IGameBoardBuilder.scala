package model.core.board

import model.core.board.sector.ISector

/** Represents an interface for a game board builder. */
trait IGameBoardBuilder:

  /** Returns the number of columns of the game board being built.
   *
   * @return The number of columns.
   */
  def cols: Int

  /** Returns the number of rows of the game board being built.
   *
   * @return The number of rows.
   */
  def rows: Int

  /** Returns the 2D vector representing the data of the game board being built.
   *
   * @return The 2D vector of `ISector` that represents the game board.
   */
  def data: Vector[Vector[ISector]]

  /** Sets the number of columns for the game board being built.
   *
   * @param cols The number of columns.
   * @return A new `IGameBoardBuilder` with the updated number of columns.
   */
  def withSizeX(cols: Int): IGameBoardBuilder

  /** Sets the number of rows for the game board being built.
   *
   * @param rows The number of rows.
   * @return A new `IGameBoardBuilder` with the updated number of rows.
   */
  def withSizeY(rows: Int): IGameBoardBuilder

  /** Initializes the data for the game board being built.
   *
   * @return A new `IGameBoardBuilder` with initialized data.
   */
  def withData: IGameBoardBuilder

  /** Builds and returns the `IGameBoard`.
   *
   * @return The built `IGameBoard`.
   */
  def build: IGameBoard
