package game

import game.Move.*
import org.junit.Test

class TestRobot : AbstractTestGame() {
  @Test
  fun test1() {
    val robotMoves = listOf(RIGHT, LEFT, DOWN, DOWN, WAIT, WAIT, RIGHT, RIGHT, RIGHT)
    checkGame(
      robotMoves,
      initial = """
            ######
            #R.  #
            #  ###
            #    #
            ######""",
      expected = """
            ######
            #    #
            #  ###
            #   R#
            ######"""
    )
  }

  @Test
  fun test2() {
    val robotMoves = listOf(RIGHT, UP, DOWN, DOWN, RIGHT)
    checkGame(
      robotMoves,
      initial = """
            ######
            #   R#
            ######""",
      expected = """
            ######
            #   R#
            ######"""
    )
  }
}