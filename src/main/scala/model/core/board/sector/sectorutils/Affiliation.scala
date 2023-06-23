package model.core.board.sector.sectorutils

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

enum Affiliation:
  case INDEPENDENT
  case PLAYER
  case ENEMY


object Affiliation:

  implicit val affiliationDecoder: Decoder[Affiliation] = deriveDecoder

  implicit val affiliationEncoder: Encoder[Affiliation] = deriveEncoder
