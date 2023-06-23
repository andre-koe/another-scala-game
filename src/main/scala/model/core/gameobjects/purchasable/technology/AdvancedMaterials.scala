package model.core.gameobjects.purchasable.technology

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import model.core.gameobjects.resources.resourcetypes.{Energy, ResearchPoints}
import model.core.utilities.{IResourceHolder, IRound, ResourceHolder, Round}
import utils.CirceImplicits._


case class AdvancedMaterials(name: String = "Advanced Materials",
                             roundsToComplete: IRound = Round(3),
                             cost: IResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                             description: String = "Advanced Materials") extends ITechnology:
  
  override def toString: String
  = "Advanced Materials"

  override def decreaseRoundsToComplete: AdvancedMaterials =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object AdvancedMaterials:
  implicit val encoder: Encoder[AdvancedMaterials] = deriveEncoder[AdvancedMaterials]
  implicit val decoder: Decoder[AdvancedMaterials] = deriveDecoder[AdvancedMaterials]
    


