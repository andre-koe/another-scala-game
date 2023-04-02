package model.purchasable.building

case class ResearchLab(name: String = "Research Lab") extends Building:
    override def toString: String =
      "The Research Lab increases research output and unlocks new technologies for players."

