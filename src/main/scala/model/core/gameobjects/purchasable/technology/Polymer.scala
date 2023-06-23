package model.core.gameobjects.purchasable.technology

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import model.core.gameobjects.resources.resourcetypes.{Energy, ResearchPoints}
import model.core.utilities.{ResourceHolder, Round}
import utils.CirceImplicits._


case class Polymer(name: String = "Polymer",
                   roundsToComplete: Round = Round(3),
                   cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                   description: String = "Polymer") extends ITechnology:
  
  override def toString: String = "Polymer"

  override def decreaseRoundsToComplete: Polymer =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object Polymer:
  implicit val encoder: Encoder[Polymer] = deriveEncoder[Polymer]
  implicit val decoder: Decoder[Polymer] = deriveDecoder[Polymer]