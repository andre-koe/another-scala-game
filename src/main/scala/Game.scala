import controller.Controller
import view.tui.Tui

object Game:
  def main(string:Array[String]): Unit = Tui(new Controller()).run()
