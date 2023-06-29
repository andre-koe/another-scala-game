package model.game.playervalues

import model.core.board.sector.sectorutils.Affiliation
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.utilities.interfaces.IUpkeep
import model.core.utilities.{IAffiliated, ICapacity, IResourceHolder, ResourceHolder}
import utils.IXMLSerializable

import scala.xml.Elem

/** Interface representing player values in the game.
 *  Provides methods to retrieve and manipulate player-specific information such as resources, technologies, capacity, upkeep, and income.
 */
trait IPlayerValues extends IXMLSerializable, IAffiliated:

  /** Retrieves the resource holder associated with the player.
   *
   *  @return IResourceHolder: The resource holder associated with the player.
   */
  def resourceHolder: IResourceHolder

  /** Retrieves the list of technologies owned by the player.
   *
   *  @return Vector[ITechnology]: The list of technologies owned by the player.
   */
  def listOfTechnologies: Vector[ITechnology]

  /** Retrieves the list of technologies currently being researched by the player.
   *
   *  @return Vector[ITechnology]: The list of technologies currently being researched by the player.
   */
  def listOfTechnologiesCurrentlyResearched: Vector[ITechnology]

  /** Retrieves the capacity of the player.
   *
   *  @return ICapacity: The capacity of the player.
   */
  def capacity: ICapacity

  /** Retrieves the upkeep of the player.
   *
   *  @return IResourceHolder: The upkeep of the player.
   */
  def upkeep: IResourceHolder

  /** Retrieves the income of the player.
   *
   *  @return IResourceHolder: The income of the player.
   */
  def income: IResourceHolder

  /** Creates a copy of the player values with optional parameters overridden.
   *
   *  @param resourceHolder                 The resource holder to override.
   *  @param listOfTechnologies             The list of technologies to override.
   *  @param affiliation                    The affiliation to override.
   *  @param listOfTechnologiesCurrentlyResearched  The list of technologies currently being researched to override.
   *  @param capacity                       The capacity to override.
   *  @param upkeep                         The upkeep to override.
   *  @param income                         The income to override.
   *  @return IPlayerValues: The new player values with overridden parameters.
   */
  def extCopy(resourceHolder: IResourceHolder = resourceHolder,
              listOfTechnologies: Vector[ITechnology] = listOfTechnologies,
              affiliation: Affiliation = affiliation,
              listOfTechnologiesCurrentlyResearched: Vector[ITechnology] = listOfTechnologiesCurrentlyResearched,
              capacity: ICapacity = capacity,
              upkeep: IResourceHolder = upkeep,
              income: IResourceHolder = income): IPlayerValues

  /** Retrieves the affiliation of the player.
   *
   *  @return Affiliation: The affiliation of the player.
   */
  override def affiliation: Affiliation

  /** Converts the player values to XML format.
   *
   *  @return Elem: The XML representation of the player values.
   */
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
