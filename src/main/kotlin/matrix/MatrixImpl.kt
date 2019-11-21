package matrix

class MatrixImpl<E>(
  override val width: Int,
  override val height: Int
) : MutableMatrix<E> {
  private val elementMap = List(height) {
    List(width) { mutableSetOf<E>() }
  }
  private val cellMap = mutableMapOf<E, Position>()

  private fun elementsIn(position: Position): MutableSet<E> {
    if (position.x !in 0 until width || position.y !in 0 until height)
      throw IllegalArgumentException(
        "Wrong cell(${position.x}, ${position.y}): " +
          "not in a range of (0..${width - 1}, 0..${height - 1}"
      )
    return elementMap[position.y][position.x]
  }

  override fun add(element: E, position: Position) {
    if (cellMap.containsKey(element)) {
      throw IllegalArgumentException("Element $element is already present in a matrix")
    }
    elementsIn(position) += element
    cellMap[element] = position
  }

  override fun remove(element: E) {
    val cell = position(element) ?: return
    elementsIn(cell) -= element
    cellMap.remove(element)
  }

  override fun cell(position: Position): Cell<E> {
    return Cell(position, elementsIn(position))
  }

  override fun position(element: E): Position? {
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