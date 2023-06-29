package model.core.gameobjects.purchasable

import io.circe.generic.auto.*
import model.core.utilities.{IResourceHolder, IRound}

/** All model components which can be owned by the player derive from 'IGameObject' */
trait IGameObject:

  /** The name of the object
   *
   *  @return name as String
   */
  def name: String


  /** The decription of the object
   *
   *  @return desription as String
   */
  def description: String
  

