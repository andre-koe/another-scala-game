package view.swingGui

import model.core.board.sector.ISector
import model.core.board.sector.sectorutils.Affiliation
import view.swingGui.guiUtils.Subject

import java.awt.Color
import javax.swing.ImageIcon
import scala.swing.{Alignment, Dimension, Label}
import scala.swing.event.MouseClicked

class SectorView(sector: ISector) extends Label with Subject[ISector]:

  private val myImageIcon = sector.affiliation match
    case Affiliation.ENEMY =>
      new ImageIcon("src/main/resources/images/solar-system-orbit-svgrepo-com-Enemy.png")
    case Affiliation.INDEPENDENT =>
      new ImageIcon("src/main/resources/images/solar-system-orbit-svgrepo-com-Independent.png")
    case Affiliation.PLAYER =>
      new ImageIcon("src/main/resources/images/solar-system-orbit-svgrepo-com-Player.png")

  icon = myImageIcon
  foreground = Color.white
  text = s"[${sector.location.toString}]"
  horizontalTextPosition = Alignment.Center

  maximumSize = new Dimension(60, 60)

  listenTo(mouse.clicks)

  reactions += {
    case MouseClicked(_, _, _, _, _) =>
      notifyObservers(sector)
  }
    