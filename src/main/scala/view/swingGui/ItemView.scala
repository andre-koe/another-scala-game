package view.swingGui

import controller.IController
import controller.command.commands.{BuildCommand, RecruitCommand, ResearchCommand, SellCommand}
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.interfaces.IPurchasable
import model.utils.GameObjectUtils
import utils.Observable
import view.swingGui.guiUtils.GuiUtils

import java.awt.Color
import scala.swing.{Button, GridPanel, Label}
import scala.swing.*
import scala.swing.event.ButtonClicked

abstract class ItemView(name: String, controller: IController) extends BoxPanel(Orientation.Vertical):

  val titleLabel = new Label(name)
  foreground = Color.white
  background = Color.darkGray

  contents += titleLabel


class BuyableItemView(name: String, sector: IPlayerSector, controller: IController) extends ItemView(name, controller):

  val go: IPurchasable = GameObjectUtils().findInLists(name).get
  var count: Int = go match
    case _: IBuilding => sector.buildingsInSector.map(_.name).count(_ == name)
    case _ => sector.unitsInSector.flatMap(_.units).map(_.name).count(_ == name)

  private val countLabel: Label = GuiUtils().colorLabel(s"In Possession: $count")
  private val infoLabel: Label = GuiUtils().colorLabel("")

  private val buy = new Button("+")
  buy.background = Color.green
  buy.foreground = Color.white

  private val sell = new Button("-")
  sell.background = Color.red
  sell.foreground = Color.white

  contents += countLabel
  contents += infoLabel
  contents += new BoxPanel(Orientation.Horizontal) {
    contents += sell
    Swing.HGlue
    contents += buy
  }

  listenTo(sell, buy)

  reactions += {
    case ButtonClicked(`sell`) =>
      if (count > 0
        && (sector.buildingsInSector.map(_.name).contains(go)
        || sector.unitsInSector.flatMap(_.units).map(_.name).contains(go))) {
        count -= 1
        countLabel.text = s"In Possession: $count"
        controller.processInput(SellCommand(go.asInstanceOf[IGameObject], 1, sector, controller.getState.getGSM))
      }

    case ButtonClicked(`buy`) =>
      if (controller.getState.getGSM.playerValues.resourceHolder.decrease(go.cost).isDefined) {
        count += 1
        go match
          case x: IBuilding => controller.processInput(BuildCommand(x, sector, controller.getState.getGSM))
          case x: IUnit => controller.processInput(RecruitCommand(x, 1, sector, controller.getState.getGSM))
          case _ =>
      } else {
        infoLabel.foreground = Color.white
        infoLabel.text = "Insufficient Funds: Total Lacking:" +
          controller.getState.getGSM.playerValues.resourceHolder.lacking(go.cost).toString
      }
      countLabel.text = s"In Possession: $count"
  }


class TechnologyItemView(name: String, controller: IController) extends ItemView(name, controller):

  var alreadyResearched: Boolean = false
  private val buy = new Button("+")
  buy.background = Color.green
  buy.foreground = Color.white
  
  contents += buy

  listenTo(buy)

  reactions += {
    case ButtonClicked(`buy`) =>
      if (!alreadyResearched) {
        alreadyResearched = true
        buy.enabled = false
        buy.text = "Already Researched"
        buy.background = Color.blue
        GameObjectUtils().findInLists(name).get match
          case x: ITechnology => controller.processInput(ResearchCommand(x, controller.getState.getGSM))
          case _ =>
      }
  }
