package view.swingGui

import controller.{Controller, IController}
import model.utils.General
import guiUtils.{GUIObserver, GuiUtils}
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.mechanics.fleets.IFleet
import model.core.utilities.IResourceHolder
import model.core.utilities.interfaces.IRoundBasedConstructable
import view.swingGui.BuyableItemView

import java.awt.Color
import scala.swing.{BoxPanel, Component, Dimension, GridPanel, Label, Orientation, ScrollPane, Swing}

class DetailsView(controller: IController) extends ScrollPane with GUIObserver[ISector] {

  private val state = controller.getState
  private val affiliation = state.getGSM.affiliation
  private val defaultBackground = Color.darkGray
  private val defaultForeground = Color.white
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
      case s: IPlayerSector if s.affiliation == affiliation =>
        val recruitable = controller.getState.getRecruitableUnits.map(s => BuyableItemView(s.name, t.asInstanceOf[IPlayerSector], controller))
        val constructable = state.getConstructableBuildings.map(s => BuyableItemView(s.name, t.asInstanceOf[IPlayerSector], controller))

        panel.contents += seqToBoxPanel(Seq(
          createLabelBox(s"Player Sector at Location: [${s.sector.location}]"),
          Swing.VStrut(10),
          createResourcePanel("Total Income Generated:", General().generatedIncomeFromSector(s)),
          createResourcePanel("Total Upkeep Next Round:", General().upkeepFromBuildings(s).increase(General().upkeepFromUnitsInSector(s))),
          purchasableItemPane(constructable.toList),
          purchasableItemPane(recruitable.toList),
          Swing.VGlue,
          createLabelBox(s"Buildings In Sector"),
          Swing.VGlue,
          scrollPaneCreator(objectsInSector(s.buildingsInSector), "Buildings"),
          Swing.VGlue,
          scrollPaneCreator(fleetsInSector(s.unitsInSector), "B"),
          Swing.VGlue,
          scrollPaneCreator(objectsInSector(s.constQuBuilding, true), "Construction Queue"),
          Swing.VGlue,
          scrollPaneCreator(objectsInSector(s.constQuUnits, true), "Recruitment Queue")))
      case _ => panel.contents += createLabelBox("We don't have any information on this Sector")
    panel

  private def createResourcePanel(label: String, resource: IResourceHolder): Component = {
    GuiUtils().resPanelFromSector(label, resource)
  }

  private def createLabelBox(text: String): BoxPanel = {
    val panel = new BoxPanel(Orientation.Vertical) {
      background = Color.darkGray
      contents += GuiUtils().colorLabel(text)
    }
    panel
  }

  private def purchasableItemPane(buildings: List[BuyableItemView]): ScrollPane = {
    val panel = new BoxPanel(Orientation.Horizontal)
    panel.background = defaultBackground
    buildings.foreach(x =>
      panel.contents += x
      panel.contents += Swing.HGlue
    )
    new ScrollPane {
      contents = panel
      foreground = defaultForeground
      background = defaultBackground
    }
  }

  private def fleetsInSector(obj: Vector[IFleet]): BoxPanel =
    new BoxPanel(Orientation.Vertical) {
      background = defaultBackground
      obj.foreach(x =>
        contents += seqToBoxPanel(Seq(
          GuiUtils().colorLabel(x.name),
          Swing.HGlue,
          GuiUtils().colorLabel(
            if x.moveVector.isMoving then s"In transit to dest. ${x.moveVector.target}"
            else s"stationed in ${x.location}"
          ),
          Swing.HGlue,
          GuiUtils().colorLabel(x.description),
          Swing.HGlue
        ))
      )
    }

  private def objectsInSector(obj: Vector[IRoundBasedConstructable & IGameObject],
                              withRounds: Boolean = false): BoxPanel = {
    new BoxPanel(Orientation.Vertical) {
      background = defaultBackground
      obj.foreach(x =>
        contents += seqToBoxPanel(Seq(GuiUtils().colorLabel(x.name),
          Swing.HGlue,
          GuiUtils().colorLabel(x.description),
          Swing.HGlue))
        if withRounds then contents += GuiUtils().colorLabel(x.roundsToComplete.value.toString)
      )
    }
  }

  private def scrollPaneCreator(panel: BoxPanel, str: String): ScrollPane =
    new ScrollPane {
      contents = new BoxPanel(Orientation.Vertical) {
        background = defaultBackground
        contents += GuiUtils().colorLabel(str)
        contents += panel
      }
      foreground = defaultForeground
      background = defaultBackground
    }

  private def seqToBoxPanel(seq: Seq[Component]): BoxPanel =
    val panel = BoxPanel(Orientation.Vertical)
    seq.map {
      panel.contents += _
    }
    panel

}
