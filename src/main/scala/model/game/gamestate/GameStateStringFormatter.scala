package model.game.gamestate

import model.game.{GameValues, IValues, PlayerValues, Round}
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}

case class GameStateStringFormatter(round: Round = Round(),
                                    playerValues: PlayerValues = PlayerValues(),
                                    userMsg: String = "",
                                    gameValues: IValues = GameValues()):

  def separator(len: Int = 4): String = " |" + "-" * len + "| "
  def vertBar(len: Int = 30): String = "=" * len
  def empty: String = ""
  def overview(round: Round = round, resourceHolder: ResourceHolder = playerValues.resourceHolder): String =
    header(round, resourceHolder) + underConstruction

  def goodbyeResponse: String = f"Goodbye!"

  def invalidInputResponse(msg: String): String =
    f"$msg - invalid\nEnter help to get an overview of all available commands"

  private def header(round: Round = round, resourceHolder: ResourceHolder = playerValues.resourceHolder): String =
    val len: Int = (round.toString + separator() + resourceHolder.toString + separator()).length + 2
    vertBar(len) + "\n" + " " + round + separator()
      + resourceHolder.toString + separator() + " " + "\n" + vertBar(len) + "\n"
  private def underConstruction: String =
    recruiting + researching + building
  private def recruiting: String =
    if playerValues.listOfUnitsUnderConstruction.nonEmpty then
      " Ongoing recruiting: " + playerValues
        .listOfUnitsUnderConstruction.sortBy(_.roundsToComplete.value)
        .map(x => s"[${x.name} | Rounds to complete: ${x.roundsToComplete.value}] ").mkString(" ") + "\n"
    else ""
  private def researching: String =
    if playerValues.listOfTechnologiesCurrentlyResearched.nonEmpty then
      " Ongoing research: " + playerValues
        .listOfTechnologiesCurrentlyResearched.sortBy(_.roundsToComplete.value)
        .map(x => s"[${x.name} | Rounds to complete: ${x.roundsToComplete.value}] ").mkString(" ") + "\n"
    else ""
  private def building: String =
    if playerValues.listOfBuildingsUnderConstruction.nonEmpty then
      " Ongoing construction: " + playerValues
        .listOfBuildingsUnderConstruction.sortBy(_.roundsToComplete.value)
        .map(x => s"[${x.name} | Rounds to complete: ${x.roundsToComplete.value}] ").mkString(" ") + "\n"
    else ""