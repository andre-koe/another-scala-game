package model.utils

import model.game.purchasable.building.IBuilding
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class GameObjectUtilsSpec extends AnyWordSpec {

  "The GameObjectUtils" should {
    "provide a Method findInLists which" when {
      "asked for a Unit name returns the appropriate unit object" in {
        GameObjectUtils().findInLists("corvette").isDefined should be(true)
        GameObjectUtils().findInLists("cruiser").get shouldBe a[IUnit]
      }
      "asked for a Technology name returns the appropriate unit object" in {
        GameObjectUtils().findInLists("polymer").isDefined should be(true)
        GameObjectUtils().findInLists("advanced materials").get shouldBe a[ITechnology]
      }
      "asked for a Building name returns the appropriate unit object" in {
        GameObjectUtils().findInLists("mine").isDefined should be(true)
        GameObjectUtils().findInLists("energy grid").get shouldBe a[IBuilding]
      }
      "asked for something undefined name returns none" in {
        GameObjectUtils().findInLists("test").isDefined should be(false)
      }
    }
  }

}
