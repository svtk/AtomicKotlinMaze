package ui

import game.createGame
import java.io.File

fun main() {
  val mapRepresentation = File("mazes/0.txt").readText()
  val game = createGame(mapRepresentation)
  playGame(game)
}