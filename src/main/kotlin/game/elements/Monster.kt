package game.elements

import game.*
import game.Move.*
import matrix.Position

class Monster : MobileElement() {

    override val symbol: Char
        get() = MONSTER

    override fun playTurn(maze: Maze): Set<GameAction> {
        val sameCellElements = maze.sameCellElements(this)
        val robot = sameCellElements.find { it is Robot } ?: return setOf()
        return setOf(DestroyAction(robot))
    }

    override fun makeMove(currentPosition: Position, move: Move, maze: Maze): Position {
        val directions = listOf(UP, RIGHT, DOWN, LEFT)
        return directions
            .map { currentPosition.applyMove(it) }
            .filter { maze.isPassable(it) }
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?: currentPosition
    }
}