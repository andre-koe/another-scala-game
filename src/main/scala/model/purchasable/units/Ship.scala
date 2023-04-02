package model.purchasable.units

trait Ship:
  def name: String

case class Cruiser(name: String = "Cruiser") extends Ship:
  override def toString: String =
    "The Cruiser is a versatile naval unit that can engage both " +
      "ground and air targets with its array of weapons and sensors."
case class Corvette(name: String = "Corvette") extends Ship:
  override def toString: String = "" +
    "The Corvette is a fast and agile naval unit that excels at harassing enemy fleets and scouting enemy positions."
case class Battleship(name: String = "Battleship") extends Ship:
  override def toString: String =
    "The Battleship is a heavily armed and armored naval unit that can deal " +
      "massive damage to enemy ships and structures from a distance."
case class Destroyer(name: String = "Destroyer") extends Ship:
  override def toString: String =
    "The Destroyer is a specialized naval unit that can detect and track enemy ships."
