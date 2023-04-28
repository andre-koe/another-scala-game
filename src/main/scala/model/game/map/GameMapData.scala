package model.game.map

import model.game.map.system.Affiliation.{ENEMY, PLAYER}
import model.game.map.system.{System, SystemType}
import model.game.map.system.SystemType.BASE

case class GameMapData(sizeX: Int, sizeY: Int):
  def returnGameMapContent: Vector[Vector[System]] =
    Vector.tabulate(sizeY, sizeX) {
      (y, x) =>
        val coord = Coordinate(x, y)
        if x == 0 & y == 0 then System(coordinate = coord, affiliation = PLAYER, BASE)
        else if x == sizeX - 1 & y == sizeY - 1 then System(coordinate = coord, affiliation = ENEMY, systemType = BASE)
        else System(coordinate = coord)
    }
