package model.game.gamestate.strategies.sell

import model.core.board.sector.impl.IPlayerSector
import model.core.mechanics.fleets.Fleet
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{ICapacity, IResourceHolder}
import model.game.gamestate.IGameStateManager
import model.game.playervalues.IPlayerValues

import scala.annotation.tailrec

case class SellUnitStrategy(sector: IPlayerSector, tbr: Seq[IUnit]) extends ISellStrategy:
  override def sell(gsm: IGameStateManager): IGameStateManager =
    val profit = calcProfit(tbr)
    val capGained = returnAccumulated(tbr, (b: IUnit) => b.capacity, (x: ICapacity, y: ICapacity) => x.increase(y))
    val msg = sellSuccessMsg(tbr.head.name, tbr.size, profit)
    val nMap = gsm.gameMap.updateSector(sector.removeUnits(tbr.toVector).get)

    val indexToUpdate = gsm.currentPlayerIndex
    val updatedPlayerValues = gsm.currentPlayerValues.extCopy(
      resourceHolder = gsm.currentPlayerValues.resourceHolder.increase(profit),
      capacity = gsm.currentPlayerValues.capacity.increase(capGained))

    gsm.extCopy(
      gameMap = nMap,
      playerValues = gsm.playerValues.updated(indexToUpdate, updatedPlayerValues),
      message = msg
    )




