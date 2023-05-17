package model.game.purchasable.units

import model.game.map.Coordinate
import model.game.purchasable.IPurchasableFactory

case class UnitFactory() extends IPurchasableFactory[IUnit]:
  override def create(string: String): Option[IUnit] =
    string match
      case "corvette" => Option(Corvette(location = Coordinate()))
      case "cruiser" => Option(Cruiser(location = Coordinate()))
      case "destroyer" => Option(Destroyer(location = Coordinate()))
      case "battleship" => Option(Battleship(location = Coordinate()))
      case _ => None