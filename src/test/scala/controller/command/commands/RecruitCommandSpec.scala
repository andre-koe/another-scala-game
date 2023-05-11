package controller.command.commands

import model.game.{Capacity, PlayerValues}
import model.game.gamestate.GameStateManager
import model.game.purchasable.units.Corvette
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class RecruitCommandSpec extends AnyWordSpec {

  "The RecruitCommand" should {
    "invoke the correct behaviour corresponding to the request" when {

      "initialized with with valid unit and sufficient capacity and resources" in {
        val pV: PlayerValues = PlayerValues(
          capacity = Capacity(100),
          resourceHolder = ResourceHolder(energy = Energy(1000), minerals = Minerals(1000)))
        val gsm: GameStateManager = GameStateManager(playerValues = pV)

        val recruitCommand = RecruitCommand(Corvette(), 2, gsm)
        recruitCommand.execute().playerValues.listOfUnitsUnderConstruction.isEmpty should be(false)
        recruitCommand.execute().toString should be("Beginning construction of 2 x Corvette " +
          s"for ${Corvette().cost.multiplyBy(2)}, completion in ${Corvette().roundsToComplete.value} rounds.")
      }

      "initialized with with valid unit and sufficient resources but insuffficient capacity" in {
        val pV: PlayerValues = PlayerValues(
          capacity = Capacity(0),
          resourceHolder = ResourceHolder(energy = Energy(1000), minerals = Minerals(1000)))
        val gsm: GameStateManager = GameStateManager(playerValues = pV)

        val recruitCommand = RecruitCommand(Corvette(), 2, gsm)
        recruitCommand.execute().playerValues.listOfUnitsUnderConstruction.isEmpty should be(true)
        recruitCommand.execute().toString should be(s"Insufficient Capacity --- [Capacity: 2].")
      }

      "initialized with with valid unit and sufficient capacity but insuffficient resources" in {
        val pV: PlayerValues = PlayerValues(
          capacity = Capacity(2),
          resourceHolder = ResourceHolder(energy = Energy(0), minerals = Minerals(0)))
        val gsm: GameStateManager = GameStateManager(playerValues = pV)

        val recruitCommand = RecruitCommand(Corvette(), 2, gsm)
        recruitCommand.execute().playerValues.listOfUnitsUnderConstruction.isEmpty should be(true)
        recruitCommand.execute().toString should be(s"Insufficient Funds --- Total Lacking: [Energy: 140] [Minerals: 60].")
      }

    }
  }
}
