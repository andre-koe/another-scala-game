package model.game
import model.game.gamestate.Round
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class RoundSpec extends AnyWordSpec {
 "A Round" when {
   "initialized" should {
     val round:Round = Round()
     "have a value of 1" in {
       round.getRoundValue should be (1)
     }
   }
   "incremented" should {
     val round:Round = Round()
     "give us a new Round object" in {
       round.next should not be equal(round)
     }
     "have the value 2 when incremented after being initialized" in {
       round.next.getRoundValue should be (2)
     }
   }
  }
}