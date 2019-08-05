package game

import game.Move.RIGHT
import org.junit.Test

class TestTeleport : AbstractTestGame() {
  @Test
  fun test1() {
    checkGame(
      listOf(RIGHT),
      """
        #########
        #Ra  #a #
        #########""",
      """
        #########
        # a  #R #
        #########
            """
    )
  }

  @Test
  fun test2() {
    checkGame(
      listOf(RIGHT, RIGHT),
      """
        #########
        #Ra  #a #
        #########""",
      """
        #########
        # a  #aR#
        #########
            """
    )
  }
}