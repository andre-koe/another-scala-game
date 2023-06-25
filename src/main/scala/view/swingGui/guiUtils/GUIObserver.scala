package view.swingGui.guiUtils

trait GUIObserver[T] {
  def onUpdate(event: T): Unit
}

trait Subject[T] {

  private var observers: List[GUIObserver[T]] = List()

  def addObserver(observer: GUIObserver[T]): Unit = {
    observers = observer :: observers
  }

  def notifyObservers(event: T): Unit = {
    observers.foreach(_.onUpdate(event))
  }
}
