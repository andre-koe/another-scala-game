package model.game.map

import model.game.map.system.Affiliation.{ENEMY, PLAYER}
import model.game.map.system.{System, SystemType}
import model.game.map.system.SystemType.BASE

case class GameMapData(sizeX: Int, sizeY: Int):

  def returnGameMapContent: Vector[Vector[System]] =
    Vector.tabulate(sizeY, sizeX) {
      (y, x) =>
        val coord = Coordinate(x, y)
        (coord.posX, coord.posY) match
          case (0, 0) => System(coordinate = coord, affiliation = PLAYER, BASE)
          case (x, y) if isLast(x,y) => System(coordinate = coord, affiliation = ENEMY, systemType = BASE)
          case _ => System(coordinate = coord)
    }

  private def isLast(x: Int, y: Int): Boolean =
    if x == sizeX -1 && y == sizeY -1 then true else false
