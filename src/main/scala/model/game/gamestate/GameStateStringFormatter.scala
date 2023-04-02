package model.game.gamestate

import model.countable.{Balance, Research}
import model.game.{GameValues, IValues, Round}

case class GameStateStringFormatter(round: Round = Round(),
                                    funds: Balance = Balance(),
                                    researchOutput: Research = Research(),
                                    userMsg: String = "",
                                    gameValues: IValues = GameValues()):

  def separator(len: Int = 4): String = " |" + "-" * len + "| "
  def vertBar(len: Int = 30): String = "=" * len
  def empty: String = ""
  def showMessage: String = userMsg
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

  def invalidInputResponse(msg: String): String =
    f"$msg - invalid\nEnter help to get an overview of all available commands"

  def listBuildings: String =
    s"==== Buildings ====\n${gameValues.listOfBuildings.map(x => prependString(x.name)).mkString("\n")}\n"
  def listUnits: String =
    s"==== Units ====\n${gameValues.listOfUnits.map(x => prependString(x.name)).mkString("\n")}\n"
  def listTechnologies: String =
    s"==== Technologies ====\n${gameValues.listOfTechnologies.map(x => prependString(x.name)).mkString("\n")}\n"
  def listAll: String = listBuildings + listTechnologies + listUnits

  private def prependString(str: String) = str.prependedAll(" - ")