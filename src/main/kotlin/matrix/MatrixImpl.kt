package matrix

class MatrixImpl<E>(
  override val width: Int,
  override val height: Int
) : MutableMatrix<E> {
  private val elementMap = List(height) {
    List(width) { mutableSetOf<E>() }
  }
  private val cellMap = mutableMapOf<E, Cell>()

  private fun elementsIn(cell: Cell): MutableSet<E> {
    if (cell.x !in 0 until width || cell.y !in 0 until height)
      throw IllegalArgumentException(
        "Wrong cell(${cell.x}, ${cell.y}): " +
          "not in a range of (0..${width - 1}, 0..${height - 1}"
      )
    return elementMap[cell.y][cell.x]
  }

  override fun add(element: E, cell: Cell) {
    if (cellMap.containsKey(element)) {
      throw IllegalArgumentException("Element $element is already present in a matrix")
    }
    elementsIn(cell) += element
    cellMap[element] = cell
  }

  override fun remove(element: E) {
    val cell = cell(element) ?: return
    elementsIn(cell) -= element
    cellMap.remove(element)
  }

  override fun allIn(cell: Cell): Set<E> {
    return elementsIn(cell)
  }

  override fun cell(element: E): Cell? {
    return cellMap[element]
  }

  override fun all(): Set<E> {
    return cellMap.keys.toSet()
  }

  override fun toString(): String {
    return elementMap.joinToString("\n") { row ->
      row.joinToString("") { elements ->
        "${elements.lastOrNull()?.toString() ?: ' '}"
      }.trimEnd()
    }
  }
}