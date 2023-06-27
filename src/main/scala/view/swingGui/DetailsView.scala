package view.swingGui

import controller.command.commands.{BuildCommand, RecruitCommand, SellCommand}
import controller.{Controller, IController}
import model.utils.General
import guiUtils.{GUIObserver, GuiUtils}
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.board.sector.sectorutils.Affiliation
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.{BuildingFactory, IBuilding}
import model.core.gameobjects.purchasable.units.UnitFactory
import model.core.mechanics.fleets.IFleet
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.IResourceHolder
import model.core.utilities.interfaces.IRoundBasedConstructable
import view.swingGui.BuyableItemView

import java.awt.{Color, Dimension}
import javax.swing.border.EmptyBorder
import scala.swing.event.{ButtonClicked, SelectionChanged}
import scala.swing.{BoxPanel, Button, ComboBox, Component, Dialog, Dimension, GridPanel, Label, Orientation, ScrollPane, Swing, TabbedPane, TextField}

class DetailsView(controller: IController) extends ScrollPane with GUIObserver[ISector] {

  private val state = controller.getState
  private val affiliation = state.getGSM.affiliation
  private val defaultBackground = Color.darkGray
  private val defaultForeground = Color.white
  private val innerPanel = new BoxPanel(Orientation.Vertical)

  innerPanel.border = EmptyBorder(10,10,10,10)
  innerPanel.background = Color.darkGray
  innerPanel.contents += GuiUtils().colorLabel("Details: Select a sector to get it's Details")

  this.contents = innerPanel
  this.preferredSize = new Dimension(400, 200)  // Set your desired initial size here

  override def onUpdate(event: ISector): Unit =
    innerPanel.contents.clear()
    event match
      case x: ISector => innerPanel.contents += createSectorView(x)
    revalidate()
    repaint()

  private def createSectorView(t: ISector): BoxPanel =
    val bsButton = new Button("Buy/Sell")
    val panel = BoxPanel(Orientation.Vertical)
    panel.background = defaultBackground
    t match
      case s: IPlayerSector if s.affiliation == affiliation =>
        val recruitable = controller.getState.getRecruitableUnits.map(s => BuyableItemView(s.name, t.asInstanceOf[IPlayerSector], controller))
        val constructable = state.getConstructableBuildings.map(s => BuyableItemView(s.name, t.asInstanceOf[IPlayerSector], controller))

        panel.contents += seqToBoxPanel(Seq(
          createLabelBox(s"Player Sector at Location: [${s.sector.location}]"),
          Swing.VStrut(10),
          bsButton,
          Swing.VStrut(15),
          createResourcePanel("Total Income Generated:", General().generatedIncomeFromSector(s)),
          createResourcePanel("Total Upkeep Next Round:", General().upkeepFromBuildings(s).increase(General().upkeepFromUnitsInSector(s))),
          purchasableItemPane(constructable.toList),
          purchasableItemPane(recruitable.toList),
          Swing.VGlue,
          createLabelBox(s"Buildings In Sector"),
          Swing.VGlue,
          scrollPaneCreator(objectsInSector(s.buildingsInSector), "Buildings"),
          Swing.VGlue,
          scrollPaneCreator(fleetsInSector(s.unitsInSector), "Units in Sector"),
          Swing.VGlue,
          scrollPaneCreator(objectsInSector(s.constQuBuilding, true), "Construction Queue"),
          Swing.VGlue,
          scrollPaneCreator(objectsInSector(s.constQuUnits, true), "Recruitment Queue")))
      case _ => panel.contents += createLabelBox("We don't have any information on this Sector")

    bsButton.listenTo(bsButton)

    bsButton.reactions += {
      case ButtonClicked(_) =>
        val dialog: Dialog = new Dialog {
          title = "Buy/Sell"
          modal = true
          preferredSize = Dimension(300, 200)

          val affiliation: Affiliation = controller.getState.getGSM.affiliation
          val listOfBuildings: List[String] = controller.getState.getGSM.getGameValues.buildings.map(_.name).toList
          val listOfUnits: List[String] = controller.getState.getGSM.getGameValues.units.map(_.name).toList
          val listOfSectors: List[String] = controller.getState.getGSM.gameMap.getPlayerSectors(affiliation).map(_.location.toString).toList

          val buildings = "Buildings"
          val units = "Units"

          val comboBoxSelectBuy = new ComboBox(List(buildings, units))
          val comboBoxSelectSell = new ComboBox(List(buildings, units))

          val sectorsBuy = new ComboBox(listOfSectors)
          val sectorsSell = new ComboBox(listOfSectors)

          val itemsBuy = new ComboBox(List.empty[String])
          val itemsSell = new ComboBox(List.empty[String])

          def updateItemsBuy(): Unit = comboBoxSelectBuy.selection.item match
            case "Units" =>
              itemsBuy.peer.setModel(ComboBox.newConstantModel(listOfUnits))
            case "Buildings" =>
              itemsBuy.peer.setModel(ComboBox.newConstantModel(listOfBuildings))
            case _ =>


          def updateItemsSell(): Unit = comboBoxSelectSell.selection.item match
            case "Units" =>
              itemsSell.peer.setModel(ComboBox.newConstantModel(listOfUnits))
            case "Buildings" =>
              itemsSell.peer.setModel(ComboBox.newConstantModel(listOfBuildings))
            case _ =>


          comboBoxSelectBuy.selection.reactions += {
            case SelectionChanged(_) => updateItemsBuy()
          }

          comboBoxSelectSell.selection.reactions += {
            case SelectionChanged(_) => updateItemsSell()
          }

          val textField1 = new TextField("Quantity")
          val textField2 = new TextField("Quantity")

          val buy: BoxPanel = new BoxPanel(Orientation.Vertical) {
            contents += comboBoxSelectBuy
            contents += itemsBuy
            contents += sectorsBuy
            contents += textField1
            contents += Button("Buy") {
              val coord = GuiUtils().stringToCoord(sectorsBuy.selection.item)
              val sector = controller.getState.getGSM.gameMap.getSectorAtCoordinate(coord).get
              comboBoxSelectBuy.selection.item match
                case "Buildings" =>
                  val building = BuildingFactory(itemsBuy.selection.item, coord).get
                  controller.processInput(BuildCommand(building, sector, controller.getState.getGSM))
                case "Units" =>
                  val unit = UnitFactory(itemsBuy.selection.item).get
                  textField1.text.toIntOption match
                    case Some(q) => controller.processInput(RecruitCommand(unit, q, sector, controller.getState.getGSM))
                    case _ =>
            }
          }

          val sell: BoxPanel = new BoxPanel(Orientation.Vertical) {
            contents += comboBoxSelectSell
            contents += itemsSell
            contents += sectorsSell
            contents += textField2
            contents += Button("Sell") {
              val coord = GuiUtils().stringToCoord(sectorsSell.selection.item)
              val sector = controller.getState.getGSM.gameMap.getSectorAtCoordinate(coord).get
              val item = comboBoxSelectSell.selection.item match
                case "Building" => BuildingFactory(itemsSell.selection.item, coord).get
                case "Unit" => UnitFactory(itemsBuy.selection.item).get
              textField2.text.toIntOption match
                case Some(q) =>controller.processInput(SellCommand(item, q, sector, controller.getState.getGSM))
            }
          }

          val tabs: TabbedPane = new TabbedPane {
            pages += new TabbedPane.Page("Buy", buy)
            pages += new TabbedPane.Page("Sell", sell)
          }

          contents = tabs
          centerOnScreen()
        }
        dialog.open()
    }


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
      background = defaultBackground
    }

  private def seqToBoxPanel(seq: Seq[Component]): BoxPanel =
    val panel = BoxPanel(Orientation.Vertical)
    seq.map {
      panel.contents += _
    }
    panel.background = defaultBackground
    panel

}
