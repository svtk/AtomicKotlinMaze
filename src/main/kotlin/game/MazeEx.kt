package game

import matrix.Position

fun Maze.sameCellElements(element: GameElement): Set<GameElement> {
  val position = position(element) ?: return setOf()
  return cell(position).occupants - element
}

fun Maze.isPassable(position: Position): Boolean {
  if (position.x !in (0 until width) || position.y !in (0 until height)) {
    return false
  }
  val cellOccupants = cell(position).occupants
  return cellOccupants.all { it.sharesCell }
}

fun Position.applyMove(move: Move): Position =
  when (move) {
    Move.WAIT -> Position(x, y)
    Move.UP -> Position(x, y - 1)
    Move.DOWN -> Position(x, y + 1)
    Move.RIGHT -> Position(x + 1, y)
    Move.LEFT -> Position(x - 1, y)
  }