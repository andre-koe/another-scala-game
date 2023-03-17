package controller.gamestate

enum GameState {
  case RUNNING
  case EXITED
  case UNDEFINED_USER_INPUT
  case HELP_REQUEST
  case END_ROUND
}