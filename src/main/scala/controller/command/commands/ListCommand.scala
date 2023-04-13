package controller.command.commands

import controller.command.ICommand
import model.game.GameValues
import model.game.gamestate.IGameStateManager
import model.purchasable.IGameObject
import model.purchasable.building.IBuilding
import model.purchasable.technology.ITechnology
import model.purchasable.types.EntityType.{BUILDING, TECHNOLOGY, UNIT}
import model.purchasable.units.IUnit
import model.resources.ResourceHolder

import scala.io
import scala.io.AnsiColor

case class ListCommand(string: String,
                       gameStateManager: IGameStateManager,
                       gameValues: GameValues = GameValues()) extends ICommand:
    override def toString: String =
        "The list command with optional parameter (units/buildings/technologies) will list all available Game Objects " +
          "according to input, if omitted everything will be listed.\nEnter list help to see this message"

    override def execute(): IGameStateManager =
        string match
            case "technology" | "technologies" | "tech" => gameStateManager.message(listTechnologies)
            case "units" | "unit" => gameStateManager.message(listUnits)
            case "buildings" | "building" => gameStateManager.message(listBuildings)
            case "" => gameStateManager.message(listAll)
            case "help" => gameStateManager.message(this.toString)
            case _ => gameStateManager.invalid(s"list: '$string'")
    def listBuildings: String =
        s"==== Buildings ====\n${prependString(gameValues.listOfBuildings.map(addStyle)).mkString("\n")}\n"
    def listUnits: String =
        s"==== Units ====\n${prependString(gameValues.listOfUnits.map(addStyle)).mkString("\n")}\n"
    def listTechnologies: String =
        s"==== Technologies ====\n${prependString(gameValues.listOfTechnologies.map(addStyle)).mkString("\n")}\n"
    def listAll: String = listBuildings + listTechnologies + listUnits
    private def prependString(l: List[String]) = l.map(_.prependedAll(" - "))
    private def addStyle(gameObj: IGameObject): String =
        gameObj match
            case _:IUnit => styleUnitListEntry(gameObj.name)
            case _:IBuilding => styleBuildingListEntry(gameObj.name)
            case _:ITechnology => styleTechnologyListEntry(gameObj.name)
    private def styleBuildingListEntry(str: String): String =
        val building = gameValues.listOfBuildings.find(_.name == str).head
        handleColoring(building)
    private def styleUnitListEntry(str: String): String =
        val unit = gameValues.listOfUnits.find(_.name == str).head
        returnOptionRemainingResources(unit.cost) match
            case Some(_) => unit.toString + s" (${gameStateManager.playerValues.resourceHolder.holds(unit.cost).get})"
            case None => handleStringColor(AnsiColor.RED,
                unit.toString + " " + gameStateManager.playerValues.resourceHolder.lacking(unit.cost))
    private def styleTechnologyListEntry(str: String): String =
        val tech = gameValues.listOfTechnologies.find(_.name == str).head
        if checkIfTechAlreadyResearched(tech.name)
        then handleStringColor(AnsiColor.CYAN, tech.toString + " (already researched)") else handleColoring(tech)
    private def handleColoring(obj: IGameObject): String =
        returnOptionRemainingResources(obj.cost) match
            case Some(_) => obj.toString
            case None => handleStringColor(AnsiColor.RED,
                    obj.toString + " " + gameStateManager.playerValues.resourceHolder.lacking(obj.cost))
    private def returnOptionRemainingResources(resourceHolder: ResourceHolder): Option[ResourceHolder] =
        gameStateManager.playerValues.resourceHolder.decrease(resourceHolder)
    private def checkIfTechAlreadyResearched(name: String): Boolean =
        gameStateManager.playerValues.listOfTechnologiesCurrentlyResearched.exists(_.name == name) ||
          gameStateManager.playerValues.listOfTechnologies.exists(_.name == name)
    private def handleStringColor(color: String, target: String): String = color + target + AnsiColor.RESET