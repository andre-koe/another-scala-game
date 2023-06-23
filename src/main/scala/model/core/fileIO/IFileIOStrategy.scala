package model.core.fileIO

import model.game.gamestate.{GameStateManager, IGameStateManager}

import java.io.File
import java.nio.file.{Files, Path}
import java.nio.file.attribute.BasicFileAttributes
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import scala.util.Try

trait IFileIOStrategy:
  
  val dir: File

  val formatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").withZone(ZoneId.systemDefault())

  def getFile(string: String): Option[File] =
    if (dir.exists && dir.isDirectory) {
      dir.listFiles.find(_.getName == string)
    } else None

  def getLatest: Option[File] =
    if (dir.exists && dir.isDirectory) {
      val files = dir.listFiles
      val sortedFiles = files.sortWith {
        case (file1, file2) =>
          val file1Path: Path = file1.toPath
          val file2Path: Path = file2.toPath

          val file1Attrs: BasicFileAttributes = Files.readAttributes(file1Path, classOf[BasicFileAttributes])
          val file2Attrs: BasicFileAttributes = Files.readAttributes(file2Path, classOf[BasicFileAttributes])

          val file1ModificationTime = file1Attrs.lastModifiedTime().toMillis
          val file2ModificationTime = file2Attrs.lastModifiedTime().toMillis

          file1ModificationTime > file2ModificationTime
      }
      sortedFiles.headOption
    } else None

  def save(gsm: IGameStateManager, string: Option[String]): IGameStateManager

  def load(string: Option[String]): Try[IGameStateManager]
