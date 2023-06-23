package model.core.utilities

import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.core.utilities.ResourceHolder
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ResourceHolderSpec extends AnyWordSpec {

  "A ResourceHolder" when {
    "initialized" should {
      val resourceHolder: ResourceHolder = ResourceHolder()
      "have all values set to zero" in {
        resourceHolder.alloys.value should be(0)
        resourceHolder.energy.value should be(0)
        resourceHolder.researchPoints.value should be(0)
        resourceHolder.minerals.value should be(0)
      }
    }
    "incremented with respect to energy" should {
      val resourceHolder: ResourceHolder = ResourceHolder()
      "store the correct value" in {
        resourceHolder.increase(ResourceHolder(energy = Energy(10))).energy.value should be(10)
      }
      "have the appropriate String representation" in {
        resourceHolder.increase(ResourceHolder(energy = Energy(10))).toString should be("Total Cost: [Energy: 10]")
      }
    }
    "incremented with respect to minerals" should {
      val resourceHolder: ResourceHolder = ResourceHolder()
      "store the correct value" in {
        resourceHolder.increase(ResourceHolder(minerals = Minerals(10))).minerals.value should be(10)
      }
      "have the appropriate String representation" in {
        resourceHolder.increase(ResourceHolder(minerals = Minerals(10))).toString should be("Total Cost: [Minerals: 10]")
      }
    }
    "incremented with respect to alloys" should {
      val resourceHolder: ResourceHolder = ResourceHolder()
      "store the correct value" in {
        resourceHolder.increase(ResourceHolder(alloys = Alloys(10))).alloys.value should be(10)
      }
      "have the appropriate String representation" in {
        resourceHolder.increase(ResourceHolder(alloys = Alloys(10))).toString should be("Total Cost: [Alloys: 10]")
      }
    }
    "incremented with respect to researchPoints" should {
      val resourceHolder: ResourceHolder = ResourceHolder()
      "store the correct value" in {
        resourceHolder.increase(ResourceHolder(researchPoints = ResearchPoints(10))).researchPoints.value should be(10)
      }
      "have the appropriate String representation" in {
        resourceHolder.increase(ResourceHolder(researchPoints = ResearchPoints(10)))
          .toString should be("Total Cost: [Research Points: 10]")
      }
    }
    "incremented with respect to all resources" should {
      val resourceHolder: ResourceHolder = ResourceHolder()
      val newResourceHolder =
        resourceHolder.increase(
          ResourceHolder(
            energy = Energy(10),
            minerals = Minerals(10),
            researchPoints = ResearchPoints(10),
            alloys = Alloys(10)
          ))
      "store the correct values" in {
        newResourceHolder.minerals.value should be(10)
        newResourceHolder.energy.value should be(10)
        newResourceHolder.researchPoints.value should be(10)
        newResourceHolder.alloys.value should be(10)
      }
      "have the appropriate String representation" in {
        newResourceHolder
          .toString should be("Total Cost: [Energy: 10] [Minerals: 10] [Alloys: 10] [Research Points: 10]")
      }
    }
    "divided by an arbitrary value" should {
      val resourceHolder: ResourceHolder = ResourceHolder()
      val newResourceHolder =
        resourceHolder.increase(
          ResourceHolder(
            energy = Energy(10),
            minerals = Minerals(10),
            researchPoints = ResearchPoints(10),
            alloys = Alloys(10)
          ))
      "have the correct values if divided by 2" in {
        newResourceHolder.divideBy(2).alloys.value should be(5)
        newResourceHolder.divideBy(2).energy.value should be(5)
        newResourceHolder.divideBy(2).researchPoints.value should be(5)
        newResourceHolder.divideBy(2).minerals.value should be(5)
      }
      "have the appropriate String representation" in {
        newResourceHolder.divideBy(2)
          .toString should be("Total Cost: [Energy: 5] [Minerals: 5] [Alloys: 5] [Research Points: 5]")
      }
    }
    "decreased with a ResourceHolder with valid values" should {
      val resourceHolder: ResourceHolder = ResourceHolder()
      val newResourceHolder =
        resourceHolder.increase(
          ResourceHolder(
            energy = Energy(10),
            minerals = Minerals(10),
            researchPoints = ResearchPoints(10),
            alloys = Alloys(10)
          ))
      "return a new ResourceHolder Option with correct values" in {
        newResourceHolder
          .decrease(ResourceHolder(energy = Energy(10), minerals = Minerals(5))).isDefined should be(true)
        newResourceHolder
          .decrease(ResourceHolder(energy = Energy(10), minerals = Minerals(5))).get.minerals.value should be(5)
        newResourceHolder
          .decrease(ResourceHolder(energy = Energy(10), minerals = Minerals(5))).get.energy.value should be(0)
        newResourceHolder
          .decrease(ResourceHolder(energy = Energy(10), minerals = Minerals(5))).get.alloys.value should be(10)
        newResourceHolder
          .decrease(ResourceHolder(energy = Energy(10), minerals = Minerals(5))).get.researchPoints.value should be(10)
      }
      "return a new ResourceHolder Option with correct toString representation" in {
        newResourceHolder
          .decrease(
            ResourceHolder(energy = Energy(10), minerals = Minerals(5)))
          .get.toString should be("Total Cost: [Minerals: 5] [Alloys: 10] [Research Points: 10]")
      }
    }
    "decremented by a ResourceHolder with invalid values" should {
      val resourceHolder: ResourceHolder = ResourceHolder()
      val newResourceHolder =
        resourceHolder.increase(
          ResourceHolder(
            energy = Energy(10),
            minerals = Minerals(10),
            researchPoints = ResearchPoints(10),
            alloys = Alloys(10)
          ))
      "return None" in {
        newResourceHolder.decrease(ResourceHolder(energy = Energy(30))).isDefined should be(false)
      }
    }
    "should when multiplied with an Int value such as 10" should {
      "return a new ResourceHolder with expected values" in {
        val resourceHolder: ResourceHolder =
          ResourceHolder(energy = Energy(10),
            minerals = Minerals(10),
            alloys = Alloys(10),
            researchPoints = ResearchPoints(10))

        resourceHolder.multiplyBy(10).energy.value should be(100)
        resourceHolder.multiplyBy(10).minerals.value should be(100)
        resourceHolder.multiplyBy(10).alloys.value should be(100)
        resourceHolder.multiplyBy(10).researchPoints.value should be(100)
      }
    }
    "hold is applied" should {
      val container: ResourceHolder =
        ResourceHolder(energy = Energy(100),
          minerals = Minerals(100),
          alloys = Alloys(100),
          researchPoints = ResearchPoints(100))

      val contained: ResourceHolder =
        ResourceHolder(energy = Energy(10),
          minerals = Minerals(10),
          alloys = Alloys(20),
          researchPoints = ResearchPoints(50))
      "return the number of ResourceHolders which can be contained within a ResourceHolder" in {
        container.holds(contained).get should be(2)
      }
      "return None if the ResourceHolder cannot be contained within a ResourceHolder" in {
        contained.holds(container).isDefined should be(false)
      }
    }
    "asked for lacking" should {
      val target: ResourceHolder = ResourceHolder(energy = Energy(100),
        minerals = Minerals(100),
        alloys = Alloys(100),
        researchPoints = ResearchPoints(100))
      val start: ResourceHolder = ResourceHolder()
      "return a ResourceHolder which represents the missing resources" in {
        start.lacking(target).energy.value should be(100)
        start.lacking(target).minerals.value should be(100)
        start.lacking(target).alloys.value should be(100)
        start.lacking(target).researchPoints.value should be(100)
      }
    }
  }

}
