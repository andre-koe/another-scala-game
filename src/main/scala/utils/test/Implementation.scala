package utils.test

// Strategy
trait Implementation {
  def doSomething(): Unit
}

class Implementation1 extends Implementation {
  override def doSomething(): Unit = print("test")
}

class Implementation2 extends Implementation {
  override def doSomething(): Unit = print("hi")
}

class ImplementationHandler {
  var implementation: Implementation = Implementation1()
  
  def select(int: Int): Unit =
    implementation = if int == 1 then Implementation1() else Implementation2()
  
  def doAction(): Unit = implementation.doSomething()
}

// State Pattern
trait State {
  def doSomething(): State
}
class State1 extends State {
  override def doSomething(): State =
    print("test")
    State2()
}
class State2 extends State {
  override def doSomething(): State = 
    print("hi")
    State1()
}
class StateHandler {
  var state: State = State1()
  def handle(): Unit = state = state.doSomething()
}
