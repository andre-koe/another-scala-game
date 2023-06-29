package model.game.gamestate.strategies.sell

import model.core.gameobjects.purchasable.IGameObject
import model.core.utilities.IResourceHolder
import model.core.utilities.interfaces.{IPurchasable, IUpkeep}
import model.game.gamestate.IGameStateManager
/** Interface representing a sell strategy.
 *  The sell strategy provides methods for selling game objects and calculating profits.
 */
trait ISellStrategy:

  /** Sells game objects and returns the updated game state manager.
   *
   *  @param gsm: The game state manager.
   *  @return IGameStateManager: The updated game state manager after selling.
   */
  def sell(gsm: IGameStateManager): IGameStateManager

  /** Calculates the profit based on the given sequence of purchasable items.
   *
   *  @param l: The sequence of purchasable items.
   *  @return IResourceHolder: The calculated profit as a resource holder.
   */
  def calcProfit(l: Seq[IPurchasable]): IResourceHolder =
    returnAccumulated(l, (x: IPurchasable) => x.cost, (x: IResourceHolder, y: IResourceHolder) => x.increase(y)).divideBy(2)

  /** Removes a specified quantity of a game object from a sequence.
   *
   *  @param s: The sequence of game objects.
   *  @param qty: The quantity to be removed.
   *  @param what: The game object to be removed.
   *  @return (Seq[T], Seq[T]): A tuple containing the updated sequence with the specified quantity removed
   *                            and the removed game objects.
   */
  def removeFromSeq[T <: IGameObject](s: Seq[T], qty: Int, what: T): (Seq[T], Seq[T]) =
    val sf: Seq[T] = s.filter(_.name == what.name)
    val actualQty = math.min(qty, sf.length)
    (s diff sf.take(actualQty), sf.take(actualQty))

  /** Returns the accumulated result of applying a function to a sequence of items and combining the results.
   *
   *  @param l: The sequence of items.
   *  @param f: The function to be applied to each item.
   *  @param combiner: The function to combine the results.
   *  @return R: The accumulated result of applying the function and combining the results.
   */
  def returnAccumulated[T, R](l: Seq[T], f: T => R, combiner: (R, R) => R): R =
    if l.length > 1 then l.map(f).reduce(combiner) else l.map(f).head

  /** Capitalizes the given string.
   *
   *  @param str: The string to be capitalized.
   *  @return String: The capitalized string.
   */
  private def capitalize(str: String): String = s"${str.split(" ").map(_.capitalize).mkString(" ")}"

  /** Generates a success message for a successful sell operation.
   *
   *  @param str: The name of the sold item.
   *  @param quantity: The quantity sold.
   *  @param profit: The profit obtained from the sell operation.
   *  @return String: The success message.
   */
  def sellSuccessMsg(str: String, quantity: Int, profit: IResourceHolder): String =
    s"Successfully Sold: $quantity x ${capitalize(str)} for a profit of $profit."
