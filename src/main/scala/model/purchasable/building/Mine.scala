package model.purchasable.building

case class Mine(name: String = "Mine") extends Building:
  override def toString: String = "The Mine extracts minerals which are used to produce alloys."
    