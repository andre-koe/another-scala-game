package controller.command.commands

import controller.command.ICommand
import model.game.{Capacity, GameValues}
import model.game.gamestate.GameStateManager
import model.game.purchasable.IGameObject
import model.game.purchasable.building.IBuilding
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit
import model.game.resources.ResourceHolder

import scala.io
import scala.io.AnsiColor

case class ListCommand(string: String,
                       gameStateManager: GameStateManager,
                       gameValues: GameValues = GameValues()) extends ICommand:
    
    override def toString: String =
        "The list command with optional parameter (units/buildings/technologies) will list all available Game Objects " +
          "according to input, if omitted everything will be listed.\nEnter list help to see this message"
    
    override def execute(): GameStateManager =
        string match
            case "technology" | "technologies" | "tech" => gameStateManager.message(listTechnologies)
            case "units" | "unit" => gameStateManager.message(listUnits)
            case "buildings" | "building" => gameStateManager.message(listBuildings)
            case "" => gameStateManager.message(listAll)
            case "help" => gameStateManager.message(this.toString)
            case _ => gameStateManager.invalid(s"list: '$string'")
    
    private def listBuildings: String =
        s"==== Buildings ====\n${prependString(gameValues.listOfBuildings.map(addStyle)).mkString("\n")}\n"
    
    private def listUnits: String =
        s"==== Units ====\n${prependString(gameValues.listOfUnits.map(addStyle)).mkString("\n")}\n"
    
    private def listTechnologies: String =
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
        (returnOptionRemainingResources(unit.cost), returnOptionRemainingCapacity(unit.capacity)) match
            case (Some(_), Some(_)) =>
                unit.toString + s" " +
                  s"(${Math.min(gameStateManager.playerValues.resourceHolder.holds(unit.cost).get,
                      gameStateManager.playerValues.capacity.holds(unit.capacity).get)})"
            case (Some(_), None) => handleStringColor(AnsiColor.RED,
                unit.toString + " Total Lacking: " + gameStateManager.playerValues.capacity.lacking(unit.capacity))
            case _ => handleStringColor(AnsiColor.RED,
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
        
    private def returnOptionRemainingCapacity(capacity: Capacity): Option[Capacity] =
        gameStateManager.playerValues.capacity.decrease(capacity)
    
    private def checkIfTechAlreadyResearched(name: String): Boolean =
        gameStateManager.playerValues.listOfTechnologiesCurrentlyResearched.exists(_.name == name) ||
          gameStateManager.playerValues.listOfTechnologies.exists(_.name == name)
    
    private def handleStringColor(color: String, target: String): String = color + target + AnsiColor.RESET