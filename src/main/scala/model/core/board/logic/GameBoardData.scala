package model.core.board.logic

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate


case class GameBoardData(cols: Int, rows: Int):

  def returnGameBoardData: Vector[Vector[ISector]] =
    Vector.tabulate(cols, rows) {
      (cols, rows) => val coord = Coordinate(cols, rows)
      coord match
        case Coordinate(0,0) => PlayerSector(Sector(Coordinate(cols, rows), Affiliation.PLAYER, SectorType.BASE))
        case Coordinate(_,_) if isLast(coord) => PlayerSector(Sector(Coordinate(cols, rows), Affiliation.ENEMY, SectorType.BASE))
        case _ =>  Sector(Coordinate(cols, rows), Affiliation.INDEPENDENT, SectorType.REGULAR)
    }

  private def isLast(coordinate: Coordinate): Boolean =
    if coordinate.yPos == rows-1 && coordinate.xPos == cols-1 then true else false

