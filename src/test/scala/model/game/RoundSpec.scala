package model.game

import model.game.Round
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class RoundSpec extends AnyWordSpec {
 "A Round" when {
   "initialized" should {
     val round: Round = Round()
     "have a value of 1" in {
       round.value should be (1)
     }
   }
   "incremented" should {
     val round: Round = Round()
     "give us a new Round object" in {
       round.next should not be equal(round)
     }
     "have the value 2 when incremented after being initialized" in {
       round.next.value should be (2)
     }
   }
   "decreased" should {
     val round: Round = Round(10)
     "give us a new Round object" in {
       round.decrease should not be equal(round)
     }
     "have the value 9 when decreased after being initialized with 10" in {
       round.decrease.get.value should be(9)
     }
     "return None when decreased after being initialized with 0" in {
       val newRound: Round = Round(0)
       newRound.decrease.isDefined should be(false)
     }
   }
   "asked for its String representation" should {
     val aTestRoundValue: Int = 32
     val round: Round = Round(32)
     "give us a nice String" in {
       round.toString should be (f"[Round: $aTestRoundValue]")
     }
   }
  }
}