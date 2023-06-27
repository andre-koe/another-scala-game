package model.core.mechanics.fleets

import model.core.board.sector.impl.Sector
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.units.{Battleship, Corvette}
import model.core.mechanics.MoveVector
import model.core.mechanics.fleets.components.units.IUnit
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FleetSpec extends AnyWordSpec with Matchers {

  "A Fleet" when {
    "initialized" in {
      val fleet = Fleet("TestFleet", Vector[IUnit](Corvette()), Coordinate(0, 0), MoveVector())
      fleet.name should be("TestFleet")
      fleet.fleetComponents should be(Vector[IUnit](Corvette()))
      fleet.location should be(Coordinate(0, 0))
      fleet.moveVector should be(MoveVector())
    }

    "merged with another fleet" in {
      val fleetA = Fleet("TestFleetA", Vector[IUnit](Corvette()), Coordinate(0, 0), MoveVector())
      val fleetB = Fleet("TestFleetB", Vector[IUnit](Corvette()), Coordinate(0, 0), MoveVector())

      fleetA.merge(fleetB).fleetComponents should be(Vector(Corvette(), Corvette()))
    }

    "split into two fleets" in {
      val fleetA = Fleet("TestFleetA", Vector[IUnit](Corvette()), Coordinate(0, 0), MoveVector())
      val fleetB = Fleet("TestFleetB", Vector[IUnit](Corvette()), Coordinate(0, 0), MoveVector())

      fleetA.merge(fleetB).split()._1.fleetComponents should be(Vector(Corvette()))
      fleetA.merge(fleetB).split()._2.fleetComponents should be(Vector(Corvette()))
    }

    "calculating firepower" in {
      val component1 = Corvette()
      val component2 = Battleship()
      val fleet = Fleet(fleetComponents = Vector(component1, component2))

      fleet.firepower should be(component1.firepower + component2.firepower)
    }

    "calculating upkeep" in {
      val component1 = Corvette()
      val component2 = Battleship()
      val fleet = Fleet(fleetComponents = Vector(component1, component2))

      fleet.upkeep should be(component1.upkeep.increase(component2.upkeep))
    }

    "calculating capacity" in {
      val component1 = Corvette()
      val component2 = Battleship()
      val fleet = Fleet(fleetComponents = Vector(component1, component2))

      fleet.capacity should be(component1.capacity.increase(component2.capacity))
    }

    "calculating fleet speed" in {
      val component1 = Corvette()
      val component2 = Battleship()
      val fleet = Fleet(fleetComponents = Vector(component1, component2))

      fleet.speed should be(Math.min(component1.speed, component2.speed))
    }

    "show the correct description" in {
      val fleet = Fleet(name = "TestFleet", fleetComponents = Vector(Corvette(), Corvette()))

      fleet.description should be(s"${fleet.name}\n Firepower: ${fleet.firepower}\n Size: ${fleet.units.length}\n Capacity: ${fleet.capacity}\n Upkeep: ${fleet.upkeep}")
    }
  }
}
