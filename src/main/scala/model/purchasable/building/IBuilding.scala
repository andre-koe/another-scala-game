package model.purchasable.building

import model.purchasable.utils.Output
import model.purchasable.{IGameObject, IUpkeep}

trait IBuilding extends IUpkeep:
  
  def output: Output
