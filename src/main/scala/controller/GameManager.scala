package controller

import model.entities.{Building, Technology}

case class GameManager(round: Int) {
  private var roundCounter: Int = round

  // TODO: Keep track of what the player HAS, BUILDS, and CAN BUILD, RESEARCH AND RECRUIT
  // CAN BUILD can be an 
  //  var listOfBuildings: List[Building]
  //  var listOfTechnologies: List[Technology]
  //  var listOfUnits: List[Recruitable]
  def nextRound(): Unit = {
    roundCounter += 1
    // TODO: Implement things to do when round is done
  }

  // private def reduceRemainder(list: List[UnderConstruction]) : List[Purchasable] = ???

  def getRoundCounter: Int = roundCounter
}
