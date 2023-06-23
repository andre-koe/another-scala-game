package model.game.gamestate.strategies.sell

import model.core.gameobjects.purchasable.IGameObject
import model.core.utilities.IResourceHolder
import model.core.utilities.interfaces.{IPurchasable, IUpkeep}
import model.game.gamestate.IGameStateManager

trait ISellStrategy:
  
  def sell(gsm: IGameStateManager): IGameStateManager

  def calcProfit(l: Seq[IPurchasable]): IResourceHolder =
    returnAccumulated(l, (x: IPurchasable) =>
      x.cost, (x: IResourceHolder, y: IResourceHolder) => x.increase(y)).divideBy(2)

  def removeFromSeq[T <: IGameObject](s: Seq[T], qty: Int, what: T): (Seq[T], Seq[T]) =
    val sf: Seq[T] = s.filter(_.name == what.name)
    val actualQty = math.min(qty, sf.length)
    (s diff sf.take(actualQty), sf.take(actualQty))

  def returnAccumulated[T, R](l: Seq[T], f: T => R, combiner: (R, R) => R): R =
    if l.length > 1 then l.map(f).reduce(combiner) else l.map(f).head

  private def capitalize(str: String): String = s"${str.split(" ").map(_.capitalize).mkString(" ")}"

  def sellSuccessMsg(str: String, quantity: Int, profit: IResourceHolder): String =
    s"Successfully Sold: $quantity x ${capitalize(str)} for a profit of $profit."