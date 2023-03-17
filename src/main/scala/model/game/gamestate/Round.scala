package model.game.gamestate

case class Round(round: Int = 1):
  def next: Round = Round(round+1)
  def getRoundValue: Int = round
  override def toString: String = f"[Round: $round]"

