package model.purchasable.technology

case class TechnologyFactory():
  def create(string: String): Option[Technology] =
    string match
      case "advanced materials" => Option(AdvancedMaterials())
      case "polymer" => Option(Polymer())
      case "nano robotics" => Option(NanoRobotics())
      case "advanced propulsion" => Option(AdvancedPropulsion())
      case _ => None
      
      
