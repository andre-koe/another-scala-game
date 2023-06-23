package model.core.utilities

import model.core.gameobjects.resources.IResource
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import utils.IXMLSerializable

import scala.annotation.tailrec
import scala.xml.Elem

trait IResourceHolder extends IXMLSerializable:

  def descriptor: String

  def energy: Energy

  def minerals: Minerals

  def alloys: Alloys

  def researchPoints: ResearchPoints

  def increase(other: IResourceHolder): IResourceHolder

  def decrease(other: IResourceHolder): Option[IResourceHolder]

  def divideBy(divisor: Int): IResourceHolder

  def multiplyBy(multiplier: Int): IResourceHolder

  def lacking(other: IResourceHolder): IResourceHolder

  def holds(other: IResourceHolder): Option[Int]

  def subtract(other: IResourceHolder): IResourceHolder

  def resourcesAsVector: Vector[IResource[_]]

  def onlyNegative(): IResourceHolder

  override def toXML: scala.xml.Elem =
    <ResourceHolder>
      <Descriptor>{descriptor}</Descriptor>
      <Energy>{energy.value}</Energy>
      <Minerals>{minerals.value}</Minerals>
      <ResearchPoints>{researchPoints.value}</ResearchPoints>
      <Alloys>{alloys.value}</Alloys>
    </ResourceHolder>