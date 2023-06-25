package model.utils

import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.utils.IOutput
import model.core.utilities.{Capacity, ICapacity, IResourceHolder, ResourceHolder}

class General {
  
  def generatedIncomeFromSector(sector: IPlayerSector): IResourceHolder =
    sector.buildingsInSector.map(_.output).map(_.rHolder)
      .foldLeft(ResourceHolder())((x:IResourceHolder, y:IResourceHolder) => x.increase(y))

  def generatedCapacityFromSector(sector: IPlayerSector): ICapacity =
    sector.buildingsInSector.map(_.output).map(_.cap)
      .foldLeft(Capacity())((x: ICapacity, y: ICapacity) => x.increase(y))
    
  def upkeepFromBuildings(sector: IPlayerSector): IResourceHolder =
    sector.buildingsInSector.map(_.upkeep)
      .foldLeft(ResourceHolder())((x:IResourceHolder, y:IResourceHolder) => x.increase(y))
    
  def upkeepFromUnitsInSector(sector: IPlayerSector): IResourceHolder =
    sector.unitsInSector.map(_.upkeep)
      .foldLeft(ResourceHolder())((x:IResourceHolder, y:IResourceHolder) => x.increase(y))

}
