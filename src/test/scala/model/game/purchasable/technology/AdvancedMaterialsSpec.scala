package model.game.purchasable.technology

import model.game.purchasable.technology.AdvancedMaterials
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class AdvancedMaterialsSpec extends AnyWordSpec {
  "AdvancedMaterials" should {
    "have a name of Advanced Materials" in {
      AdvancedMaterials().name should be("Advanced Materials")
    }
    "have a fitting toString representation" in {
      AdvancedMaterials().toString should be("Advanced Materials")
    }
    "return a new AdvancedMaterials object when round is decreased" in {
      AdvancedMaterials().decreaseRoundsToComplete
        .roundsToComplete.value should be(AdvancedMaterials().roundsToComplete.value - 1)
    }
  }
}