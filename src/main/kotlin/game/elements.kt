package game

import game.elements.*
import matrix.MatrixImpl
import matrix.Cell

const val WALL = '#'
const val FOOD = '.'
const val MONSTER = 'M'
const val ROBOT = 'R'
const val EXIT = '!'
val BOMB_RANGE = '0'..'9'
val TELEPORT_RANGE = 'a'..'z'

fun createGameElement(char: Char?): GameElement? = when (char) {
  WALL -> Wall()
  FOOD -> Food()
  MONSTER -> Monster()
  ROBOT -> Robot()
  EXIT -> Exit()
  in BOMB_RANGE -> Bomb(char!! - '0')
  in TELEPORT_RANGE -> Teleport(char!!)
  else -> null
}

typealias PostProcessor = (Maze) -> Unit

val elementPostProcessors = listOf(Teleport)

fun createMaze(representation: String): MutableMaze {
  val lines = representation.trim().lines()
  val width = lines.maxBy { it.length }?.length ?: 0
  val height = lines.size

  val maze = MatrixImpl<GameElement>(width, height)
  for (y in 0 until height) {
    for (x in 0 until width) {
      val ch = lines.getOrNull(y)?.getOrNull(x)
      val element = createGameElement(ch)
      if (element != null) {
        maze.add(element, Cell(x, y))
      }
    }
  }
  for (process in elementPostProcessors) {
    process(maze)
  }
  return maze
}

fun createGame(representation: String): Game {
  return GameImpl(representation)
}