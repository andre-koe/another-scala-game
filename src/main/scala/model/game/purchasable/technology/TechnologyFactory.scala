package model.game.purchasable.technology

import model.game.purchasable.IPurchasableFactory

case class TechnologyFactory() extends IPurchasableFactory[ITechnology]:
  
  def create(string: String): Option[ITechnology] =
    string match
      case "advanced materials" => Option(AdvancedMaterials())
      case "polymer" => Option(Polymer())
      case "nano robotics" => Option(NanoRobotics())
      case "advanced propulsion" => Option(AdvancedPropulsion())
      case _ => None
      
      
