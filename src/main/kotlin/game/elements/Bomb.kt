package game.elements

import game.*
import matrix.Position
import kotlin.math.sqrt

class Bomb(
    private val diameter: Int
) : StaticElement(sharesCell = true) {

    override val symbol
        get() = '0' + diameter

    override fun interact(maze: Maze): Set<GameAction> {
        val sameCellElements = maze.sameCellElements(this)
        if (sameCellElements.isEmpty()) return setOf()
        val bombPosition = maze.position(this) ?: return setOf()
        return maze.all().mapNotNull { element ->
            val position = maze.position(element)
            if (position != null && isCloseToBomb(position, bombPosition)) {
                DestroyAction(element)
            } else null
        }.toSet()
    }

    private fun isCloseToBomb(position: Position, bombPosition: Position) =
        distance(position, bombPosition) <= diameter / 2

    private fun distance(from: Position, to: Position): Double {
        fun sqr(i: Int) = i.toDouble() * i
        return sqrt(sqr(from.x - to.x) + sqr(from.y - to.y))
    }
}