package game.elements

import game.*
import game.Move.*
import matrix.Position

class Monster : MobileElement() {

  override val symbol: Char
    get() = MONSTER

  override fun playTurn(maze: Maze): Set<GameAction> {
    val sameCellElements = maze.sameCellElements(this)
    val robot = sameCellElements.find { it is Robot } ?: return setOf()
    return setOf(DestroyAction(robot), GameOver)
  }

  override fun makeMove(move: Move, maze: Maze): Position? {
    val currentPosition = maze.position(this) ?: return null
    val directions = listOf(UP, RIGHT, DOWN, LEFT)
    val possiblePositions = directions
      .map { currentPosition.applyMove(it) }
      .filter { maze.isPassable(it) }
    if (possiblePositions.isEmpty()) return null
    return possiblePositions.random()
  }
}