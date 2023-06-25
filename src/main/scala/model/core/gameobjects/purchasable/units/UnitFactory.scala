package model.core.gameobjects.purchasable.units

import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.mechanics.fleets.components.units.IUnit

object UnitFactory:
  def apply(string: String): Option[IUnit] =
    string.toLowerCase match
      case "corvette" => Option(Corvette())
      case "cruiser" => Option(Cruiser())
      case "destroyer" => Option(Destroyer())
      case "battleship" => Option(Battleship())
      case _ => None