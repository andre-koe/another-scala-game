package model.core.utilities

import model.game.purchasable.IGameObject

case class ListOperations():

  def handleList[A <: IGameObject](list: List[A]): (List[A], List[A]) =
    val (completed, inProgress) = list.map(_.decreaseRoundsToComplete).partition(_.roundsToComplete.value != 0)
    (completed.asInstanceOf[List[A]], inProgress.asInstanceOf[List[A]])

