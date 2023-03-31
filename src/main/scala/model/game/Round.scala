package model.game

case class Round(value: Int = 1):
  def next: Round = this.copy(value = value+1)
  override def toString: String = f"[Round: $value]"

