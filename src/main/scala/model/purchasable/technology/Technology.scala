package model.purchasable.technology

trait Technology:
  def name: String
case class Polymer(name: String = "Polymer") extends Technology:
  override def toString: String
  = "Polymer"
case class AdvancedMaterials(name: String = "Advanced Materials") extends Technology:
  override def toString: String
  = "Advanced Materials"
case class NanoRobotics(name: String = "Nano Robotics") extends Technology:
  override def toString: String
  = "Nano Robotics"
case class AdvancedPropulsion(name: String = "Advanced Propulsion") extends Technology:
  override def toString: String
  = "Advanced Propulsion"