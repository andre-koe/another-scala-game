package controller.command.commands

import controller.command.ICommand
import model.game.{Capacity, GameValues}
import model.game.gamestate.GameStateManager
import model.game.gamestate.enums.ListParams
import model.game.gamestate.enums.ListParams.{TECHNOLOGY, UNITS, BUILDING, ALL}
import model.game.purchasable.IGameObject
import model.game.purchasable.building.IBuilding
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit
import model.game.resources.ResourceHolder

import scala.io
import scala.io.AnsiColor

case class ListCommand(param: ListParams, gsm: GameStateManager) extends ICommand:

    override def execute(): GameStateManager =
        val message = param match
            case TECHNOLOGY => listSection("technologies", gsm.gameValues.listOfTechnologies)
            case UNITS => listSection("units", gsm.gameValues.listOfUnits)
            case BUILDING => listSection("buildings", gsm.gameValues.listOfBuildings)
            case ALL => listAll
        gsm.message(message)

    private def listSection[T<:IGameObject](name: String, list: List[T]): String =
        s"==== ${name.toUpperCase} ====\n${prependString(list.map(addStyle)).mkString("\n")}\n"

    def listAll: String =
        listSection("buildings", gsm.gameValues.listOfBuildings) +
        listSection("technologies", gsm.gameValues.listOfTechnologies) +
        listSection("units", gsm.gameValues.listOfUnits)

    private def prependString(l: List[String]) = l.map(_.prependedAll(" - "))

    private def addStyle(gameObj: IGameObject): String =
        gameObj match
            case _:IUnit => styleUnitListEntry(gameObj.name)
            case _:IBuilding => styleBuildingListEntry(gameObj.name)
            case _:ITechnology => styleTechnologyListEntry(gameObj.name)

    private def styleBuildingListEntry(str: String): String =
        val building = gsm.gameValues.listOfBuildings.find(_.name == str).head
        handleColoring(building)

    private def styleUnitListEntry(str: String): String =
        val unit = gsm.gameValues.listOfUnits.find(_.name == str).head
        (returnOptionRemainingResources(unit.cost), returnOptionRemainingCapacity(unit.capacity)) match
            case (Some(_), Some(_)) =>
                unit.toString + s" " +
                  s"(${Math.min(gsm.playerValues.resourceHolder.holds(unit.cost).get,
                      gsm.playerValues.capacity.holds(unit.capacity).get)})"
            case (Some(_), None) => handleStringColor(AnsiColor.RED,
                unit.toString + " Total Lacking: " + gsm.playerValues.capacity.lacking(unit.capacity))
            case _ => handleStringColor(AnsiColor.RED,
                unit.toString + " " + gsm.playerValues.resourceHolder.lacking(unit.cost))

    private def styleTechnologyListEntry(str: String): String =
        val tech = gsm.gameValues.listOfTechnologies.find(_.name == str).head
        if checkIfTechAlreadyResearched(tech.name)
        then handleStringColor(AnsiColor.CYAN, tech.toString + " (already researched)") else handleColoring(tech)

    private def handleColoring(obj: IGameObject): String =
        returnOptionRemainingResources(obj.cost) match
            case Some(_) => obj.toString
            case None => handleStringColor(AnsiColor.RED,
                    obj.toString + " " + gsm.playerValues.resourceHolder.lacking(obj.cost))
    private def returnOptionRemainingResources(resourceHolder: ResourceHolder): Option[ResourceHolder] =
        gsm.playerValues.resourceHolder.decrease(resourceHolder)

    private def returnOptionRemainingCapacity(capacity: Capacity): Option[Capacity] =
        gsm.playerValues.capacity.decrease(capacity)

    private def checkIfTechAlreadyResearched(name: String): Boolean =
        gsm.playerValues.listOfTechnologiesCurrentlyResearched.exists(_.name == name) ||
          gsm.playerValues.listOfTechnologies.exists(_.name == name)

    private def handleStringColor(color: String, target: String): String = color + target + AnsiColor.RESET