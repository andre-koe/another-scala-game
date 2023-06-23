package model.game.playervalues

import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.utilities.interfaces.IUpkeep
import model.core.utilities.{ICapacity, IResourceHolder, ResourceHolder}
import utils.IXMLSerializable

import scala.xml.Elem

trait IPlayerValues extends IXMLSerializable:

  def resourceHolder: IResourceHolder

  def listOfTechnologies: Vector[ITechnology]

  def listOfTechnologiesCurrentlyResearched: Vector[ITechnology]

  def capacity: ICapacity

  def upkeep: IResourceHolder

  def income: IResourceHolder

  def extCopy(resourceHolder: IResourceHolder = resourceHolder,
              listOfTechnologies: Vector[ITechnology] = listOfTechnologies,
              listOfTechnologiesCurrentlyResearched: Vector[ITechnology] = listOfTechnologiesCurrentlyResearched,
              capacity: ICapacity = capacity,
              upkeep: IResourceHolder = upkeep,
              income: IResourceHolder = income): IPlayerValues

  override def toXML: scala.xml.Elem =
    <PlayerValues>
      <ResourceHolder>{resourceHolder.toXML}</ResourceHolder>
      <Technologies>{listOfTechnologies.map(tech =>
        <Technology>{tech.toXML}</Technology>)}
      </Technologies>
      <TechnologiesCurrentlyResearched>
        {listOfTechnologiesCurrentlyResearched.map(tech => <Technology>{tech.toXML}</Technology>)}
      </TechnologiesCurrentlyResearched>
      <Capacity>{capacity.value}</Capacity>
      <Upkeep>{upkeep.toXML}</Upkeep>
      <Income>{income.toXML}</Income>
    </PlayerValues>
