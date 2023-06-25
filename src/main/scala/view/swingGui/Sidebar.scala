package view.swingGui

import controller.IController
import model.core.board.sector.impl.Sector
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.technology.ITechnology
import view.swingGui.guiUtils.GuiUtils

import java.awt.Color
import scala.swing.event.ButtonClicked
import scala.swing.{BoxPanel, Button, Dimension, Orientation, ScrollPane, Swing}

class Sidebar(controller: IController) extends BoxPanel(Orientation.Vertical):
  private val toggleButton = new Button(">")
  listenTo(toggleButton)

  private val initWidth: Int = toggleButton.preferredSize.width

  preferredSize = new Dimension(initWidth, 500)
  background = Color.darkGray

  private val itemList = controller.getState.getResearchableTech.map(x => TechnologyItemView(x.name, controller))

  private val researchPane: ScrollPane = createScrollPane(itemList.toList)
  researchPane.visible = false

  reactions += {
    case ButtonClicked(`toggleButton`) =>
      if (preferredSize.width == initWidth) {
        preferredSize = new Dimension(300, 500)
        toggleButton.text = "<"
        researchPane.visible = !researchPane.visible
      } else {
        preferredSize = new Dimension(initWidth, 500)
        toggleButton.text = ">"
        researchPane.visible = !researchPane.visible
      }
      background = Color.darkGray
      revalidate()
      repaint()
  }

  contents += toggleButton

  def createScrollPane(itemViews: List[ItemView]): ScrollPane = {
    val panel = new BoxPanel(Orientation.Vertical)
    panel.background = Color.darkGray
    panel.contents += GuiUtils().iconLabel("Research", "tech")
    panel.contents += Swing.VStrut(40)
    itemViews.foreach(itemView =>
      panel.contents += itemView
      panel.contents += Swing.VGlue
    )
    new ScrollPane() {
      contents = panel
      background = Color.darkGray
    }
  }

  researchPane.background = Color.darkGray
  contents += researchPane



