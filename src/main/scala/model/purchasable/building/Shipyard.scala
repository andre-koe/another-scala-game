package model.purchasable.building

case class Shipyard(name: String = "Shipyard") extends Building:
  override def toString: String = "The Shipyard allows players to construct and upgrade naval units for their fleet."
