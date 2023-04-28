package model.game.purchasable.unit

import model.game.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer, UnitFactory}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class UnitFactorySpec extends AnyWordSpec {

  "The UnitFactory" should {
    val unitFactory: UnitFactory = UnitFactory()
    "return Options" in {
      unitFactory.create("corvette") should be(Option(Corvette()))
    }
    "create the following units based on their name" in {
      unitFactory.create("battleship").get should be(Battleship())
      unitFactory.create("corvette").get should be(Corvette())
      unitFactory.create("cruiser").get should be(Cruiser())
      unitFactory.create("destroyer").get should be(Destroyer())
    }
    "return None if an nonexitent unit name is passed to it" in {
      unitFactory.create("test unit") should be(None)
    }
  }

}
