package view.swingGui

import controller.IController
import utils.{Observable, Observer}

import java.awt.{Color, Dimension}
import javax.swing.border.EmptyBorder
import scala.language.postfixOps
import scala.swing.{BoxPanel, GridPanel, ScrollPane}

class SectorGridPanel(controller: IController, detailsView: DetailsView) extends ScrollPane:

  border = EmptyBorder(40, 40, 40, 40)

  val rows: Int = controller.getState.getGameMapSizeX
  val cols: Int = controller.getState.getGameMapSizeY

  val grid = new GridPanel(rows, cols)
  grid.contents ++= gridContentGenerator

  grid.background = Color.black
  contents = grid


  minimumSize = new Dimension(1400, 200)
  maximumSize = new Dimension(1400, 700)

  private def gridContentGenerator: List[SectorView] =
    (for {
      row <- 0 until rows
      col <- 0 until cols
    } yield {
      val sectorView = SectorView(controller.getState.getGameMapInfo.getSector(row, col).get)
      sectorView.addObserver(detailsView)
      sectorView}).toList


  background = Color.black

