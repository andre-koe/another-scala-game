package view.swingGui

import com.google.inject.Guice
import controller.IController
import controller.command.commands.{EndGameCommand, EndRoundCommand, LoadCommand, SaveCommand, UserAcceptCommand, UserDeclineCommand}
import model.game.gamestate.gamestates.WaitForUserConfirmation
import utils.Observer
import utils.DefaultValueProvider.given_IFileIOStrategy
import view.swingGui

import java.awt.Color
import scala.swing.*
import scala.swing.event.{ActionEvent, ButtonClicked, Key}
import utils.DefaultValueProvider.given_IController


class GuiS(using controller: IController) extends swing.Frame with Observer {
  
  controller.registerObserver(this)

  title = "My Game"
  preferredSize = new Dimension(900, 900)

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Quit") {
        controller.processInput(EndGameCommand(controller.getState.getGSM))
        System.exit(0)
      })
      contents += new MenuItem(Action("Save") {
        controller.processInput(SaveCommand(None, controller.getState.getGSM))
      })
      contents += new MenuItem(Action("Load Latest") {
        controller.processInput(LoadCommand(None, controller.getState.getGSM))
      })
      contents += new MenuItem(Action("Load File") {
        val fileChooser = new FileChooser
        fileChooser.showOpenDialog(null) match
          case FileChooser.Result.Approve =>
            val selectedFile = fileChooser.selectedFile
            controller.processInput(LoadCommand(Some(selectedFile.getName), controller.getState.getGSM))
          case _ =>
      })
      contents += new Button("Save File") {
        reactions += {
          case ButtonClicked(_) =>
            new Dialog(null) {
              title = "Save as"
              modal = true
              contents = new FlowPanel {
                contents += new Label("Filename")
                val textField: TextField = new TextField() {
                  preferredSize = new Dimension(400, 20)
                }
                contents += textField
                contents += Button("OK") {
                  close()
                  controller
                    .processInput(
                      SaveCommand(
                        Some(textField.text.trim.replace(" ", "_")),
                        controller.getState.getGSM))
                }
              }
              centerOnScreen()
              open()
            }
        }
      }
    }
  }

  private val detailsView = new DetailsView(controller)
  private val overviewPanel = new OverviewPanel(controller)
  private val sectorGridPanel = new SectorGridPanel(controller, detailsView)
  private val sideBarResearch = new Sidebar(controller)


  private val mainPanel: BorderPanel = new BorderPanel {
    layout(overviewPanel) = BorderPanel.Position.North
    layout(sectorGridPanel) = BorderPanel.Position.Center
    layout(sideBarResearch) = BorderPanel.Position.West
    layout(detailsView) = BorderPanel.Position.South
  }
  contents = mainPanel

  pack()
  visible = true

  override def update(): Unit = {
    controller.getState.getState match
      case _: WaitForUserConfirmation => new Dialog {
        title = "End Turn Dialog"
        peer.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE)
        modal = true
        preferredSize = new Dimension(400, 200)
        val boxPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
          contents += Swing.VGlue
          contents += new Label() {
            text = "Are you sure you want to end your turn?"
          }
          contents += Swing.VGlue

          val buttonPanel: BoxPanel = new BoxPanel(Orientation.Horizontal) {
            contents += Swing.HGlue
            contents += Button("No") {
              controller.processInput(UserDeclineCommand(controller.getState.getGSM))
              dispose()
            }
            contents += Swing.HGlue
            contents += Button("Yes") {
              controller.processInput(UserAcceptCommand(controller.getState.getGSM))
              dispose()
            }
            contents += Swing.HGlue
          }
          contents += buttonPanel
          contents += Swing.VGlue
        }
        contents = boxPanel
        centerOnScreen()
        open()
      }
      case _: EndRoundCommand =>
        overviewPanel.update()
        mainPanel.layout(new SectorGridPanel(controller, new DetailsView(controller))) = BorderPanel.Position.Center
        mainPanel.layout(new Sidebar(controller)) = BorderPanel.Position.West
      case _  =>
        overviewPanel.update()
        mainPanel.layout(new SectorGridPanel(controller, detailsView)) = BorderPanel.Position.Center
        mainPanel.layout(new Sidebar(controller)) = BorderPanel.Position.West
  }
}



