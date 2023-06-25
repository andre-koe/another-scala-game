package model.core.gameobjects.purchasable.utils

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.utilities.{Capacity, ICapacity, IResourceHolder, ResourceHolder}

case class Output(rHolder: IResourceHolder = ResourceHolder(), cap: ICapacity = Capacity()) extends IOutput:
  
  def increase(other: IOutput): IOutput = 
    this.copy(rHolder = rHolder.increase(other.rHolder), cap = cap.increase(other.cap))


