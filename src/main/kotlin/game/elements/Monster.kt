package game.elements

import game.*
import game.Move.*
import matrix.Cell

class Monster : MobileElement() {

  override val symbol: Char
    get() = MONSTER

  override fun play(maze: Maze): Set<GameAction> {
    val sameCellElements = maze.sameCellElements(this)
    val robot = sameCellElements.find { it is Robot } ?: return setOf()
    return setOf(DestroyAction(robot), GameOver)
  }

  override fun move(move: Move, maze: Maze): Cell? {
    val currentPosition = maze.cell(this) ?: return null
    val directions = listOf(UP, RIGHT, DOWN, LEFT)
    val possiblePositions = directions
      .map { currentPosition.applyMove(it) }
      .filter { maze.isPassable(it) }
    if (possiblePositions.isEmpty()) return null
    return possiblePositions.random()
  }
}