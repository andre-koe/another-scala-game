package model.core.utilities.interfaces

import model.core.gameobjects.purchasable.IGameObject
import model.core.utilities.IRound

trait IRoundBasedConstructable:

  def decreaseRoundsToComplete: IRoundBasedConstructable

  def roundsToComplete: IRound
