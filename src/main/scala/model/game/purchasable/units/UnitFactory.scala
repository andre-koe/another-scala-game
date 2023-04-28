package model.game.purchasable.units

import model.game.purchasable.IPurchasableFactory

case class UnitFactory() extends IPurchasableFactory[IUnit]:
  override def create(string: String): Option[IUnit] =
    string match
      case "corvette" => Option(Corvette())
      case "cruiser" => Option(Cruiser())
      case "destroyer" => Option(Destroyer())
      case "battleship" => Option(Battleship())
      case _ => None