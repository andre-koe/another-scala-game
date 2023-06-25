package controller.validator

import controller.command.ICommand
import controller.command.commands.{InvalidCommand, MessageCommand, MoveCommand}
import controller.newInterpreter.KeywordType.*
import controller.newInterpreter.{InterpretedCommand, InterpretedInput, KeywordType}
import controller.validator.validatorutils.ValidatorUtils
import model.core.board.boardutils.ICoordinate
import model.game.gamestate.IGameStateManager
import model.game.gamestate.enums.messages.MessageType
import model.game.gamestate.enums.messages.MessageType.{INVALID_INPUT, MALFORMED_INPUT}
import model.utils.GameObjectUtils

case class MoveValidator(orig: String, gsm: IGameStateManager) extends IValidator:


  override def validate(input: Vector[InterpretedInput]): Either[IValidator, Option[ICommand]] =
    val unidentified = ValidatorUtils().findUnidentified(input)
    val coordinate = ValidatorUtils().findCoordinate(input)

    val t = ValidatorUtils().findSubcommands(input)
    val l = ValidatorUtils().findKeyWords(input)

    val returns = unidentified match
      case Some(x) if x contains "help" =>
        MessageCommand("Enter move <fleet name> <location> to send your fleet to another sector",
          messageType = MessageType.HELP, gsm)
      case Some(x) if x.length == 1 =>
          coordinate match
            case Some(y) => validate(x.head, y)
            case _ => InvalidCommand(orig + " no coordinate specified", gsm)
      case _ => InvalidCommand(orig + " no fleet name specified", gsm)

    Right(Some(returns))

  private def validate(fName: String, coord: ICoordinate): ICommand =
    if (fleetExists(fName) && coordIsValid(coord)) then MoveCommand(fName, coord, gsm)
    else InvalidCommand(s"$fName is not a valid fleet", gsm)

  private def fleetExists(str: String): Boolean = 
    gsm.gameMap.data.flatMap(_.flatMap(_.unitsInSector)).exists(_.name.toLowerCase == str)

  private def coordIsValid(coord: ICoordinate): Boolean = gsm.gameMap.getSectorAtCoordinate(coord).isDefined

