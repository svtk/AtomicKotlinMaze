package matrix

data class Cell(val x: Int, val y: Int)

interface Matrix<E> {
  val width: Int
  val height: Int
  fun all(): Set<E>
  fun allIn(cell: Cell): Set<E>
  fun cell(element: E): Cell?
}

interface MutableMatrix<E>: Matrix<E> {
  fun add(element: E, cell: Cell)
  fun remove(element: E)
}