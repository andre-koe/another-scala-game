package model.core.gameobjects.purchasable.unit

import model.core.gameobjects.purchasable.units.Battleship
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class BattleshipSpec extends AnyWordSpec {

  "A Battleship" should {
    "have a name of Battleship" in {
      Battleship().name should be("Battleship")
    }
    "have a fitting toString representation" in {
      Battleship()
        .toString should be("Battleship")
    }
    "return a new BattleShip object when round is decreased" in {
      Battleship().decreaseRoundsToComplete.roundsToComplete.value should be(Battleship().roundsToComplete.value - 1)
    }
  }

}
