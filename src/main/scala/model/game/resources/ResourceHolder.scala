package model.game.resources

import model.game.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}

import scala.annotation.tailrec
import scala.jdk.Accumulator

case class ResourceHolder(descriptor: String = "Cost",
                          energy: Energy = Energy(0),
                          minerals: Minerals = Minerals(0),
                          alloys: Alloys = Alloys(0),
                          researchPoints: ResearchPoints = ResearchPoints(0)):

  def increase(other: ResourceHolder): ResourceHolder =
    val newEnergy = energy.increase(other.energy)
    val newMinerals = minerals.increase(other.minerals)
    val newAlloys = alloys.increase(other.alloys)
    val newResearchPoints = researchPoints.increase(other.researchPoints)

    this.copy(
      energy = newEnergy,
      minerals = newMinerals,
      alloys = newAlloys,
      researchPoints = newResearchPoints)
  def decrease(other: ResourceHolder): Option[ResourceHolder] =
    val newEnergy = energy.decrease(other.energy)
    val newMinerals = minerals.decrease(other.minerals)
    val newAlloys = alloys.decrease(other.alloys)
    val newResearchPoints = researchPoints.decrease(other.researchPoints)

    if newEnergy.isDefined & newMinerals.isDefined & newAlloys.isDefined & newResearchPoints.isDefined
    then Option(this.copy(
      energy = newEnergy.get,
      minerals = newMinerals.get, 
      alloys = newAlloys.get, 
      researchPoints = newResearchPoints.get))
    else None
  def divideBy(divisor: Int): ResourceHolder =
    this.copy(
      energy = Energy(energy.value / divisor),
      minerals = Minerals(minerals.value / divisor),
      alloys = Alloys(alloys.value / divisor),
      researchPoints = ResearchPoints(researchPoints.value / divisor))
  def multiplyBy(multiplier: Int): ResourceHolder =
    this.copy(
      energy = Energy(energy.value * multiplier),
      minerals = Minerals(minerals.value * multiplier),
      alloys = Alloys(alloys.value * multiplier),
      researchPoints = ResearchPoints(researchPoints.value * multiplier))
  def lacking(other: ResourceHolder): ResourceHolder =
    ResourceHolder(descriptor = "Lacking").increase(difference(other)).onlyNegative.divideBy(-1)

  def holds(other: ResourceHolder): Option[Int] =
    @tailrec
    def acc(thisRes: ResourceHolder, otherRes: ResourceHolder, accumulator: Int): Option[Int] =
      thisRes.decrease(otherRes) match
        case Some(value) => acc(value, otherRes, accumulator + 1)
        case None => if accumulator > 0 then Option(accumulator) else None
    acc(this, other, 0)
    
  def subtract(other: ResourceHolder): ResourceHolder =
    val nEnergy: Energy = Energy(energy.value - other.energy.value)
    val nAlloys: Alloys = Alloys(alloys.value - other.alloys.value)
    val nMinerals: Minerals = Minerals(minerals.value - other.minerals.value)
    val nResearchPoints: ResearchPoints = ResearchPoints(researchPoints.value - other.researchPoints.value)

    this.copy(
      energy = nEnergy,
      minerals = nMinerals,
      alloys = nAlloys,
      researchPoints = nResearchPoints)

  override def toString: String =
    s"Total $descriptor: $energyString$mineralsString$alloysString$researchPointsString".stripTrailing()
  private def alloysString: String =
    if alloys.value > 0 then alloys.toString + " " else ""
  private def energyString: String =
    if energy.value > 0 then energy.toString + " " else ""
  private def mineralsString: String =
    if minerals.value > 0 then minerals.toString + " " else ""
  private def researchPointsString: String =
    if researchPoints.value > 0 then researchPoints.toString + " " else ""
  private def onlyNegative: ResourceHolder =
    val nEnergy: Energy = if energy.value < 0 then Energy(energy.value) else Energy()
    val nMinerals: Minerals = if minerals.value < 0 then Minerals(minerals.value) else Minerals()
    val nAlloys: Alloys = if alloys.value < 0 then Alloys(alloys.value) else Alloys()
    val nResearchPoints: ResearchPoints =
      if researchPoints.value < 0 then ResearchPoints(researchPoints.value) else ResearchPoints()

    this.copy(
      energy = nEnergy,
      minerals = nMinerals,
      alloys = nAlloys,
      researchPoints = nResearchPoints
    )
  private def difference(other: ResourceHolder): ResourceHolder =
    val nEnergy: Energy = if other.energy.value > 0 then Energy(energy.value - other.energy.value) else Energy()
    val nAlloys: Alloys = if other.alloys.value > 0 then Alloys(alloys.value - other.alloys.value) else Alloys()
    val nMinerals: Minerals =
      if other.minerals.value > 0 then Minerals(minerals.value - other.minerals.value) else Minerals()
    val nResearchPoints: ResearchPoints =
      if other.researchPoints.value > 0
      then ResearchPoints(researchPoints.value - other.researchPoints.value)
      else ResearchPoints()

    this.copy(
      energy = nEnergy,
      minerals = nMinerals,
      alloys = nAlloys,
      researchPoints = nResearchPoints
    )