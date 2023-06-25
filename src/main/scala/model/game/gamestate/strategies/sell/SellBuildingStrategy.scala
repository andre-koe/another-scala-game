package model.game.gamestate.strategies.sell

import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.utils.IOutput
import model.game.gamestate.IGameStateManager

case class SellBuildingStrategy(location: IPlayerSector, str: IBuilding, quantity: Int) extends ISellStrategy:

  override def sell(gsm: IGameStateManager): IGameStateManager =
    val buildings = removeFromSeq(location.buildingsInSector, quantity, str)
    val profit = calcProfit(buildings._2)
    val capLost = returnAccumulated(buildings._2, (b: IBuilding) => b.output, (x:IOutput, y:IOutput) => x.increase(y)).cap
    val msg = sellSuccessMsg(str.name, quantity, profit)
    
    val nMap = gsm.gameMap.updateSector(location.extCopy(buildingsInSector = buildings._1.toVector))

    val indexToUpdate = gsm.currentPlayerIndex
    val updatedPlayerValues = gsm.currentPlayerValues.extCopy(
      resourceHolder = gsm.currentPlayerValues.resourceHolder.increase(profit),
      capacity = gsm.currentPlayerValues.capacity.decrease(capLost).get)
    
    gsm.extCopy(
      gameMap = nMap,
      playerValues = gsm.playerValues.updated(indexToUpdate, updatedPlayerValues),
      message = msg
    )
