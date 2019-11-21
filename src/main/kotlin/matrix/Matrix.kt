package matrix

data class Position(val x: Int, val y: Int)

interface Cell<E> {
  val position: Position
  val occupants: Set<E>
}

interface Matrix<E> {
  val width: Int
  val height: Int
  fun all(): Set<E>
  fun cell(position: Position): Cell<E>
  fun position(element: E): Position?
}

interface MutableMatrix<E>: Matrix<E> {
  fun add(element: E, position: Position)
  fun remove(element: E)
}