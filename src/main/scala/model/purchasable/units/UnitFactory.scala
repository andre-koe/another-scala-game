package model.purchasable.units

case class UnitFactory():
  def create(string: String): Option[IUnit] =
    string match
      case "corvette" => Option(Corvette())
      case "cruiser" => Option(Cruiser())
      case "destroyer" => Option(Destroyer())
      case "battleship" => Option(Battleship())
      case _ => None