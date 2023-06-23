package controller.newInterpreter.tokens

import model.core.board.boardutils.Coordinate
case class CoordinateToken(string: String) extends AbstractToken[Coordinate]:

  override def interpret(): Coordinate =
    val coords = string.split("-")
    Coordinate(coords(0).toInt, coords(1).toInt)


