package model.core.gameobjects.purchasable.utils

import model.core.utilities.{ICapacity, IResourceHolder}

trait IOutput:

  def rHolder: IResourceHolder
  
  def cap: ICapacity
  
  def increase(other: IOutput): IOutput