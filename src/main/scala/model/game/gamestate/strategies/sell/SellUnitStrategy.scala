package model.game.gamestate.strategies.sell

import model.game.Capacity
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import model.game.purchasable.units.IUnit
import model.game.resources.ResourceHolder

case class SellUnitStrategy(units: List[IUnit], profit: ResourceHolder, cap: Capacity) extends ISellStrategy:

  override def sell(gsm: GameStateManager): GameStateManager =
    gsm.copy(
      playerValues = PlayerValues(
        resourceHolder = gsm.playerValues.resourceHolder.increase(profit),
        capacity = gsm.playerValues.capacity.increase(cap),
        listOfUnits = units)
    )
