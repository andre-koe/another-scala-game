package model.core.utilities

import model.core.gameobjects.purchasable.IGameObject
import model.core.utilities.interfaces.IRoundBasedConstructable

case class SeqOperations():

  def partitionOnRounds0[A <: IRoundBasedConstructable](list: Seq[A]): (Seq[A], Seq[A]) =
    val (completed, inProgress) = list.map(_.decreaseRoundsToComplete).partition(_.roundsToComplete.value != 0)
    (completed.asInstanceOf[Seq[A]], inProgress.asInstanceOf[Seq[A]])

