package model.game.purchasable.utils

import model.game.Capacity
import model.game.resources.ResourceHolder
import model.utils.Increaseable

case class Output(rHolder: ResourceHolder = ResourceHolder(), cap: Capacity = Capacity()) extends Increaseable[Output]:

  def increase(other: Output): Output = 
    this.copy(rHolder = rHolder.increase(other.rHolder), cap = cap.increase(other.cap))


