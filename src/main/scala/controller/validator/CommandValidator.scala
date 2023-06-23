package controller.validator
import controller.command.ICommand
import controller.command.commands.{EmptyCommand, EndGameCommand, EndRoundCommand, InvalidCommand, RedoCommand, UndoCommand, UserAcceptCommand, UserDeclineCommand}
import controller.newInterpreter.{CommandType, InterpretedCommand, InterpretedInput, InterpretedUnidentified, TokenizedInput}
import controller.newInterpreter.CommandType.*
import model.game.gamestate.IGameStateManager

case class CommandValidator(orig: String, gsm: IGameStateManager) extends IValidator:

  override def validate(expr: Vector[InterpretedInput]): Either[IValidator, Option[ICommand]] =
    val returns = expr.headOption match
      case Some(value) => handleCommand(value.asInstanceOf[InterpretedCommand].commandType, expr.tail)
      case None => EmptyCommand(gsm)

    returns match
      case command: ICommand => Right(Some(command))
      case validator: IValidator => Left(validator)

  private def handleCommand(cT: CommandType, rest: Vector[InterpretedInput]): IValidator | ICommand =
    cT match
      case BUILD | RESEARCH | RECRUIT => InstantiationValidator(orig, gsm)
      case SELL => SellValidator(orig, gsm)
      case HELP => HelpValidator(orig, gsm)
      case LOAD | SAVE => FileIOValidator(orig, gsm)
      case SHOW => ShowValidator(orig, gsm)
      case LIST => ListValidator(orig, gsm)
      case MOVE => MoveValidator(orig, gsm)
      case _ => handleTerminalCommand(cT, rest)

  private def handleTerminalCommand(cT: CommandType, rest: Vector[InterpretedInput]): ICommand =
    cT match
      case USER_ACCEPT if rest.isEmpty => UserAcceptCommand(gsm)
      case USER_DECLINE if rest.isEmpty => UserDeclineCommand(gsm)
      case END_GAME if rest.isEmpty => EndGameCommand(gsm)
      case END_ROUND if rest.isEmpty => EndRoundCommand(gsm)
      case UNDO if rest.isEmpty => UndoCommand(gsm)
      case REDO if rest.isEmpty => RedoCommand(gsm)
      case _ => InvalidCommand(orig, gsm)
