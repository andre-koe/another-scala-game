package model.purchasable.unit

import model.purchasable.unit._
import model.purchasable.units._
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class UnitFactorySpec extends AnyWordSpec {

  "The UnitFactory" should {
    val unitFactory: UnitFactory = UnitFactory()
    "return Options" in {
      unitFactory.create("corvette", 1) should be(Option(Corvette()))
    }
    "create the following units based on their name" in {
      unitFactory.create("battleship", 1).get should be(Battleship())
      unitFactory.create("corvette", 1).get should be(Corvette())
      unitFactory.create("cruiser", 1).get should be(Cruiser())
      unitFactory.create("destroyer", 1).get should be(Destroyer())
    }
    "return None if an nonexitent unit name is passed to it" in {
      unitFactory.create("test unit", 1) should be(None)
    }
  }

}
