package model.purchasable.technology

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class AdvancedMaterialsSpec extends AnyWordSpec {
  "AdvancedMaterials" should {
    "have a name of Advanced Materials" in {
      AdvancedMaterials().name should be("Advanced Materials")
    }
    "have a fitting toString representation" in {
      AdvancedMaterials().toString should be("Advanced Materials")
    }
  }
}
