package model.game.gamestate.strategies.sell

import model.game.{Capacity, PlayerValues}
import model.game.gamestate.GameStateManager
import model.game.purchasable.building.IBuilding
import model.game.resources.ResourceHolder

case class SellBuildingStrategy(buildings: List[IBuilding], profit: ResourceHolder, cap: Capacity) extends ISellStrategy:

  override def sell(gsm: GameStateManager): GameStateManager =
    gsm.copy(
      playerValues = PlayerValues(
        resourceHolder = gsm.playerValues.resourceHolder.increase(profit), 
        capacity = gsm.playerValues.capacity.decrease(cap).get, 
        listOfBuildings = buildings)
    )
