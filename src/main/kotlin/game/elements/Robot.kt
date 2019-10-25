package game.elements

import game.*
import matrix.Position

class Robot : MobileElement() {

  private var eatenFoodItems: Int = 0

  override val symbol: Char
    get() = ROBOT

  override fun playTurn(
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

  override fun makeMove(move: Move, maze: Maze): Position? {
    val currentPosition = maze.position(this) ?: return null
    val newPosition = currentPosition.applyMove(move)
    return if (maze.isPassable(newPosition)) newPosition
    else null
  }
}