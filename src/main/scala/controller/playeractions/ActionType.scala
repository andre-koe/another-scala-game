package controller.playeractions

enum ActionType {
  case BUILD(name: Option[String])
  case RESEARCH(name: Option[String])
  case RECRUIT(name: Option[String], quantity: Option[String])
  case SELL(name: Option[String])
  case MOVE(positionX: Option[String], positionY: Option[String])
  case SAVE(name: Option[String])
  case LOAD(name: Option[String])
  case INVALID(input: Option[String])
  case SHOW(what: Option[String])
  case LIST(what: Option[String])
  case EMPTY
  case EXIT
  case HELP
  case FINISH_ROUND_REQUEST
  case USER_ACCEPT
  case USER_DECLINE
}