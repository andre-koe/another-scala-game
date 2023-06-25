package model.core.gameobjects.purchasable.technology

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import model.core.gameobjects.resources.resourcetypes.{Energy, ResearchPoints}
import model.core.utilities.{ResourceHolder, Round}
import utils.CirceImplicits._

case class NanoRobotics(name: String = "Nano Robotics",
                        roundsToComplete: Round = Round(3),
                        cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                        description: String = "Nano Robotics") extends ITechnology:
  
  override def toString: String
  = "Nano Robotics"

  override def decreaseRoundsToComplete: NanoRobotics =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object NanoRobotics:
  implicit val encoder: Encoder[NanoRobotics] = deriveEncoder[NanoRobotics]
  implicit val decoder: Decoder[NanoRobotics] = deriveDecoder[NanoRobotics]
