package game

import matrix.Matrix
import matrix.MutableMatrix
import matrix.Position

interface Game {
  val maze: Maze
  fun playMove(move: Move)
  fun playTurn()
  fun gameOver(): Boolean
  fun hasWon(): Boolean
  fun score(): Int
}

enum class Move {
  UP, RIGHT, DOWN, LEFT, WAIT
}

interface GameElement {
  val symbol: Char
  val sharesCell: Boolean
  fun playTurn(maze: Maze): Set<GameAction>
}

typealias Maze = Matrix<GameElement>
typealias MutableMaze = MutableMatrix<GameElement>

sealed class GameAction
data class MoveAction(val element: GameElement, val newPosition: Position) : GameAction()
data class DestroyAction(val element: GameElement) : GameAction()
data class CreateAction(val element: GameElement, val position: Position) : GameAction()

abstract class MobileElement : GameElement {
  override val sharesCell: Boolean
    get() = true

  abstract fun makeMove(move: Move, maze: Maze): Position?

  override fun toString() = symbol.toString()
}

abstract class StaticElement(
  override val sharesCell: Boolean
) : GameElement {

  override fun playTurn(maze: Maze): Set<GameAction> {
    // Default implementation: do nothing
    return emptySet()
  }

  override fun toString() = symbol.toString()
}