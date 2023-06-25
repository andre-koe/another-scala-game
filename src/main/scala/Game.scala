import controller.{Controller, IController}
import view.swingGui.GuiS
import view.tui.Tui
import utils.DefaultValueProvider.{given_IController, given_ICommandTokenizer}

object Game:
  def main(string:Array[String]): Unit =
    GuiS()
    Tui().run()


