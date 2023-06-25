package model.core.utilities

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.gameobjects.resources.IResource
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.utils.Increaseable

import java.lang.reflect.Constructor
import scala.annotation.tailrec
import scala.jdk.Accumulator


case class ResourceHolder(descriptor: String = "Cost",
                          energy: Energy = Energy(0),
                          minerals: Minerals = Minerals(0),
                          alloys: Alloys = Alloys(0),
                          researchPoints: ResearchPoints = ResearchPoints(0)) extends IResourceHolder:

  override def increase(other: IResourceHolder): IResourceHolder =
    val nEnergy = energy.increase(other.energy)
    val nMinerals = minerals.increase(other.minerals)
    val nAlloys = alloys.increase(other.alloys)
    val nResearchPoints = researchPoints.increase(other.researchPoints)

    this.copy(energy = nEnergy, minerals = nMinerals, alloys = nAlloys, researchPoints = nResearchPoints)

  def decrease(other: IResourceHolder): Option[IResourceHolder] =
    val nEnergy = energy.decrease(other.energy)
    val nMinerals = minerals.decrease(other.minerals)
    val nAlloys = alloys.decrease(other.alloys)
    val nResearchPoints = researchPoints.decrease(other.researchPoints)

    if nEnergy.isDefined & nMinerals.isDefined & nAlloys.isDefined & nResearchPoints.isDefined
    then
      Some(
        this.copy(energy = nEnergy.get, minerals = nMinerals.get,
          alloys = nAlloys.get, researchPoints = nResearchPoints.get))
    else None
    
  def divideBy(divisor: Int): IResourceHolder =
    this.copy(
      energy = Energy(energy.value / divisor),
      minerals = Minerals(minerals.value / divisor),
      alloys = Alloys(alloys.value / divisor),
      researchPoints = ResearchPoints(researchPoints.value / divisor))
    
  def multiplyBy(multiplier: Int): IResourceHolder =
    this.copy(
      energy = Energy(energy.value * multiplier),
      minerals = Minerals(minerals.value * multiplier),
      alloys = Alloys(alloys.value * multiplier),
      researchPoints = ResearchPoints(researchPoints.value * multiplier))
    
  def lacking(other: IResourceHolder): IResourceHolder =
    ResourceHolder(descriptor = "Lacking").increase(difference(other)).onlyNegative().divideBy(-1)

  def holds(other: IResourceHolder): Option[Int] =
    @tailrec
    def acc(thisRes: IResourceHolder, otherRes: IResourceHolder, accumulator: Int): Option[Int] =
      thisRes.decrease(otherRes) match
        case Some(value) => acc(value, otherRes, accumulator + 1)
        case None => if accumulator > 0 then Option(accumulator) else None
    acc(this, other, 0)
    
  def subtract(other: IResourceHolder): IResourceHolder =
    val nEnergy: Energy = Energy(energy.value - other.energy.value)
    val nAlloys: Alloys = Alloys(alloys.value - other.alloys.value)
    val nMinerals: Minerals = Minerals(minerals.value - other.minerals.value)
    val nResearchPoints: ResearchPoints = ResearchPoints(researchPoints.value - other.researchPoints.value)

    this.copy(energy = nEnergy, minerals = nMinerals, alloys = nAlloys, researchPoints = nResearchPoints)

  def resourcesAsVector: Vector[IResource[_]] = Vector(energy, minerals, alloys, researchPoints)

  def onlyNegative(): IResourceHolder =
    val nEnergy: Energy = negRes(energy)(value => Energy(value))
    val nMinerals: Minerals = negRes(minerals)(value => Minerals(value))
    val nAlloys: Alloys = negRes(alloys)(value => Alloys(value))
    val nResearchPoints: ResearchPoints = negRes(researchPoints)(value => ResearchPoints(value))

    this.copy(energy = nEnergy, minerals = nMinerals, alloys = nAlloys, researchPoints = nResearchPoints)

  override def toString: String =
    s"Total $descriptor: " +
      s"${resToString(energy)}${resToString(minerals)}${resToString(alloys)}${resToString(researchPoints)}"
        .stripTrailing()

  private def resToString[T](res: IResource[T]): String =
    if res.value > 0 then res.toString + " " else ""

  private def resDiff[A,T <: IResource[A]](l: T ,r: T)(constructor: Int => T): T =
    if r.value > l.value then constructor(l.value - r.value) else constructor(0)

  private def negRes[A, T <: IResource[A]](l: T)(constructor: Int => T): T =
    if l.value < 0 then constructor(l.value) else constructor(0)

  private def difference(other: IResourceHolder): IResourceHolder =
    val nEnergy: Energy = resDiff(energy, other.energy)(value => Energy(value))
    val nAlloys: Alloys = resDiff(alloys, other.alloys)(value => Alloys(value))
    val nMinerals: Minerals = resDiff(minerals, other.minerals)(value => Minerals(value))
    val nResearchPoints: ResearchPoints = resDiff(researchPoints, other.researchPoints)(value => ResearchPoints(value))

    this.copy(energy = nEnergy, minerals = nMinerals, alloys = nAlloys, researchPoints = nResearchPoints)
