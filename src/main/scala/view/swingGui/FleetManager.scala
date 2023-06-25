package view.swingGui

import controller.IController
import controller.command.commands.MoveCommand
import model.core.mechanics.fleets.IFleet
import view.swingGui.guiUtils.GuiUtils

import java.awt.{Color, Dimension}
import javax.swing.border.EmptyBorder
import scala.language.postfixOps
import scala.swing.{BoxPanel, Dimension, *}
import scala.swing.event.ButtonClicked

class FleetManager(controller: IController) extends BoxPanel(Orientation.Vertical) {

  private val defaultBackground: Color = Color.darkGray
  private val defaultForeground: Color = Color.white
  private val fleets: Vector[IFleet] =
    controller.getState.getGameMapInfo.getFleetsInSectors(controller.getState.getGSM.affiliation)
  private val fleetPanels = for (fleet <- fleets) yield createFleetPanel(fleet)

  border = EmptyBorder(0, 10, 5, 10)

  contents += GuiUtils().colorLabel("FleetManager")
  contents += Swing.VStrut(40)
  contents ++= fleetPanels

  preferredSize = Dimension(300, 500)
  background = defaultBackground


  private def createFleetPanel(fleet: IFleet): BoxPanel = {
    val moveVector = fleet.moveVector
    val fPanel =  BoxPanel(Orientation.Vertical)

    fPanel.border = EmptyBorder(40, 40, 40, 40)
    val moveInfo = if moveVector.isMoving then
      textBox("En route to", moveVector.target.toString)
    else
      textBox("Stationed in", moveVector.start.toString)

    fPanel.contents += textBox("Fleet name", fleet.name)
    fPanel.contents += textBox("Firepower", fleet.firepower.toString)
    fPanel.contents += textBox("Upkeep", fleet.upkeep.toString)
    fPanel.contents += textBox("Location", fleet.moveVector.start.toString)
    fPanel.contents += textBox("Fleet size", fleet.fleetComponents.size.toString)
    fPanel.contents += moveInfo

    fPanel.background = defaultBackground

    val button = createFleetInteractionButton
    button.listenTo(button)
    button.reactions += {
      case ButtonClicked(_) =>
        val comboBox = new ComboBox(controller.getState.getGameMapInfo.getData.map(_.location.toString).toList)
        val result = Dialog.showInput(
          message = "Choose a sector",
          title = "Move Fleet To",
          messageType = Dialog.Message.Plain,
          icon = null,
          entries = controller.getState.getGameMapInfo.getData.map(_.location.toString).toList,
          initial = comboBox.peer.getItemAt(0)
        )
        result match {
          case Some(choice) =>
            val coord = GuiUtils().stringToCoord(choice)
            controller.processInput(MoveCommand(fleet.name, coord, controller.getState.getGSM))
          case None =>
        }
    }
    val buttonPanel = new BoxPanel(Orientation.Horizontal) {
      contents += button
    }
    fPanel.contents += buttonPanel
    fPanel
  }

  private def textBox(lText: String, rText: String): BoxPanel = {
    val tPanel = BoxPanel(Orientation.Horizontal)
    tPanel.contents += GuiUtils().colorLabel(lText + ":")
    tPanel.contents += Swing.HGlue
    tPanel.contents += GuiUtils().colorLabel(rText)

    tPanel.background = defaultBackground
    tPanel
  }

  private def createFleetInteractionButton: Button = new Button("Set Target")
}
