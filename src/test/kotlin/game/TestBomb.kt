package game

import game.Move.*
import org.junit.Test

class TestBomb : AbstractTestGame() {
  @Test
  fun test1() {
    val robotMoves = listOf(WAIT)
    checkGame(
      robotMoves,
      initial = """
            #######
            #M2  R#
            #######""",
      expected = """
            ## ####
            #    R#
            ## ####"""
    )
  }

  @Test
  fun test2() {
    val robotMoves = listOf(DOWN, DOWN, RIGHT, RIGHT, RIGHT)
    checkGame(
      robotMoves,
      initial = """
            ######
            #R   #
            #  2##
            #  ..#
            ######""",
      expected = """
            ######
            #    #
            #  2##
            #   R#
            ######"""
    )
  }

  @Test
  fun test3() {
    val robotMoves = listOf(DOWN, RIGHT, RIGHT)
    checkGame(
      robotMoves,
      initial = """
            ######
            #R   #
            #  2##
            #  ..#
            ######""",
      expected = """
            ######
            #    #
            #    #
            #   .#
            ######"""
    )
  }
}