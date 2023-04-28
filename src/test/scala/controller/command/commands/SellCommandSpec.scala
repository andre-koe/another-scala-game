package controller.command.commands

import model.game.{Capacity, PlayerValues}
import model.game.gamestate.GameStateManager
import model.game.purchasable.building.{EnergyGrid, Mine}
import model.game.purchasable.units.Cruiser
import model.game.resources.ResourceHolder
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class SellCommandSpec extends AnyWordSpec {
  
  "The SellCommand" should {
    "when initialized with help should return an explanatory gamestate message " in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("help", gameStateManager)
      sellCommand.execute()
        .toString should be("sell <name> (quantity) - Sell a building or an unit by specifying it's name use " +
        "the optional parameter quantity to sell more than 1 instance. You get half the cost back.")
    }
    "when initialized with empty input should return an explanatory gamestate message " in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("", gameStateManager)
      sellCommand.execute()
        .toString should be("sell <name> (quantity) - Sell a building or an unit by specifying it's name use " +
        "the optional parameter quantity to sell more than 1 instance. You get half the cost back.")
    }
    "when initialized with invalid input (size of input) should return an explanatory gamestate message " in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("i am most definitely invalid", gameStateManager)
      sellCommand.execute()
        .toString should be("'i am most definitely invalid' is invalid expected input of type sell <what> (quantity) " +
        s"use sell help to get an overview on how to use sell.")
    }
    "when initialized with a valid input length but nonexisting gameobject" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("Test", gameStateManager)
      sellCommand.execute()
        .toString should be(s"'Test' is neither a Unit nor a Building, use 'list buildings|units' " +
        s"to get an overview of all available units or buildings.")
    }
    "when initialized with a valid input length but nonexisting gameobject (size name 1) + quantity" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("Test 1", gameStateManager)
      sellCommand.execute()
        .toString should be(s"'Test' is neither a Unit nor a Building, use 'list buildings|units' " +
        s"to get an overview of all available units or buildings.")
    }
    "when initialized with a valid input length but nonexisting gameobject (size name 2) + quantity" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("Test Test 1", gameStateManager)
      sellCommand.execute()
        .toString should be(s"'Test Test' is neither a Unit nor a Building, use 'list buildings|units' " +
        s"to get an overview of all available units or buildings.")
    }
    "when initialized with a name of building which does exist but is not owned by the player" +
      " should return an explanatory gamestate message (name of length 2)" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("Energy Grid", gameStateManager)
      sellCommand.execute()
        .toString should be("Cannot sell 1 x Energy Grid, you can only sell what you own.")
    }
    "when initialized with a name of building which does exist but is not owned by the player" +
      " should return an explanatory gamestate message (name of length 1)" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("Mine", gameStateManager)
      sellCommand.execute()
        .toString should be("Cannot sell 1 x Mine, you can only sell what you own.")
    }
    "when initialized with a name of building which does exist and quantity but is not owned by the player" +
      " should return an explanatory gamestate message (name of length 1)" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("Mine 4", gameStateManager)
      sellCommand.execute()
        .toString should be("Cannot sell 4 x Mine, you can only sell what you own.")
    }
    "when initialized with a name of building (size 2) without quantity and is owned by the player" +
      " should return an explanatory gamestate message and updated resourceholder(name of length 2)" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder(),
        listOfBuildings = List(EnergyGrid(), EnergyGrid()))
      val gSM: GameStateManager = GameStateManager(playerValues = plV)
      val sellCommand: SellCommand = SellCommand("Energy Grid", gSM)
      sellCommand.execute()
        .playerValues.resourceHolder.energy.value should be(EnergyGrid().cost.divideBy(2).energy.value)
      sellCommand.execute().playerValues.listOfBuildings.length should be(1)
      sellCommand.execute()
        .toString should be(s"Successfully Sold: 1 x Energy Grid for a profit of " +
        s"${EnergyGrid().cost.divideBy(2)}.")
    }
    "when initialized with a name of building (size 2) and quantity and is owned by the player" +
      " should return an explanatory gamestate message and updated resourceholder(name of length 2)" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder(),
        listOfBuildings = List(EnergyGrid(), EnergyGrid()))
      val gSM: GameStateManager = GameStateManager(playerValues = plV)
      val sellCommand: SellCommand = SellCommand("Energy Grid 2", gSM)
      sellCommand.execute().playerValues.resourceHolder.minerals.value should be(75)
      sellCommand.execute().playerValues.listOfBuildings.length should be(0)
      sellCommand.execute()
        .toString should be(s"Successfully Sold: 2 x Energy Grid for a profit of " +
        s"${EnergyGrid().cost}.")
    }
    "when initialized with a name of unit which does exist but is not owned by the player" +
      " should return an explanatory gamestate message (name of length 2)" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("Cruiser", gameStateManager)
      sellCommand.execute()
        .toString should be("Cannot sell 1 x Cruiser, you can only sell what you own.")
    }
    "when initialized with a name of unit which does exist and quantity but is not owned by the player" +
      " should return an explanatory gamestate message (name of length 2)" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val sellCommand: SellCommand = SellCommand("Cruiser 4", gameStateManager)
      sellCommand.execute()
        .toString should be("Cannot sell 4 x Cruiser, you can only sell what you own.")
    }
    "when initialized with a name of building which does exist and is owned by the player" +
      " should return a success gamestate message and increase player resourceholder" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder(),
        listOfBuildings = List(Mine(), Mine()), capacity = Capacity(20))
      val gSM: GameStateManager = GameStateManager(playerValues = plV)
      val sellCommand: SellCommand = SellCommand("Mine", gSM)
      sellCommand.execute()
        .playerValues.resourceHolder.energy.value should be(Mine().cost.divideBy(2).energy.value)
      sellCommand.execute().playerValues.listOfBuildings.length should be(1)
      sellCommand.execute()
        .toString should be(s"Successfully Sold: 1 x Mine for a profit of ${Mine().cost.divideBy(2)}.")
    }
    "when initialized with a name of building which does exist and quantity and is owned by the player" +
      " should return a success gamestate message and increase player resourceholder" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder(),
        listOfBuildings = List(Mine(), Mine()), capacity = Capacity(20))
      val gSM: GameStateManager = GameStateManager(playerValues = plV)
      val sellCommand: SellCommand = SellCommand("Mine 2", gSM)
      sellCommand.execute()
        .playerValues.resourceHolder.energy.value should be(Mine().cost.energy.value)
      sellCommand.execute().playerValues.listOfBuildings.length should be(0)
      sellCommand.execute()
        .toString should be(s"Successfully Sold: 2 x Mine for a profit of ${Mine().cost}.")
    }
    "when initialized with a name of unit which does exist and quantity and is owned by the player" +
      " should return a success gamestate message and increase player capacity and resourceHolder" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder(),
        listOfUnits = List(Cruiser(), Cruiser()), capacity = Capacity(20))
      val gSM: GameStateManager = GameStateManager(playerValues = plV)
      val sellCommand: SellCommand = SellCommand("Cruiser", gSM)
      sellCommand.execute()
        .playerValues.resourceHolder.energy.value should be(Cruiser().cost.divideBy(2).energy.value)
      sellCommand.execute()
        .playerValues.resourceHolder.minerals.value should be(Cruiser().cost.divideBy(2).minerals.value)
      sellCommand.execute()
        .playerValues.resourceHolder.alloys.value should be(Cruiser().cost.divideBy(2).alloys.value)
      sellCommand.execute()
        .playerValues.capacity.value should be(20 + Cruiser().capacity.value)
      sellCommand.execute().playerValues.listOfUnits.length should be(1)
      sellCommand.execute()
        .toString should be(s"Successfully Sold: 1 x Cruiser for a profit of ${Cruiser().cost.divideBy(2)}.")
    }
    "when initialized with a name of unit which does exist and quantity 3 and is owned by the player" +
      " should return a success gamestate message and increase player capacity and resourceHolder" in {
      val plV: PlayerValues = PlayerValues(resourceHolder = ResourceHolder(),
        listOfUnits = List(Cruiser(), Cruiser(), Cruiser(), Cruiser()), capacity = Capacity(20))
      val gSM: GameStateManager = GameStateManager(playerValues = plV)
      val sellCommand: SellCommand = SellCommand("Cruiser 3", gSM)
      sellCommand.execute()
        .playerValues.resourceHolder.energy.value should be(225)
      sellCommand.execute()
        .playerValues.resourceHolder.minerals.value should be(150)
      sellCommand.execute()
        .playerValues.resourceHolder.alloys.value should be(112)
      sellCommand.execute()
        .playerValues.capacity.value should be(20 + Cruiser().capacity.value * 3)
      sellCommand.execute().playerValues.listOfUnits.length should be(1)
      sellCommand.execute()
        .toString should be(s"Successfully Sold: 3 x Cruiser for a profit of Total Cost: " +
        s"[Energy: 225] [Minerals: 150] [Alloys: 112].")
    }
  }
}
