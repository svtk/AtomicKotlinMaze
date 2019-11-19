package game.elements

import game.*
import matrix.Cell

class Robot : MobileElement() {

  private var eatenFoodItems: Int = 0

  override val symbol: Char
    get() = ROBOT

  override fun play(
    maze: Maze
  ): Set<GameAction> {
    val sameCellElements = maze.sameCellElements(this)
    return sameCellElements
      .filterIsInstance<Food>()
      .map { food ->
        eatenFoodItems++
        DestroyAction(food)
      }
      .toSet()
  }

  fun score(): Int {
    return eatenFoodItems
  }

  override fun move(move: Move, maze: Maze): Cell? {
    val currentPosition = maze.position(this) ?: return null
    val newPosition = currentPosition.applyMove(move)
    return if (maze.isPassable(newPosition)) newPosition
    else null
  }
}