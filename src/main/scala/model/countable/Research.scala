package model.countable

case class Research(value: Int = 100) extends ICountable[Research] {
  override def increase(other: Research): Research = this.copy(value = value + other.value)
  override def decrease(other: Research): Option[Research] =
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
  override def toString: String = f"[Current research points: $value]"

}
