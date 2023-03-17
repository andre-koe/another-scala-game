package utils

trait Observer {
  def update(): Unit
}

trait Observable {
  private var observers: Vector[Observer] = Vector[Observer]()
  def registerObserver(o:Observer): Unit = observers = observers :+ o
  def removeObserver(o:Observer): Unit = observers = observers.filter(_ != o)
  def notifyObservers(): Unit = observers.foreach(_.update())

}
