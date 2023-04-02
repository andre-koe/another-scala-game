package model.purchasable.unit

import model.purchasable.units.Battleship
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class BattleshipSpec extends AnyWordSpec {

  "A Battleship" should {
    "have a name of Battleship" in {
      Battleship().name should be("Battleship")
    }
    "have a fitting toString representation" in {
      Battleship()
        .toString should be("The Battleship is a heavily armed and armored naval unit that can deal " +
        "massive damage to enemy ships and structures from a distance.")
    }
  }

}
