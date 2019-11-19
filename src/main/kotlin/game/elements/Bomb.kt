package game.elements

import game.*
import matrix.Cell
import kotlin.math.sqrt

class Bomb(
  private val diameter: Int
) : StaticElement(sharesCell = true) {

  override val symbol
    get() = '0' + diameter

  override fun play(maze: Maze): Set<GameAction> {
    val sameCellElements = maze.sameCellElements(this)
    if (sameCellElements.isEmpty()) return setOf()
    val bombPosition = maze.cell(this) ?: return setOf()
    return maze.all().flatMap { element ->
      val cell = maze.cell(element)
      if (cell != null && isCloseToBomb(cell, bombPosition)) {
        if (element is Robot) {
          listOf(DestroyAction(element), GameOver)
        } else {
          listOf(DestroyAction(element))
        }
      } else listOf()
    }.toSet()
  }

  private fun isCloseToBomb(cell: Cell, bombCell: Cell) =
    distance(cell, bombCell) <= diameter / 2

  private fun distance(from: Cell, to: Cell): Double {
    fun sqr(i: Int) = i.toDouble() * i
    return sqrt(sqr(from.x - to.x) + sqr(from.y - to.y))
  }
}