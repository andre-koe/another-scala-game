package model.purchasable.building

case class Factory(name: String = "Factory") extends Building:
  override def toString: String = 
    "The Factory processes minerals into alloys which are needed for construction of buildings and ships."

