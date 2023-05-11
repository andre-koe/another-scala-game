package model.game.purchasable.building

import model.game.purchasable.{IGameObject, IUpkeep}
import model.game.purchasable.utils.Output

trait IBuilding extends IUpkeep:
  
  def output: Output
