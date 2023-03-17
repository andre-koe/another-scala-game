trait Purchasable {
  def getName: String
  def getCost: Int
}

trait UnderConstruction {
  def getTimeToFinish: String
  def reduceTimeToFinish: UnderConstruction
  def finishConstruction: Purchasable
  def getIsUnique: Boolean
}

trait Building extends Purchasable {
  def getOutput: Int
}

trait Technology extends Purchasable {
  def unlocks: Option[List[Technology]]
}

trait IUnit extends Purchasable {
  def getSpeed: Int
  def getRank: Int
  def getAttack: Int
  def promote: Recruitable
}

case class Cruiser(speed: Int, rank: Int, attack: Int) extends IUnit {
  override def getSpeed: Int
  override def getRank: Int
  override def getAttack: Int
  override def promote: IUnit = Cruiser(speed + rank, rank + 1, attack + rank)
}

case class Corvette(speed: Int, rank: Int, attack: Int) extends IUnit {
  override def getSpeed: Int
  override def getRank: Int
  override def getAttack: Int
  override def promote: IUnit = Corvette(speed + rank, rank + 1, attack + rank)
}

case class CruiserConstruction(n: String) extends UnderConstruction {
  override def getTimeToFinish: String = 4
  override def getIsUnique: Boolean = false
  override def finishConstruction: Purchasable = {
    getTimeToFinish match
      case 0 => 
  }
  override def reduceTimeToFinish: Purchasable = getTimeToFinish match
    case 0 => Cruiser(2 ,0, 3)
    case _ => CruiserConstruction(0, getTimeToFinish - 1)
}

case class CorvetteConstruction(rank: Int = 0, timeToFinish: Int = 2) extends UnderConstruction {
  override def getName: String = "Corvette"
  override def getCost: Int = 20
  override def getTimeToFinish: Int = timeToFinish
  override def getIsUnique: Boolean = false
  override def reduceTimeToFinish: Purchasable = getTimeToFinish match
    case 0 => Corvette(4, 0, 1)
    case _ => CorvetteConstruction(0, getTimeToFinish - 1)
}


def acquire(str: String): UnderConstruction = {
  str match
    case 
}