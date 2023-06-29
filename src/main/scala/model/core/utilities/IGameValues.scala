package model.core.utilities

import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.mechanics.fleets.components.units.IUnit

/** Interface representing the game values, such as buildings, units, and technologies. */
trait IGameValues:

  /** Retrieves the vector of buildings in the game.
   *
   *  @return Vector[IBuilding]: The vector of buildings.
   */
  def buildings: Vector[IBuilding]

  /** Retrieves the vector of units in the game.
   *
   *  @return Vector[IUnit]: The vector of units.
   */
  def units: Vector[IUnit]

  /** Retrieves the vector of technologies in the game.
   *
   *  @return Vector[ITechnology]: The vector of technologies.
   */
  def tech: Vector[ITechnology]

