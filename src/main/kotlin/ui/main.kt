package ui

import game.createGame
import java.io.File

fun main() {
    val mapRepresentation = File("mazes/3.txt").readText()
    val game = createGame(mapRepresentation)
    playGame(game)
}