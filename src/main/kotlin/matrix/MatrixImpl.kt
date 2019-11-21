package matrix

class MatrixImpl<E>(
  override val width: Int,
  override val height: Int
) : MutableMatrix<E> {

  data class MutableCell<E>(
    override val position: Position,
    override val occupants: MutableSet<E> = mutableSetOf()
  ): Cell<E>

  private val cells = List(height) {
    List(width) { MutableCell(Position(height, width), mutableSetOf<E>()) }
  }
  private val positionMap = mutableMapOf<E, Position>()

  private fun cellFor(position: Position): MutableCell<E> {
    if (position.x !in 0 until width || position.y !in 0 until height)
      throw IllegalArgumentException(
        "Wrong cell(${position.x}, ${position.y}): " +
          "not in a range of (0..${width - 1}, 0..${height - 1}"
      )
    return cells[position.y][position.x]
  }

  override fun add(element: E, position: Position) {
    if (positionMap.containsKey(element)) {
      throw IllegalArgumentException("Element $element is already present in a matrix")
    }
    cellFor(position).occupants += element
    positionMap[element] = position
  }

  override fun remove(element: E) {
    val cell = position(element) ?: return
    cellFor(cell).occupants -= element
    positionMap.remove(element)
  }

  override fun cell(position: Position): Cell<E> {
    return cellFor(position)
  }

  override fun position(element: E): Position? {
    return positionMap[element]
  }

  override fun all(): Set<E> {
    return positionMap.keys.toSet()
  }

  override fun toString(): String {
    return cells.joinToString("\n") { row ->
      row.joinToString("") { cell ->
        "${cell.occupants.lastOrNull()?.toString() ?: ' '}"
      }.trimEnd()
    }
  }
}