package view.swingGui

import controller.IController
import controller.command.commands.{EndRoundCommand, UserAcceptCommand, UserDeclineCommand}
import model.core.board.sector.sectorutils.Affiliation
import model.core.utilities.{Capacity, ResourceHolder, Round}
import model.game.gamestate.GameStateManager
import model.core.gameobjects.resources.IResource
import view.swingGui
import view.swingGui.guiUtils.GuiUtils

import java.awt.{Color, Image}
import javax.swing.{BorderFactory, ImageIcon}
import javax.swing.border._
import scala.swing.*
import scala.swing.event.ButtonClicked

class OverviewPanel(controller: IController) extends FlowPanel:

  private val nRButton: Button = nextRoundButton()
  private val gameValueInfoPanel: BoxPanel = createGameValueInfoPanel()


  border = playerIndicator()

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

  private def playerIndicator(): Border =
    controller.getState.getGSM.affiliation match
      case Affiliation.PLAYER => BorderFactory.createLineBorder(Color.BLUE, 2)
      case Affiliation.ENEMY => BorderFactory.createLineBorder(Color.RED, 2)
      case _ => BorderFactory.createLineBorder(Color.WHITE, 2)

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
    border = playerIndicator()
    roundLabel.text = s"Round:  ${controller.getState.getCurrentRound}"
    capacityLabel.text =  s"Capacity: ${controller.getState.getCapacity}"
    gameValueInfoPanel.contents.clear()
    GuiUtils().fillPlayerValueInfoPanel(gameValueInfoPanel, controller)
    revalidate()
    repaint()

  private def onClickHandler(): Unit = controller.processInput(EndRoundCommand(controller.getState.getGSM))
