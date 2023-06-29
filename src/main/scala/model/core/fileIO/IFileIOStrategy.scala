package model.core.fileIO

import model.game.gamestate.{GameStateManager, IGameStateManager}

import java.io.File
import java.nio.file.{Files, Path}
import java.nio.file.attribute.BasicFileAttributes
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import scala.util.Try

/** Represents an interface for a file input/output strategy. */
trait IFileIOStrategy:

  /** The directory in which the files are stored. */
  val dir: File

  /** The date and time format for the files. */
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").withZone(ZoneId.systemDefault())

  /** Check if directory exist. */
  private val dirExist: Boolean = dir.exists && dir.isDirectory


  /** Saves the game state to a file.
   *
   * @param gsm    The game state to save.
   * @param string An option containing the name of the file to save to or None to use the default file name.
   * @return The `IGameStateManager` instance after saving.
   */
  def save(gsm: IGameStateManager, string: Option[String]): IGameStateManager


  /** Loads the game state from a file.
   *
   * @param string An option containing the name of the file to load from or None to load from the default file.
   * @return A `Try` of `IGameStateManager` that represents the loaded game state.
   */
  def load(string: Option[String]): Try[IGameStateManager]


  /** Returns a specific file or the latest file based on the provided parameter.
   *
   *  @param string An option containing the name of the file to get or None to get the latest file.
   *  @return An option containing the file if it exists or None.
   */
  def getFileByNameOrLatest(string: Option[String]): Option[File] = string match
    case Some(x) => getFile(x)
    case _ => getLatest


  /** Returns a specific file based on the provided name.
   *
   *  @param string The name of the file to get.
   *  @return An option containing the file if it exists or None.
   */
  private def getFile(string: String): Option[File] =
    if dirExist then dir.listFiles.find(_.getName == string) else None


  /** Returns the latest file.
   *
   *  @return An option containing the latest file if it exists or None.
   */
  private def getLatest: Option[File] =
    if dirExist then
      val sortedFiles = sortByModificationTime(dir.listFiles)
      sortedFiles.headOption
    else None


  /** Sorts a list of files by their modification time.
   *
   *  @param files An array of files to sort.
   *  @return An array of sorted files.
   */
  private def sortByModificationTime(files: Array[File]): Array[File] =
    files.sortWith {
      case (file1, file2) =>
        val file1Path: Path = file1.toPath
        val file2Path: Path = file2.toPath

        val file1Attrs: BasicFileAttributes = Files.readAttributes(file1Path, classOf[BasicFileAttributes])
        val file2Attrs: BasicFileAttributes = Files.readAttributes(file2Path, classOf[BasicFileAttributes])

        val file1ModificationTime = file1Attrs.lastModifiedTime().toMillis
        val file2ModificationTime = file2Attrs.lastModifiedTime().toMillis

        file1ModificationTime > file2ModificationTime
    }
