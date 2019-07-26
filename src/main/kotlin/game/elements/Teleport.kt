package game.elements

import game.*

class Teleport(
    override val symbol: Char
) : StaticElement(sharesCell = true) {

    private var connection: Teleport? = null

    override fun interact(maze: Maze): Set<GameAction> {
        val sameCellElements = maze.sameCellElements(this)
        if (connection == null) return setOf()
        return sameCellElements.mapNotNull {
            maze.position(connection!!)?.let { newPosition ->
                MoveAction(it, newPosition)
            }
        }.toSet()
    }

    companion object : PostProcessor {
        override fun invoke(maze: Maze) {
            val groupedConnections = maze.all()
                .filterIsInstance<Teleport>()
                .groupBy { it.symbol }

            for ((symbol, connections) in groupedConnections) {
                check(connections.size == 2) {
                    "Wrong map: expected exactly two teleports for $symbol"
                }
                val (first, second) = connections
                first.connection = second
                second.connection = first
            }
        }
    }
}