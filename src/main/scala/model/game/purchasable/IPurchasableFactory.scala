package model.game.purchasable

trait IPurchasableFactory[T<:IGameObject]:
  def create(name: String): Option[T]
