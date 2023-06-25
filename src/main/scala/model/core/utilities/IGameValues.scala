package model.core.utilities

import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.mechanics.fleets.components.units.IUnit

trait IGameValues:

  def buildings: Vector[IBuilding]

  def units: Vector[IUnit]

  def tech: Vector[ITechnology]
