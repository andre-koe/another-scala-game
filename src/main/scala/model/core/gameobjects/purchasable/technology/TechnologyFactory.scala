package model.core.gameobjects.purchasable.technology

object TechnologyFactory:

  def apply(string: String): Option[ITechnology] =
    string.toLowerCase match
      case "advanced materials" => Option(AdvancedMaterials())
      case "polymer" => Option(Polymer())
      case "nano robotics" => Option(NanoRobotics())
      case "advanced propulsion" => Option(AdvancedPropulsion())
      case _ => None
      
      
