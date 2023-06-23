package model.core.gameobjects.purchasable.units

import model.core.board.sector.ISector
import model.core.mechanics.fleets.components.units.IUnit
import model.core.board.boardutils.Coordinate

object UnitFactory:
  def apply(string: String, location: ISector): Option[IUnit] =
    string.toLowerCase match
      case "corvette" => Option(Corvette(location = location))
      case "cruiser" => Option(Cruiser(location = location))
      case "destroyer" => Option(Destroyer(location = location))
      case "battleship" => Option(Battleship(location = location))
      case _ => None