package game

import game.elements.Exit
import game.elements.Food
import game.elements.Robot

class GameImpl(representation: String) : Game {

  private var turns = 0

  override val maze: Maze =
    createMaze(representation)

  override fun playMove(move: Move) {
    turns++
    val allElements = maze.all()
    val actions = allElements
      .filterIsInstance<MobileElement>()
      .mapNotNull { element ->
        maze.position(element)?.let { position ->
          MoveAction(element, element.makeMove(position, move, maze))
        }
      }
    applyActions(actions)
  }

  override fun playTurn() {
    val allElements = maze.all()
    val (static, mobile) =
      allElements.partition { it is StaticElement }
    // First handle static elements, then mobile
    val actions = (static + mobile)
      .flatMap { element ->
        element.playTurn(maze)
      }
    applyActions(actions)
  }


  override fun gameOver(): Boolean {
    return maze.all().none { it is Robot }
  }

  override fun hasWon(): Boolean {
    val all = maze.all()
    if (all.any { it is Food }) return false
    val robot = all.find { it is Robot } ?: return false
    val exit = all.find { it is Exit } ?: return false
    return maze.position(robot) == maze.position(exit)
  }

  override fun score(): Int {
    val robot = maze.all().filterIsInstance<Robot>().singleOrNull() ?: return 0
    return robot.score() * 100 - turns
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
          maze.add(action.element, action.newPosition)
        }
        is CreateAction ->
          maze.add(action.element,
            action.position)
      }
    }
  }
}