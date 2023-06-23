package model.core.gameobjects.purchasable.building

import model.core.gameobjects.purchasable.building.Factory
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

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
