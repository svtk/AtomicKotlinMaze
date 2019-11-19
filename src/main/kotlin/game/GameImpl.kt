package game

import game.Game.GameState.*
import game.elements.Robot

class GameImpl(representation: String) : Game {

  private var turns = 0

  private var _state = ACTIVE
  override val state get() = _state

  override val maze: MutableMaze =
    createMaze(representation)

  override fun playMove(move: Move) {
    turns++
    val allElements = maze.all()
    val actions = allElements
      .filterIsInstance<MobileElement>()
      .mapNotNull { element ->
        val newPosition = element.move(move, maze)
        if (newPosition != null) {
          MoveAction(element, newPosition)
        }
        else null
      }
    applyActions(actions)
  }

  override fun playTurn() {
    val actions = maze.all()
      .flatMap { element ->
        element.play(maze)
      }
    applyActions(actions)
  }

  override fun score(): Int {
    val robot = maze.all()
      .find { it is Robot }
      ?: return 0
    return (robot as Robot).score() * 100 - turns
  }

  private fun applyActions(
    actions: Collection<GameAction>
  ) {
    for (action in actions) {
      when (action) {
        is DestroyAction ->
          maze.remove(action.element)
        is MoveAction -> {
          maze.remove(action.element)
          maze.add(action.element, action.newCell)
        }
        is CreateAction ->
          maze.add(action.element,
            action.cell)
        is GameOver ->
          _state = LOST
        is GameWon ->
          _state = WON
      }
    }
  }
}