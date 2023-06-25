package model.core.utilities

import io.circe.generic.auto.*
import io.circe.syntax.*

case class Round(value: Int = 1) extends IRound:

  def next: Round = this.copy(value = value+1)
  
  def decrease: Option[Round] = if value - 1 >= 0 then Option(Round(value - 1)) else None
  
  override def toString: String = f"[Round: $value]"
