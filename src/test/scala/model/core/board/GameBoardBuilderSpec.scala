package model.core.board

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameBoardBuilderSpec extends AnyWordSpec with Matchers {

  "A GameBoardBuilder" should {

    "correctly set cols with withSizeX" in {
      val builder = GameBoardBuilder()
      val newBuilder = builder.withSizeX(10)
      newBuilder.cols should be (10)
    }

    "correctly set rows with withSizeY" in {
      val builder: IGameBoardBuilder = GameBoardBuilder()
      val newBuilder = builder.withSizeY(10)
      newBuilder.rows should be (10)
    }

    "build a GameBoard with correct rows and cols" in {
      val builder: IGameBoardBuilder = GameBoardBuilder()
      val gameBoard = builder.build
      gameBoard.sizeX should be (7)
      gameBoard.sizeY should be (7)
    }

    "correctly set data with withData" in {
      val builder: IGameBoardBuilder = GameBoardBuilder().withSizeX(10).withSizeY(10)
      val newBuilder = builder.withData
      newBuilder.data.size should be(10)
      newBuilder.data.foreach(row => row.size should be(10))
    }

  }
}