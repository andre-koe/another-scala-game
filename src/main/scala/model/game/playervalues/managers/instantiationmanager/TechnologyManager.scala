package model.game.playervalues.managers.instantiationmanager

import model.game.purchasable.technology.ITechnology

case class TechnologyManager(research: List[ITechnology],
                             technology: List[ITechnology]) extends InstantiationManager[ITechnology]:

  override def addToProduction(rec: ITechnology): InstantiationManager[ITechnology] =
    this.copy(research.+:(rec))

  override def addToInventory(rec: ITechnology): InstantiationManager[ITechnology] =
    this.copy(technology.+:(rec))