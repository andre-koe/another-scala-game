enum ActionType {
  case EMPTY
  case BUILD(name: String)
  case RESEARCH(name: String)
  case RECRUIT(name: String, quantity: Option[String])
  case EXIT
  case HELP
  case INVALID
}

enum GameState {
  case RUNNING
  case EXITED
  case UNDEFINED_USER_INPUT
  case HELP_REQUEST
}