package model.game

case class Round(value: Int = 1):
  def next: Round = this.copy(value = value+1)
  def decrease: Option[Round] = if this.value - 1 > 0 then Option(Round(value - 1)) else None
  override def toString: String = f"[Round: $value]"

