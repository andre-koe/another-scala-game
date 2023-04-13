package model.purchasable.utils

import model.game.Capacity
import model.resources.ResourceHolder

case class Output(resourceHolder: ResourceHolder = ResourceHolder(), capacity: Capacity = Capacity(0)):

  def increaseOutput(other: Output): Output =
    this.copy(
      resourceHolder = resourceHolder.increase(other.resourceHolder), capacity = capacity.increase(other.capacity)
    )


