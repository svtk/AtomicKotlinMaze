package game

import matrix.Matrix
import matrix.MutableMatrix
import matrix.Cell

interface Game {
  enum class GameState(val isActive: Boolean) {
    ACTIVE(true),
    WON(false),
    LOST(false)
  }

  val maze: Maze
  val state: GameState
  fun playMove(move: Move)
  fun playTurn()
  fun score(): Int
}

enum class Move {
  UP, RIGHT, DOWN, LEFT, WAIT
}

interface GameElement {
  val symbol: Char
  val sharesCell: Boolean
  fun play(maze: Maze): Set<GameAction>
}

typealias Maze = Matrix<GameElement>
typealias MutableMaze = MutableMatrix<GameElement>

sealed class GameAction
data class MoveAction(val element: GameElement, val newCell: Cell) : GameAction()
data class DestroyAction(val element: GameElement) : GameAction()
data class CreateAction(val element: GameElement, val cell: Cell) : GameAction()
object GameOver: GameAction()
object GameWon: GameAction()

abstract class MobileElement : GameElement {
  override val sharesCell: Boolean
    get() = true

  abstract fun move(move: Move, maze: Maze): Cell?

  override fun toString() = symbol.toString()
}

abstract class StaticElement(
  override val sharesCell: Boolean
) : GameElement {

  override fun play(maze: Maze): Set<GameAction> {
    // Default implementation: do nothing
    return emptySet()
  }

  override fun toString() = symbol.toString()
}