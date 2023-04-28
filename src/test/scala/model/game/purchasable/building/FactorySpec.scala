package model.game.purchasable.building

import model.game.purchasable.building.Factory
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class FactorySpec extends AnyWordSpec {
  "A Factory" should {
    val factory: Factory = Factory()
    "be named Factory" in {
      factory.name should be("Factory")
    }
    "have an appropriate toString representation" in {
      factory
        .toString should be("Factory")
    }
    "return a new Factory object when round is decreased" in {
      factory.decreaseRoundsToComplete.roundsToComplete.value should be(Factory().roundsToComplete.value - 1)
    }
  }

}