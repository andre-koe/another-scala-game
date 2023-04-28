package model.game.purchasable.utils

import model.game.Capacity
import model.game.purchasable.utils.Output
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.Energy
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class OutputSpec extends AnyWordSpec {

  "Output" when {
    "initialized" should {
      "be initialized with Capacity and or ResourceHolder objects" in {
        Output(capacity = Capacity(), resourceHolder = ResourceHolder()).resourceHolder should not be (Nil)
        Output(capacity = Capacity(), resourceHolder = ResourceHolder()).capacity should not be (Nil)
      }
    }
    "increased" should {
      val output: Output = Output(capacity = Capacity(30), resourceHolder = ResourceHolder(energy = Energy(10)))
      "give us a new Output object with new ResourceHolder and Capacity values" in {
        output.increaseOutput(Output(capacity = Capacity(10),
          resourceHolder = ResourceHolder(energy = Energy(10)))).resourceHolder.energy.value should be(20)
        output.increaseOutput(Output(capacity = Capacity(10),
          resourceHolder = ResourceHolder(energy = Energy(10)))).capacity.value should be(40)
      }
    }
  }
}
