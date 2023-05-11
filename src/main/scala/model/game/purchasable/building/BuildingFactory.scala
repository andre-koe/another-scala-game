package model.game.purchasable.building

import model.game.purchasable.IPurchasableFactory

case class BuildingFactory() extends IPurchasableFactory[IBuilding]:

  def create(string: String): Option[IBuilding] =
    string match
      case "hangar" => Option(Hangar())
      case "energy grid" => Option(EnergyGrid())
      case "factory" => Option(Factory())
      case "mine" => Option(Mine())
      case "research lab" => Option(ResearchLab())
      case "shipyard" => Option(Shipyard())
      case _ => None

