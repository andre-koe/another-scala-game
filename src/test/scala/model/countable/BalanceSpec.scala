package model.countable

import model.countable.Balance
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class BalanceSpec extends AnyWordSpec {
  "Funds" when {
    "initialized" should {
      val funds: Balance = Balance()
      "have a value of 1000" in {
        funds.value should be(1000)
      }
    }
    "incremented by 10" should {
      val funds: Balance = Balance()
      "give us a new Funds object" in {
        funds.increase(Balance(10)) should not be equal(funds)
      }
      "have the value 2 when incremented after being initialized" in {
        funds.increase(Balance(10)).value should be(1010)
      }
    }
    "decremented by 10" should {
      val funds: Balance = Balance()
      "give us an Option Element" in {
        funds.decrease(Balance(10)).isDefined should be(true)
      }
      "give us an Option Element containing a different funds object" in {
        funds.decrease(Balance(10)).get should not be equal(funds)
      }
      "have the value 990 when decremented by 10 after being initialized" in {
        funds.decrease(Balance(10)).get.value should be(990)
      }
      "return Option.empty if decreased by more than actually contained" in {
        funds.decrease(Balance(1001)).isEmpty should be(true)
      }
    }
    "asked for its String representation" should {
      val funds: Balance = Balance(200)
      "give us a nice String" in {
        funds.toString should be(f"[Current balance: ${funds.value}]")
      }
    }
  }

}
