package game

import game.Move.*
import org.junit.Test

class TestFood : AbstractTestGame() {
  @Test
  fun test1() {
    val robotMoves = listOf(RIGHT, LEFT, DOWN, DOWN, RIGHT, RIGHT, RIGHT)
    checkGame(
      robotMoves,
      initial = """
            ######
            #R.  #
            #  ###
            #....#
            ######""",
      expected = """
            ######
            #    #
            #  ###
            #   R#
            ######"""
    )
  }
}