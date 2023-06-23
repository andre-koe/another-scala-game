package model.core.utilities

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.GameBoard
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.*
import model.core.gameobjects.purchasable.technology.*
import model.core.gameobjects.purchasable.units.*
import model.core.mechanics.fleets.components.units.IUnit
import model.game.gamestate.{GameStateManager, IGameState}
import model.game.playervalues.PlayerValues

case class GameValues(buildings: Vector[IBuilding]
                      = Vector[IBuilding](ResearchLab(), EnergyGrid(), Shipyard(), Hangar(), Factory(), Mine()),
                      units: Vector[IUnit]
                      = Vector[IUnit](Corvette(), Cruiser(), Destroyer(), Battleship()),
                      tech: Vector[ITechnology]
                      = Vector[ITechnology](Polymer(), AdvancedMaterials(), NanoRobotics(), AdvancedPropulsion())
                     ) extends IGameValues


