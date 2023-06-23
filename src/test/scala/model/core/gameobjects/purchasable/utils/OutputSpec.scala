package model.core.gameobjects.purchasable.utils

import model.core.gameobjects.purchasable.utils.Output
import model.core.gameobjects.resources.resourcetypes.Energy
import model.core.utilities.{Capacity, ResourceHolder}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class OutputSpec extends AnyWordSpec {

  "Output" when {
    "initialized" should {
      "be initialized with Capacity and or ResourceHolder objects" in {
        Output(cap = Capacity(), rHolder = ResourceHolder()).rHolder should not be (Nil)
        Output(cap = Capacity(), rHolder = ResourceHolder()).cap should not be (Nil)
      }
    }
    "increased" should {
      val output: Output = Output(cap = Capacity(30), rHolder = ResourceHolder(energy = Energy(10)))
      "give us a new Output object with new ResourceHolder and Capacity values" in {
        output.increase(Output(cap = Capacity(10),
          rHolder = ResourceHolder(energy = Energy(10)))).rHolder.energy.value should be(20)
        output.increase(Output(cap = Capacity(10),
          rHolder = ResourceHolder(energy = Energy(10)))).cap.value should be(40)
      }
    }
  }
}
