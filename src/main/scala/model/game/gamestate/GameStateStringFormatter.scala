package model.game.gamestate

import model.countable.{Balance, Research}
import model.game.Round

case class GameStateStringFormatter(round: Round = Round(),
                                    funds: Balance = Balance(),
                                    researchOutput: Research = Research(),
                                    userMsg: String = ""):

  def separator(len: Int = 4): String = " |" + "-" * len + "| "
  def vertBar(len: Int = 30): String = "=" * len
  def empty: String = ""
  def overview(round: Round = round, funds: Balance = funds, research: Research= researchOutput): String = {
    val len: Int = (round.toString + funds.toString + research.toString + separator() + separator()).length + 2
    vertBar(len) + "\n" + " " + round + separator() + funds + separator() + research + " " + "\n" + vertBar(len)
  }

  def askForConfirmation: String = "Are you sure? [yes (y) / no (n)]"

  def helpResponse: String =
    f"""The following actions should be implemented:
       | > [overview] | to get an overview of your current account balance/research points
       | > [build] <building name> | to begin construction of a building
       | > [recruit] <unit name> (quantity) | to begin recruitment of 1 or (quantity) units
       | > [research] <technology name> | to begin researching a technology
       | > [done] | to end the current round
       | > [help | h] | to show this menu
       | > [exit | quit] to quit
       | > [undo] to undo the last action
       | > [redo] to undo the last redo""".stripMargin

  def goodbyeResponse: String = f"Goodbye!"

  def invalidInputResponse(msg: String): String = f"$msg\nEnter help to get an overview of all available commands"