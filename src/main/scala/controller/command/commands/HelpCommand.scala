package controller.command.commands

import controller.command.ICommand
import model.game.GameValues
import model.game.gamestate.IGameStateManager
import model.purchasable.building.IBuilding
import model.purchasable.technology.ITechnology
import model.purchasable.units.IUnit

case class HelpCommand(string: String,
                       gameStateManager: IGameStateManager,
                       gameValues: GameValues = GameValues()) extends ICommand:
  override def execute(): IGameStateManager =
    string match
      case "building" => gameStateManager.message(helpBuilding)
      case "technology" => gameStateManager.message(helpTech)
      case "unit" => gameStateManager.message(helpUnit)
      case "" => gameStateManager.message(defaultHelpResponse)
      case _ => gameStateManager.message(findInLists(string))
  private def helpBuilding: String = "A building can impact the game in various ways, " +
    "such as increasing research output, providing energy, or increasing unit capacity."
  private def helpTech: String = "A technology in the game that can be researched by " +
    "players to unlock new abilities, units, or buildings."
  private def helpUnit: String = "A unit can be used to fight over sectors and conquer new sectors."
  private def defaultHelpResponse: String =
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
  private def findInLists(str: String): String =
    val tech = existsInTech(str)
    val building = existsInBuildings(str)
    val unit = existsInUnits(str)

    if (tech.isDefined)
      tech.get.description
    else if (building.isDefined)
      building.get.description
    else if (unit.isDefined)
      unit.get.description
    else
      s"Could not find any information on '$str'"
  private def existsInTech(name: String): Option[ITechnology] =
    gameValues.listOfTechnologies.find(_.name.toLowerCase == name)
  private def existsInBuildings(name: String): Option[IBuilding] =
    gameValues.listOfBuildings.find(_.name.toLowerCase == name)
  private def existsInUnits(name: String): Option[IUnit] =
    gameValues.listOfUnits.find(_.name.toLowerCase() == name)
