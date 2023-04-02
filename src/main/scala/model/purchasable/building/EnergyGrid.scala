package model.purchasable.building

case class EnergyGrid(name: String = "Energy Grid") extends Building:
  override def toString: String = "The Energy Grid provides a steady stream of energy to power other buildings and units."
