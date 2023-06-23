package model.game.gamestate.strategies.sell

import model.core.board.sector.impl.IPlayerSector
import model.core.mechanics.fleets.Fleet
import model.core.mechanics.fleets.components.Component
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{ICapacity, IResourceHolder}
import model.game.gamestate.IGameStateManager
import model.game.playervalues.IPlayerValues

import scala.annotation.tailrec

case class SellUnitStrategy(sector: IPlayerSector, tbr: Seq[IUnit]) extends ISellStrategy:
  override def sell(gsm: IGameStateManager): IGameStateManager =
    val profit = calcProfit(tbr)
    val capGained = returnAccumulated(tbr, (b: Component) => b.capacity, (x: ICapacity, y: ICapacity) => x.increase(y))
    val msg = sellSuccessMsg(tbr.head.name, tbr.size, profit)
    val nMap = gsm.gameMap.updateSector(sector.removeUnits(tbr.toVector).get)

    gsm.extCopy(
      gameMap = nMap,
      playerValues = gsm.playerValues.extCopy(
        resourceHolder = gsm.playerValues.resourceHolder.increase(profit),
        capacity = gsm.playerValues.capacity.increase(capGained)),
      message = msg
    )




