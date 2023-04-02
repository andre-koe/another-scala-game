package model.purchasable.building

case class Hangar(name: String = "Hangar") extends Building:
  override def toString: String = 
    "The Hangar provides additional capacity for air units and allows players to repair and upgrade them."

