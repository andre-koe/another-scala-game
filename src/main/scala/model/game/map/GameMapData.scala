package model.game.map

import model.game.map.system.Affiliation.{ENEMY, PLAYER}
import model.game.map.system.{Sector, SectorType}
import model.game.map.system.SectorType.BASE

case class GameMapData(sizeX: Int, sizeY: Int):

  def returnGameMapContent: Vector[Vector[Sector]] =
    Vector.tabulate(sizeY, sizeX) {
      (y, x) =>
        val coord = Coordinate(x, y)
        (coord.posX, coord.posY) match
          case (0, 0) => Sector(coordinate = coord, affiliation = PLAYER, BASE)
          case (x, y) if isLast(x,y) => Sector(coordinate = coord, affiliation = ENEMY, sectorType = BASE)
          case _ => Sector(coordinate = coord)
    }

  private def isLast(x: Int, y: Int): Boolean =
    if x == sizeX -1 && y == sizeY -1 then true else false
