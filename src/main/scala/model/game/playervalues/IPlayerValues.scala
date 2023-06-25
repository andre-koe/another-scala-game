package model.game.playervalues

import model.core.board.sector.sectorutils.Affiliation
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.utilities.interfaces.IUpkeep
import model.core.utilities.{IAffiliated, ICapacity, IResourceHolder, ResourceHolder}
import utils.IXMLSerializable

import scala.xml.Elem

trait IPlayerValues extends IXMLSerializable, IAffiliated:

  def resourceHolder: IResourceHolder

  def listOfTechnologies: Vector[ITechnology]

  def listOfTechnologiesCurrentlyResearched: Vector[ITechnology]

  def capacity: ICapacity

  def upkeep: IResourceHolder

  def income: IResourceHolder

  def extCopy(resourceHolder: IResourceHolder = resourceHolder,
              listOfTechnologies: Vector[ITechnology] = listOfTechnologies,
              affiliation: Affiliation = affiliation,
              listOfTechnologiesCurrentlyResearched: Vector[ITechnology] = listOfTechnologiesCurrentlyResearched,
              capacity: ICapacity = capacity,
              upkeep: IResourceHolder = upkeep,
              income: IResourceHolder = income): IPlayerValues

  override def affiliation: Affiliation

  override def toXML: scala.xml.Elem =
    <PlayerValues>
      <ResourceHolder>{resourceHolder.toXML}</ResourceHolder>
      <Technologies>{listOfTechnologies.map(tech =>
        <Technology>{tech.toXML}</Technology>)}
      </Technologies>
      <TechnologiesCurrentlyResearched>
        {listOfTechnologiesCurrentlyResearched.map(tech => <Technology>{tech.toXML}</Technology>)}
      </TechnologiesCurrentlyResearched>
      <Affiliation>{affiliation}</Affiliation>
      <Capacity>{capacity.value}</Capacity>
      <Upkeep>{upkeep.toXML}</Upkeep>
      <Income>{income.toXML}</Income>
    </PlayerValues>
