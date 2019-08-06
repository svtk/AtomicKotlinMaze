package game.elements

import game.*

class Teleport(
  override val symbol: Char
) : StaticElement(sharesCell = true) {

  private var connection: Teleport? = null

  override fun playTurn(maze: Maze): Set<GameAction> {
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
      val groupedTeleports = maze.all()
        .filterIsInstance<Teleport>()
        .groupBy { it.symbol }

      for ((symbol, teleports) in groupedTeleports) {
        check(teleports.size == 2) {
          "Wrong map: expected exactly two teleports for $symbol"
        }
        val (first, second) = teleports
        first.connection = second
        second.connection = first
      }
    }
  }
}