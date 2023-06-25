package view.swingGui.guiUtils

import controller.IController
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.board.sector.ISector
import model.core.board.sector.sectorutils.Affiliation
import model.core.gameobjects.purchasable.building.{EnergyGrid, Factory, Hangar, IBuilding, Mine, Shipyard}
import model.core.utilities.{ICapacity, IResourceHolder, IRound, ResourceHolder}
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.core.gameobjects.resources.*
import model.core.mechanics.fleets.components.units.IUnit

import java.awt.{Color, Font, Image}
import java.io.File
import javax.swing.ImageIcon
import scala.swing.*

class GuiUtils {

  def resPanelFromSector(str: String, res: IResourceHolder): BoxPanel =
    val panel = new BoxPanel(Orientation.Vertical)
    panel.background = Color.darkGray
    panel.contents += GuiUtils().colorLabel(str)
    panel.contents += GuiUtils().resourceHolderWithIcons(res)
    panel


  def valueLabel(value: Int): Label =
    val l = new Label()
    l.text = if value > 1 then "+" + value.toString else value.toString
    l.foreground = if value >= 0 then Color.green else Color.red
    l

  def colorLabel(value: String, color: Color = Color.white): Label =
    val l = Label()
    l.text = value
    l.foreground = color
    l

  def resourceHolderWithIcons(res: IResourceHolder): BoxPanel =
    val panel = new BoxPanel(Orientation.Horizontal)
    val has = res.resourcesAsVector
    val gets = ResourceHolder().resourcesAsVector
    panel.contents.addAll((has zip gets).flatMap { case (has, next) => Seq(resourceInfoLabel(has, next)) })
    panel.background = Color.darkGray
    panel

  def fillPlayerValueInfoPanel(panel: BoxPanel, controller: IController): Unit =
    val (has, gets) = getPlayerValuesWithNextRoundValues(controller)
    panel.contents.addAll((has zip gets).flatMap { case (has, next) => Seq(resourceInfoLabel(has, next)) })

  private def getPlayerValuesWithNextRoundValues(controller: IController,
                                                 withPrediction: Boolean = true): (Vector[IResource[_]], Vector[IResource[_]]) =
    val has = controller.getState.getPlayerResources
    val gets = if withPrediction then controller.getState.getPlayerNetIncome else ResourceHolder().resourcesAsVector
    (has, gets)

  def resourceInfoLabel(value: IResource[_], value1: IResource[_]): FlowPanel =
    val f = GuiUtils().colorLabel(s"${value.name}: ", Color.white)
    val wi = GuiUtils().iconLabel(value.value.toString, value, Color.white, 20)
    val l = if (value1.value != 0) GuiUtils().valueLabel(value1.value) else new Label()
    val panel = new FlowPanel()
    panel.minimumSize = new Dimension(100, 100)
    panel.contents ++= Seq(f, wi, l)
    panel.background = Color.darkGray
    panel

  def iconLabel(value: String, res: Any, color: Color = Color.white,
                iconSize: Int = 20, invertOrder: Boolean = false): Label =
    val l = Label()
    l.text = value
    l.foreground = color
    l.background = Color.darkGray
    l.icon = new ImageIcon(new ImageIcon(getIconResourceAsString(res))
      .getImage.getScaledInstance(iconSize, iconSize, Image.SCALE_REPLICATE))
    l.horizontalTextPosition = if invertOrder then Alignment.Left else Alignment.Right
    l

  def getIconResourceAsString(resource: Any): String =
    resource match
      case _: Alloys => "src/main/resources/images/resource-icon/gold-bar-svgrepo-com.png"
      case _: Minerals => "src/main/resources/images/resource-icon/diamond-svgrepo-com.png"
      case _: Energy => "src/main/resources/images/resource-icon/bolt-svgrepo-com.png"
      case _: ResearchPoints => "src/main/resources/images/resource-icon/lab-svgrepo-com.png"
      case _: ICapacity => "src/main/resources/images/resource-icon/space-shuttle-svgrepo-com.png"
      case _: IRound => "src/main/resources/images/resource-icon/hourglass-svgrepo-com.png"
      case x: String => x match
        case "next" => "src/main/resources/images/resource-icon/next-svgrepo-com.png"
        case "tech" => "src/main/resources/images/resource-icon/lab-svgrepo-com.png"
        case _ => ""
      case _ => ""


  def stringToCoord(string: String): ICoordinate =
    val pos = string.split("-")
    Coordinate(pos(0).toInt, pos(1).toInt)

}