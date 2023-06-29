package model.core.fileIO

import io.circe.*
import io.circe.Encoder.AsObject.importedAsObjectEncoder
import io.circe.generic.auto.*
import io.circe.parser.parse
import io.circe.syntax.*
import model.game.gamestate.{GameStateManager, IGameStateManager}
import utils.CirceImplicits._

import java.io.{File, FileNotFoundException, FileWriter}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, Path}
import java.time.Instant
import java.time.format.DateTimeFormatter
import scala.util.{Failure, Try}

case class JSONStrategy(override val dir: File = new File("./savegames")) extends IFileIOStrategy:

  override def load(string: Option[String]): Try[IGameStateManager] =
    getFileByNameOrLatest(string) match
      case Some(x) => parse(String(Files.readAllBytes(x.toPath))).flatMap(_.as[IGameStateManager]).toTry
      case _ => Failure(new FileNotFoundException("No file found."))

  override def save(gsm: IGameStateManager, string: Option[String]): IGameStateManager =
    val filename = string.getOrElse(formatter.format(Instant.now())) + ".json"
    if !dir.exists() then dir.mkdir()
    val file = new File(dir, filename)
    val fileWriter = new FileWriter(file)
    val jsonString =  gsm.asJson.spaces2
    fileWriter.write(jsonString)
    fileWriter.close()
    gsm

