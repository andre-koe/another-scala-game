package view.swingGui

import controller.IController
import controller.command.commands.{EndRoundCommand, UserAcceptCommand, UserDeclineCommand}
import model.core.utilities.{Capacity, ResourceHolder, Round}
import model.game.gamestate.GameStateManager
import model.core.gameobjects.resources.IResource
import view.swingGui
import view.swingGui.guiUtils.GuiUtils

import java.awt.{Color, Image}
import javax.swing.ImageIcon
import scala.swing.*
import scala.swing.event.ButtonClicked

class OverviewPanel(controller: IController) extends FlowPanel:

  private val nRButton: Button = nextRoundButton()
  private val gameValueInfoPanel: BoxPanel = createGameValueInfoPanel()

  private val roundLabel: Label =
    GuiUtils().colorLabel(s"Round: " +
      s"${controller.getState.getCurrentRound}")
  private val capacityLabel: Label =
    GuiUtils().iconLabel(s"Capacity: " +
      s"${controller.getState.getCapacity}",
      Capacity(),
      invertOrder = true)

  contents += roundLabel
  contents += Swing.HGlue
  contents += gameValueInfoPanel
  contents += Swing.HGlue
  contents += capacityLabel
  contents += Swing.HGlue
  contents += nRButton

  preferredSize = new Dimension(900, 50)
  background = Color.darkGray

  private def createGameValueInfoPanel(): BoxPanel =
    val panel = new BoxPanel(Orientation.Horizontal)
    panel.minimumSize = new Dimension(400, 100)
    GuiUtils().fillPlayerValueInfoPanel(panel, controller)
    panel

  private def nextRoundButton(): Button =
    val button = new Button() {
      icon =
        new ImageIcon(
          new ImageIcon(GuiUtils().getIconResourceAsString("next"))
            .getImage.getScaledInstance(20,20, Image.SCALE_SMOOTH))
    }
    button

  listenTo(nRButton)
  reactions += { case ButtonClicked(`nRButton`) => onClickHandler() }

  def update(): Unit =
    roundLabel.text = s"Round:  ${controller.getState.getCurrentRound}"
    capacityLabel.text =  s"Capacity: ${controller.getState.getCapacity}"
    gameValueInfoPanel.contents.clear()
    GuiUtils().fillPlayerValueInfoPanel(gameValueInfoPanel, controller)
    revalidate()
    repaint()

  private def onClickHandler(): Unit = controller.processInput(EndRoundCommand(controller.getState.getGSM))
