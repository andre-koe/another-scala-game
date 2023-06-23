package model.core.fileIO
import model.game.gamestate.IGameStateManager
import utils.XMLConverter

import java.io.{File, FileNotFoundException}
import java.nio.file.Files
import java.time.Instant
import scala.util.{Failure, Try}
import scala.xml.XML

case class XMLStrategy(override val dir: File = new File("./savegames")) extends IFileIOStrategy:

  override def load(string: Option[String]): Try[IGameStateManager] =
    val file: Option[File] = string match
      case Some(x) => getFile(x)
      case _ => getLatest

    file match
      case Some(x) => Try(XMLConverter.xmlToGameStateManager(XML.loadFile(x)))
      case _ => Failure(new FileNotFoundException("No file found."))

  override def save(gsm: IGameStateManager, string: Option[String]): IGameStateManager =
    val filename = string.getOrElse(formatter.format(Instant.now()))
    if !dir.exists() then dir.mkdir()
    val xmlData = gsm.toXML
    XML.save(dir.toPath.toString + "/" + filename + ".xml", xmlData, "UTF-8", xmlDecl = true)
    gsm



