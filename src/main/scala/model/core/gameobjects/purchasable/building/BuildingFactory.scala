package model.core.gameobjects.purchasable.building

import model.core.board.sector.ISector

object BuildingFactory:
  def apply(string: String, location: ISector): Option[IBuilding] =
    string.toLowerCase match
      case "hangar" => Option(Hangar(location = location))
      case "energy grid" => Option(EnergyGrid(location = location))
      case "factory" => Option(Factory(location = location))
      case "mine" => Option(Mine(location = location))
      case "research lab" => Option(ResearchLab(location = location))
      case "shipyard" => Option(Shipyard(location = location))
      case _ => None

