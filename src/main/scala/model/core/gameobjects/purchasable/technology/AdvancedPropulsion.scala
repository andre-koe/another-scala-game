package model.core.gameobjects.purchasable.technology

import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import io.circe.{Decoder, Encoder}
import model.core.gameobjects.resources.resourcetypes.{Energy, ResearchPoints}
import model.core.utilities.{ResourceHolder, Round}
import utils.CirceImplicits._


case class AdvancedPropulsion(name: String = "Advanced Propulsion", 
                              roundsToComplete: Round = Round(3),
                              cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                              description: String = "Advanced Propulsion") extends ITechnology:


  override def toString: String
  = "Advanced Propulsion"

  override def decreaseRoundsToComplete: AdvancedPropulsion =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object AdvancedPropulsion:
  implicit val encoder: Encoder[AdvancedPropulsion] = deriveEncoder[AdvancedPropulsion]
  implicit val decoder: Decoder[AdvancedPropulsion] = deriveDecoder[AdvancedPropulsion]