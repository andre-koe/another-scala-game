package model.purchasable.building

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FactorySpec extends AnyWordSpec {
  "A Factory" should {
    val factory: Factory = Factory()
    "be named Factory" in {
      factory.name should be("Factory")
    }
    "have an appropriate toString representation" in {
      factory
        .toString should be("The Factory processes minerals into alloys " +
        "which are needed for construction of buildings and ships.")
    }
  }

}
