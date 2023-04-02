package model.purchasable.building

case class BuildingFactory():

  def create(string: String): Option[Building] =
    string match
      case "hangar" => Option(Hangar())
      case "energy grid" => Option(EnergyGrid())
      case "factory" => Option(Factory())
      case "mine" => Option(Mine())
      case "research lab" => Option(ResearchLab())
      case "shipyard" => Option(Shipyard())
      case _ => None

