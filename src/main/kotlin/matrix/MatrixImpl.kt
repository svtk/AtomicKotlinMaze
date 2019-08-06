package matrix

class MatrixImpl<E>(
  override val width: Int,
  override val height: Int
) : MutableMatrix<E> {
  private val cells = List(height) {
    List(width) { mutableSetOf<E>() }
  }
  private val positions = mutableMapOf<E, Position>()

  private fun elements(position: Position): MutableSet<E> {
    if (position.x !in 0 until width || position.y !in 0 until height)
      throw IllegalArgumentException(
        "Wrong position(${position.x}, ${position.y}): " +
          "not in a range of (0..${width - 1}, 0..${height - 1}"
      )
    return cells[position.y][position.x]
  }

  override fun add(element: E, position: Position) {
    if (positions.containsKey(element)) {
      throw IllegalArgumentException("Element $element is already present in a map")
    }
    elements(position) += element
    positions[element] = position
  }

  override fun remove(element: E) {
    val position = position(element) ?: return
    elements(position) -= element
    positions.remove(element)
  }

  override fun allAt(position: Position): Set<E> {
    return elements(position)
  }

  override fun position(element: E): Position? {
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