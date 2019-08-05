package game.elements

import game.*
import matrix.Position

class Robot : MobileElement() {

  private var eatenFood: Int = 0

  override val symbol: Char
    get() = ROBOT

  override fun playTurn(
    maze: Maze
  ): Set<GameAction> {
    val sameCellElements = maze.sameCellElements(this)
    return sameCellElements
      .filterIsInstance<Food>()
      .map { food ->
        eatenFood++
        DestroyAction(food)
      }
      .toSet()
  }

  fun score(): Int {
    return eatenFood
  }

  override fun makeMove(currentPosition: Position, move: Move, maze: Maze): Position {
    val newPosition = currentPosition.applyMove(move)
    return if (maze.isPassable(newPosition)) newPosition
    else currentPosition
  }
}