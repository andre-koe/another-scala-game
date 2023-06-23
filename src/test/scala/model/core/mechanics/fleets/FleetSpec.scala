package model.core.mechanics.fleets

import model.core.board.sector.impl.Sector
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.units.{Battleship, Corvette}
import model.core.mechanics.fleets.components.Component
import model.core.mechanics.MoveVector
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FleetSpec extends AnyFlatSpec with Matchers {
  "A Fleet" should "be able to be initialized" in {
    val fleet = Fleet("TestFleet", Vector[Component](Corvette()), Sector(Coordinate(0,0)), MoveVector())
    fleet.name should be ("TestFleet")
    fleet.fleetComponents should be (Vector[Component](Corvette()))
    fleet.location should be (Sector(Coordinate(0,0)))
    fleet.moveVector should be (MoveVector())
  }

  it should "calculate firepower correctly" in {
    val component1 = Corvette()
    val component2 = Battleship()
    val fleet = Fleet(fleetComponents = Vector(component1, component2))

    fleet.firepower should be (component1.firepower + component2.firepower)
  }

  it should "calculate speed correctly" in {
    val component1 = Corvette()
    val component2 = Battleship()
    val fleet = Fleet(fleetComponents = Vector(component1, component2))

    fleet.speed should be (Math.min(component1.speed, component2.speed))
  }
}
