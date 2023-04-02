package model.purchasable.unit

import model.purchasable.units.Destroyer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class DestroyerSpec extends AnyWordSpec {

  "A Destroyer" should {
    "have a name of Destroyer" in {
      Destroyer().name should be("Destroyer")
    }
    "have a fitting toString representation" in {
      Destroyer()
        .toString should be("The Destroyer is a specialized naval unit that can detect and track enemy ships.")
    }
  }

}
