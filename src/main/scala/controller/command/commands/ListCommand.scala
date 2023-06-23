package controller.command.commands

import controller.command.ICommand
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.interfaces.IPurchasable
import model.core.utilities.{Capacity, GameValues, ICapacity, IResourceHolder, ResourceHolder}
import model.game.gamestate.IGameStateManager
import model.game.gamestate.enums.ListParams
import model.game.gamestate.enums.ListParams.{ALL, BUILDING, TECHNOLOGY, UNITS}

import scala.io
import scala.io.AnsiColor

case class ListCommand(param: ListParams, gameStateManager: IGameStateManager, gameValues: GameValues = GameValues()) extends ICommand:

    override def execute(): IGameStateManager =
        val message = param match
            case TECHNOLOGY => listSection("technologies", gameValues.tech)
            case UNITS => listSection("units", gameValues.units)
            case BUILDING => listSection("buildings", gameValues.buildings)
            case ALL => listAll
        gameStateManager.message(message)

    private def listSection[T<:IGameObject](name: String, vec: Vector[T]): String =
        s"==== ${name.toUpperCase} ====\n${prependString(vec.map(addStyle)).mkString("\n")}\n"

    def listAll: String =
        listSection("buildings", gameValues.buildings) +
        listSection("technologies", gameValues.tech) +
        listSection("units", gameValues.units)

    private def prependString(l: Vector[String]) = l.map(_.prependedAll(" - "))

    private def addStyle(gameObj: IGameObject): String =
        gameObj match
            case _:IUnit => styleUnitListEntry(gameObj.name)
            case _:IBuilding => styleBuildingListEntry(gameObj.name)
            case _:ITechnology => styleTechnologyListEntry(gameObj.name)

    private def styleBuildingListEntry(str: String): String =
        val building = gameValues.buildings.find(_.name == str).head
        handleColoring(building)

    private def styleUnitListEntry(str: String): String =
        val unit = gameValues.units.find(_.name == str).head
        (returnOptionRemainingResources(unit.cost), returnOptionRemainingCapacity(unit.capacity)) match
            case (Some(_), Some(_)) =>
                unit.name + s" " +
                  s"(${Math.min(gameStateManager.playerValues.resourceHolder.holds(unit.cost).get,
                      gameStateManager.playerValues.capacity.holds(unit.capacity).get)})"
            case (Some(_), None) => handleStringColor(AnsiColor.RED,
                unit.name + " Total Lacking: " + gameStateManager.playerValues.capacity.lacking(unit.capacity))
            case _ => handleStringColor(AnsiColor.RED,
                unit.name + " " + gameStateManager.playerValues.resourceHolder.lacking(unit.cost))

    private def styleTechnologyListEntry(str: String): String =
        val tech = gameValues.tech.find(_.name == str).head
        if checkIfTechAlreadyResearched(tech.name)
        then handleStringColor(AnsiColor.CYAN, tech.toString + " (already researched)") else handleColoring(tech)

    private def handleColoring(obj: IGameObject & IPurchasable): String =
        returnOptionRemainingResources(obj.cost) match
            case Some(_) => obj.name
            case None => handleStringColor(AnsiColor.RED,
                    obj.name + " " + gameStateManager.playerValues.resourceHolder.lacking(obj.cost))
    private def returnOptionRemainingResources(resourceHolder: IResourceHolder): Option[IResourceHolder] =
        gameStateManager.playerValues.resourceHolder.decrease(resourceHolder)

    private def returnOptionRemainingCapacity(capacity: ICapacity): Option[ICapacity] =
        gameStateManager.playerValues.capacity.decrease(capacity)

    private def checkIfTechAlreadyResearched(name: String): Boolean =
        gameStateManager.playerValues.listOfTechnologiesCurrentlyResearched.exists(_.name == name) ||
          gameStateManager.playerValues.listOfTechnologies.exists(_.name == name)

    private def handleStringColor(color: String, target: String): String = color + target + AnsiColor.RESET