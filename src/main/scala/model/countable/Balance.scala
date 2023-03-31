package model.countable

case class Balance(value: Int = 1000) extends ICountable[Balance] {
  override def increase(other: Balance): Balance = this.copy(value = value + other.value)
  override def decrease(other: Balance): Option[Balance] = 
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
  override def toString: String = f"[Current balance: $value]"
}
