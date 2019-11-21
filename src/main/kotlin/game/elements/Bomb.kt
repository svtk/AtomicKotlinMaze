package game.elements

import game.*
import matrix.Position
import kotlin.math.sqrt

class Bomb(
  private val diameter: Int
) : StaticElement(sharesCell = true) {

  override val symbol
    get() = '0' + diameter

  override fun play(maze: Maze): Set<GameAction> {
    val sameCellElements = maze.sameCellElements(this)
    if (sameCellElements.isEmpty()) return setOf()
    val bombPosition = maze.position(this) ?: return setOf()
    return maze.all().flatMap { element ->
      val cell = maze.position(element)
      if (cell != null && isCloseToBomb(cell, bombPosition)) {
        if (element is Robot) {
          listOf(DestroyAction(element), GameOver)
        } else {
          listOf(DestroyAction(element))
        }
      } else listOf()
    }.toSet()
  }

  private fun isCloseToBomb(position: Position, bombPosition: Position) =
    distance(position, bombPosition) <= diameter / 2

  private fun distance(from: Position, to: Position): Double {
    fun sqr(i: Int) = i.toDouble() * i
    return sqrt(sqr(from.x - to.x) + sqr(from.y - to.y))
  }
}