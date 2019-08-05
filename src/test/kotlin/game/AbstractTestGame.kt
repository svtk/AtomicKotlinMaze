package game

import org.junit.Assert

abstract class AbstractTestGame {
  protected fun checkGame(
    moves: List<Move>,
    initial: String,
    expected: String
  ) {
    val game = GameImpl(initial.trimIndent())

    for (move in moves) {
      game.playMove(move)
      game.playTurn()
    }
    Assert.assertEquals(
      "Wrong result for\n$initial",
      expected.trimIndent(),
      game.maze.toString()
    )
  }

  protected fun checkOptions(
    moves: List<Move>,
    initial: String,
    expected: Set<String>
  ) {
    val game = GameImpl(initial.trimIndent())

    for (move in moves) {
      game.playMove(move)
      game.playTurn()
    }
    val result = game.maze.toString()
    Assert.assertTrue(
      "Wrong result for\n$result",
      result in expected.map { it.trimIndent() }
    )
  }
}