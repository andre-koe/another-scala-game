package view.swingGui

import controller.IController
import model.core.board.boardutils.Coordinate
import model.core.board.sector.impl.IPlayerSector
import utils.{Observable, Observer}

import java.awt.{Color, Dimension}
import javax.swing.border.EmptyBorder
import scala.language.postfixOps
import scala.swing.{BoxPanel, GridPanel, ScrollPane}

class SectorGridPanel(controller: IController, detailsView: DetailsView) extends ScrollPane:

  private val defaultBackground = Color.black
  private val rows = controller.getState.getGameMapSizeX
  private val cols = controller.getState.getGameMapSizeY


  border = EmptyBorder(40, 40, 40, 40)
  background = defaultBackground
  minimumSize = new Dimension(1400, 200)
  maximumSize = new Dimension(1400, 700)

  val grid = new GridPanel(rows, cols)
  grid.background = defaultBackground
  grid.contents ++= gridContentGenerator

  contents = grid

  private def gridContentGenerator: List[SectorView] =
    (for {
      row <- 0 until rows
      col <- 0 until cols
    } yield {
      val sector = controller.getState.getGSM.gameMap.getSectorAtCoordinate(Coordinate(row, col)).get
      val sectorView = sector match
        case x: IPlayerSector => SectorView(x)
        case y => SectorView(y)
      sectorView.addObserver(detailsView)
      sectorView
    }).toList
