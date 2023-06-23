package model.core.gameobjects.purchasable

import io.circe.generic.auto.*
import model.core.utilities.{IResourceHolder, IRound}

trait IGameObject:
  
  def name: String

  def description: String
  

