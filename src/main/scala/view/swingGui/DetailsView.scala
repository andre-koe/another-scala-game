package view.swingGui

import controller.{Controller, IController}
import model.utils.General
import guiUtils.{GUIObserver, GuiUtils}
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.mechanics.fleets.IFleet
import model.core.utilities.interfaces.IRoundBasedConstructable
import view.swingGui.BuyableItemView

import java.awt.Color
import scala.swing.{BoxPanel, Dimension, GridPanel, Label, Orientation, ScrollPane, Swing}

class DetailsView(controller: IController) extends ScrollPane with GUIObserver[ISector] {

  private val innerPanel = new BoxPanel(Orientation.Vertical)

  innerPanel.background = Color.darkGray
  innerPanel.contents += GuiUtils().colorLabel("Details: Select a sector to get it's Details")

  this.contents = innerPanel
  this.preferredSize = new Dimension(400, 200)  // Set your desired initial size here

  override def onUpdate(event: ISector): Unit =
    innerPanel.contents.clear()
    event match
      case x: ISector =>
        val sector = controller.getState.getGameMapInfo.getSector(x.location.yPos, x.location.xPos).get
        innerPanel.contents += createSectorView(sector)
    revalidate()
    repaint()

  private def createSectorView(t: ISector): BoxPanel =
    val panel = BoxPanel(Orientation.Vertical)
    panel.background = Color.darkGray

    t match
      case s: IPlayerSector =>
        panel.contents += GuiUtils().colorLabel(s"Player Sector at Location: [${s.sector.location}]")
        panel.contents += Swing.VStrut(10)
        panel.contents +=
          GuiUtils().resPanelFromSector("Total Income Generated:", General().generatedIncomeFromSector(s))
        panel.contents +=
          GuiUtils().resPanelFromSector("Total Upkeep Next Round:",
            General().upkeepFromBuildings(s).increase(General().upkeepFromUnitsInSector(s)))
        val constructable = controller.getState.getConstructableBuildings.map(s => BuyableItemView(s.name, t.asInstanceOf[IPlayerSector], controller))
        panel.contents += buyableItemPane(constructable.toList)
        val recruitable = controller.getState.getRecruitableUnits.map(s => BuyableItemView(s.name, t.asInstanceOf[IPlayerSector], controller))
        panel.contents += buyableItemPane(recruitable.toList)
        panel.contents += Swing.VGlue
        panel.contents += GuiUtils().colorLabel(s"Buildings In Sector")
        panel.contents += Swing.VGlue
        panel.contents += scrollPaneCreator(objectsInSector(s.buildingsInSector), "Buildings")
        panel.contents += Swing.VGlue
        panel.contents += scrollPaneCreator(fleetsInSector(s.unitsInSector), "B")
        panel.contents += Swing.VGlue
        panel.contents += scrollPaneCreator(objectsInSector(s.constQuBuilding, true), "Construction Queue")
        panel.contents += Swing.VGlue
        panel.contents += scrollPaneCreator(objectsInSector(s.constQuUnits, true), "Recruitment Queue")
      case _ =>
        panel.contents += GuiUtils().colorLabel("We don't have any information on this Sector")
    panel


  private def buyableItemPane(buildings: List[BuyableItemView]): ScrollPane = {
    val panel = new BoxPanel(Orientation.Horizontal)
    panel.background = Color.darkGray
    buildings.foreach(x =>
      panel.contents += x
      panel.contents += Swing.HGlue
    )
    new ScrollPane {
      contents = panel
      foreground = Color.white
      background = Color.darkGray
    }
  }

  private def fleetsInSector(obj: Vector[IFleet]): BoxPanel =
    new BoxPanel(Orientation.Horizontal) {
      background = Color.darkGray
      obj.foreach(x =>
        contents += GuiUtils().colorLabel(x.name)
        contents += Swing.HGlue
        contents += GuiUtils().colorLabel(x.description)
        contents += Swing.HGlue
      )
    }

  private def objectsInSector(obj: Vector[IRoundBasedConstructable & IGameObject],
                              withRounds: Boolean = false): BoxPanel = {
    new BoxPanel(Orientation.Horizontal) {
      background = Color.darkGray
      obj.foreach(x =>
        contents += GuiUtils().colorLabel(x.name)
        contents += Swing.HGlue
        contents += GuiUtils().colorLabel(x.description)
        contents += Swing.HGlue
        if withRounds then contents += GuiUtils().colorLabel(x.roundsToComplete.value.toString)
      )
    }
  }

  private def scrollPaneCreator(panel: BoxPanel, str: String): ScrollPane =
    new ScrollPane {
      contents = new BoxPanel(Orientation.Vertical) {
        background = Color.darkGray
        contents += GuiUtils().colorLabel(str)
        contents += panel
      }
      foreground = Color.white
      background = Color.darkGray
    }

}
