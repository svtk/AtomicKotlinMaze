package matrix

data class Position(val x: Int, val y: Int)

interface Matrix<E> {
  val width: Int
  val height: Int
  fun all(): Set<E>
  fun allAt(position: Position): Set<E>
  fun position(element: E): Position?
  fun add(element: E, position: Position)
  fun remove(element: E)
}