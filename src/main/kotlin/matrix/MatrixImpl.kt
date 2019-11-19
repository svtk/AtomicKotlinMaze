package matrix

class MatrixImpl<E>(
  override val width: Int,
  override val height: Int
) : MutableMatrix<E> {
  private val cells = List(height) {
    List(width) { mutableSetOf<E>() }
  }
  private val positions = mutableMapOf<E, Cell>()

  private fun elements(cell: Cell): MutableSet<E> {
    if (cell.x !in 0 until width || cell.y !in 0 until height)
      throw IllegalArgumentException(
        "Wrong cell(${cell.x}, ${cell.y}): " +
          "not in a range of (0..${width - 1}, 0..${height - 1}"
      )
    return cells[cell.y][cell.x]
  }

  override fun add(element: E, cell: Cell) {
    if (positions.containsKey(element)) {
      throw IllegalArgumentException("Element $element is already present in a map")
    }
    elements(cell) += element
    positions[element] = cell
  }

  override fun remove(element: E) {
    val cell = cell(element) ?: return
    elements(cell) -= element
    positions.remove(element)
  }

  override fun allAt(cell: Cell): Set<E> {
    return elements(cell)
  }

  override fun cell(element: E): Cell? {
    return positions[element]
  }

  override fun all(): Set<E> {
    return positions.keys.toSet()
  }

  override fun toString(): String {
    return cells.joinToString("\n") { row ->
      row.joinToString("") { elements ->
        "${elements.lastOrNull()?.toString() ?: ' '}"
      }.trimEnd()
    }
  }
}