package controller.command.commands

import model.game.Capacity
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import model.game.purchasable.building.{EnergyGrid, Mine}
import model.game.purchasable.units.Cruiser
import model.game.resources.ResourceHolder
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class SellCommandSpec extends AnyWordSpec {
  
  "The SellCommand" should {
    "sell a unit if player owns it" in {
      val pV : PlayerValues = PlayerValues(listOfUnits = List(Cruiser(), Cruiser()))
      val gsm : GameStateManager = GameStateManager(playerValues = pV)

      val sellCommand: SellCommand = SellCommand(Cruiser(), 1, gsm)
      sellCommand.execute().playerValues.listOfUnits.size should be(1)
      sellCommand.execute().toString should be(s"Successfully Sold: 1 x Cruiser for a " +
        s"profit of ${Cruiser().cost.divideBy(2)}.")
    }

    "sell multiple units if player owns them" in {
      val pV: PlayerValues = PlayerValues(listOfUnits = List(Cruiser(), Cruiser()))
      val gsm: GameStateManager = GameStateManager(playerValues = pV)

      val sellCommand: SellCommand = SellCommand(Cruiser(), 2, gsm)
      sellCommand.execute().playerValues.listOfUnits.size should be(0)
      sellCommand.execute().toString should be(s"Successfully Sold: 2 x Cruiser for a " +
        s"profit of ${Cruiser().cost}.")
    }

    "sell a building if player owns it" in {
      val pV: PlayerValues = PlayerValues(listOfBuildings = List(Mine(), Mine()))
      val gsm: GameStateManager = GameStateManager(playerValues = pV)

      val sellCommand: SellCommand = SellCommand(Mine(), 1, gsm)
      sellCommand.execute().playerValues.listOfBuildings.size should be(1)
      sellCommand.execute().toString should be(s"Successfully Sold: 1 x Mine for a " +
        s"profit of ${Mine().cost.divideBy(2)}.")
    }

    "sell a multiple buildings if player owns them" in {
      val pV: PlayerValues = PlayerValues(listOfBuildings = List(Mine(), Mine()))
      val gsm: GameStateManager = GameStateManager(playerValues = pV)

      val sellCommand: SellCommand = SellCommand(Mine(), 2, gsm)
      sellCommand.execute().playerValues.listOfBuildings.size should be(0)
      sellCommand.execute().toString should be(s"Successfully Sold: 2 x Mine for a " +
        s"profit of ${Mine().cost}.")
    }

  }
}
