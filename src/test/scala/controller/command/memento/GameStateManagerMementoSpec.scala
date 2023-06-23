package controller.command.memento

import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameStateManagerMementoSpec extends AnyWordSpec with Matchers {

  "A GameStateManagerMemento" should {

    "correctly store the GameStateManager's state" in {
      val gameStateManager = GameStateManager() // Initialize a GameStateManager instance
      val memento = GameStateManagerMemento().storeState(gameStateManager)

      memento.state should be("{\"round\":{\"value\":1}," +
        "\"gameMap\":{\"sizeX\":7,\"sizeY\":7," +
        "\"data\":[[{\"sector\"" +
        ":{\"location\":{\"xPos\":0,\"yPos\":0},\"affiliation\"" +
        ":{\"PLAYER\":{}},\"sectorType\":{\"BASE\":{}}," +
        "\"unitsInSector\":[]},\"constQuBuilding\":[]," +
        "\"constQuUnits\":[],\"buildingsInSector\":[]}," +
        "{\"location\":{\"xPos\":0,\"yPos\":1}," +
        "\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":" +
        "{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":0,\"yPos\":2},\"affiliation\":" +
        "{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":" +
        "{\"xPos\":0,\"yPos\":3},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":" +
        "{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":0,\"yPos\":4},\"affiliation\":{\"INDEPENDENT\":" +
        "{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":0,\"yPos\":5}," +
        "\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]}," +
        "{\"location\":{\"xPos\":0,\"yPos\":6},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":" +
        "{\"REGULAR\":{}},\"unitsInSector\":[]}],[{\"location\":{\"xPos\":1,\"yPos\":0},\"affiliation\":" +
        "{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":" +
        "{\"xPos\":1,\"yPos\":1},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}," +
        "\"unitsInSector\":[]},{\"location\":{\"xPos\":1,\"yPos\":2},\"affiliation\":{\"INDEPENDENT\":{}}" +
        ",\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":1,\"yPos\":3},\"affiliation\"" +
        ":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":" +
        "{\"xPos\":1,\"yPos\":4},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}" +
        ",\"unitsInSector\":[]},{\"location\":{\"xPos\":1,\"yPos\":5},\"affiliation\":{\"INDEPENDENT\":{}}" +
        ",\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":1,\"yPos\":6},\"affiliation\":" +
        "{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]}],[{\"location\":" +
        "{\"xPos\":2,\"yPos\":0},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}" +
        ",\"unitsInSector\":[]},{\"location\":{\"xPos\":2,\"yPos\":1},\"affiliation\":{\"INDEPENDENT\":{}}," +
        "\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":2,\"yPos\":2},\"affiliation\"" +
        ":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\"" +
        ":{\"xPos\":2,\"yPos\":3},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}" +
        ",\"unitsInSector\":[]},{\"location\":{\"xPos\":2,\"yPos\":4},\"affiliation\":{\"INDEPENDENT\":{}}" +
        ",\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":2,\"yPos\":5}," +
        "\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]}," +
        "{\"location\":{\"xPos\":2,\"yPos\":6},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":" +
        "{\"REGULAR\":{}},\"unitsInSector\":[]}],[{\"location\":{\"xPos\":3,\"yPos\":0},\"affiliation\":" +
        "{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":" +
        "{\"xPos\":3,\"yPos\":1},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}," +
        "\"unitsInSector\":[]},{\"location\":{\"xPos\":3,\"yPos\":2},\"affiliation\":{\"INDEPENDENT\":{}}," +
        "\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":3,\"yPos\":3},\"affiliation\"" +
        ":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]}," +
        "{\"location\":{\"xPos\":3,\"yPos\":4},\"affiliation\":{\"INDEPENDENT\":{}}," +
        "\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":3,\"yPos\":5},\"affiliation\"" +
        ":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\"" +
        ":{\"xPos\":3,\"yPos\":6},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}" +
        ",\"unitsInSector\":[]}],[{\"location\":{\"xPos\":4,\"yPos\":0},\"affiliation\":{\"INDEPENDENT\":{}}" +
        ",\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":4,\"yPos\":1}" +
        ",\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]}," +
        "{\"location\":{\"xPos\":4,\"yPos\":2},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":" +
        "{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":4,\"yPos\":3},\"affiliation\"" +
        ":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\"" +
        ":{\"xPos\":4,\"yPos\":4},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}" +
        ",\"unitsInSector\":[]},{\"location\":{\"xPos\":4,\"yPos\":5},\"affiliation\":{\"INDEPENDENT\":{}}" +
        ",\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":4,\"yPos\":6},\"affiliation\"" +
        ":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]}],[{\"location\"" +
        ":{\"xPos\":5,\"yPos\":0},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}," +
        "\"unitsInSector\":[]},{\"location\":{\"xPos\":5,\"yPos\":1},\"affiliation\":{\"INDEPENDENT\":{}}," +
        "\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":5,\"yPos\":2},\"affiliation\":" +
        "{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":" +
        "{\"xPos\":5,\"yPos\":3},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}," +
        "\"unitsInSector\":[]},{\"location\":{\"xPos\":5,\"yPos\":4},\"affiliation\":{\"INDEPENDENT\":{}}," +
        "\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":5,\"yPos\":5},\"affiliation\"" +
        ":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]}," +
        "{\"location\":{\"xPos\":5,\"yPos\":6},\"affiliation\":{\"INDEPENDENT\":{}}," +
        "\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]}],[{\"location\":" +
        "{\"xPos\":6,\"yPos\":0},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":" +
        "{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":6,\"yPos\":1},\"affiliation\":{\"INDEPENDENT\":" +
        "{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":6,\"yPos\":2},\"affiliation\":" +
        "{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":{\"xPos\":6,\"yPos\":3}" +
        ",\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":[]},{\"location\":" +
        "{\"xPos\":6,\"yPos\":4},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}},\"unitsInSector\":" +
        "[]},{\"location\":{\"xPos\":6,\"yPos\":5},\"affiliation\":{\"INDEPENDENT\":{}},\"sectorType\":{\"REGULAR\":{}}," +
        "\"unitsInSector\":[]},{\"location\":{\"xPos\":6,\"yPos\":6},\"affiliation\":{\"ENEMY\":{}},\"sectorType\":" +
        "{\"BASE\":{}},\"unitsInSector\":[]}]]},\"message\":\"\",\"playerValues\":{\"resourceHolder\":" +
        "{\"descriptor\":\"Balance\",\"energy\":{\"value\":1000,\"name\":\"Energy\"},\"minerals\":" +
        "{\"value\":1000,\"name\":\"Minerals\"},\"alloys\":{\"value\":10,\"name\":\"Alloys\"},\"researchPoints\":" +
        "{\"value\":1000,\"name\":\"Research Points\"}},\"listOfTechnologies\":[],\"listOfTechnologiesCurrentlyResearched\"" +
        ":[],\"capacity\":{\"value\":3},\"upkeep\":{\"descriptor\":\"Running Cost\",\"energy\":" +
        "{\"value\":0,\"name\":\"Energy\"},\"minerals\":{\"value\":0,\"name\":\"Minerals\"},\"alloys\":" +
        "{\"value\":0,\"name\":\"Alloys\"},\"researchPoints\":{\"value\":0,\"name\":\"Research Points\"}}," +
        "\"income\":{\"descriptor\":\"Income\",\"energy\":{\"value\":0,\"name\":\"Energy\"},\"minerals\":" +
        "{\"value\":0,\"name\":\"Minerals\"},\"alloys\":{\"value\":0,\"name\":\"Alloys\"},\"researchPoints\":" +
        "{\"value\":0,\"name\":\"Research Points\"}}}}")
    }

    "correctly retrieve a GameStateManager from its state" in {
      val expectedGameStateManager = GameStateManager() // Initialize a GameStateManager instance
      val memento = GameStateManagerMemento().storeState(expectedGameStateManager)

      val retrievedGameStateManager = memento.getState.get
      retrievedGameStateManager should be(expectedGameStateManager)
    }
  }
}
