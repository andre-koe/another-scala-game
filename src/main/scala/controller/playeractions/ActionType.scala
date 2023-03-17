package controller.playeractions

enum ActionType {
  case EMPTY
  case BUILD(name: String)
  case RESEARCH(name: String)
  case RECRUIT(name: String, quantity: Option[String])
  case EXIT
  case HELP
  case INVALID
  case FINISH_ROUND
}