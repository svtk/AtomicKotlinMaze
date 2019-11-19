package game.elements

import game.*

class Wall : StaticElement(sharesCell = false) {
  override val symbol: Char
    get() = WALL
}

class Food : StaticElement(sharesCell = true) {
  override val symbol: Char
    get() = FOOD
}

class Exit : StaticElement(sharesCell = true) {
  override val symbol: Char
    get() = EXIT

  override fun play(maze: Maze): Set<GameAction> {
    val sameCellElements =
      maze.sameCellElements(this)
    if (sameCellElements.any { it is Robot } &&
      maze.all().none { it is Food }) {
      return setOf(GameWon)
    }
    return setOf()
  }
}