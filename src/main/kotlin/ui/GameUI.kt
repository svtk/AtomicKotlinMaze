package ui

import game.*
import game.Game.GameState.*
import matrix.Cell
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer
import javax.swing.WindowConstants

fun playGame(game: Game) {
  with(JFrame()) {
    title = GAME_NAME
    defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    setSize(
      game.maze.width * TILE_SIZE + (game.maze.width + 1) * TILES_MARGIN,
      game.maze.height * TILE_SIZE + (game.maze.width + 1) * TILES_MARGIN + 30
    )
    isResizable = false

    add(GameUI(game))

    setLocationRelativeTo(null)
    isVisible = true
  }
}

class GameUI(val game: Game) : JPanel() {
  private var processingRequest = false
  init {
    isFocusable = true
    addKeyListener(object : KeyAdapter() {
      override fun keyPressed(e: KeyEvent) {
        if (processingRequest) {
          // ignore all the following requests until
          // the current one is processed
          return
        }
        if (!game.state.isActive) return
        processingRequest = true
        val move = when (e.keyCode) {
          KeyEvent.VK_LEFT -> Move.LEFT
          KeyEvent.VK_RIGHT -> Move.RIGHT
          KeyEvent.VK_DOWN -> Move.DOWN
          KeyEvent.VK_UP -> Move.UP
          KeyEvent.VK_SPACE -> Move.WAIT
          else -> null
        }
        if (move != null) {
          game.playMove(move)
          repaint()

          Timer(100) {
            game.playTurn()
            repaint()
            processingRequest = false
          }.run {
            isRepeats = false
            start()
          }
        }
      }
    })
  }

  override fun paint(g: Graphics) {
    super.paint(g)
    g.color = BACKGROUND_COLOR
    g.fillRect(0, 0, this.size.width, this.size.height)
    g as Graphics2D
    for (x in 0 until game.maze.width) {
      for (y in 0 until game.maze.height) {
        drawTile(
          g,
          game.maze.allIn(Cell(x, y)).lastOrNull()?.symbol ?: ' ',
          x, y
        )
      }
    }
    paintResults(g)
  }

  private fun offsetCoors(arg: Int): Int {
    return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN
  }

  private fun paintResults(g: Graphics2D) {
    if (!game.state.isActive) {
      g.color = Color(255, 255, 255, 225)
      g.fillRect(0, 0, width, height)

      g.color = RESULT_FONT_COLOR
      g.font = Font(FONT_NAME, Font.BOLD, 36)
      if (game.state == WON) {
        g.drawString("Score: ${game.score()}", 15, 50)
      }
      if (game.state == LOST) {
        g.drawString("Game over!", 15, 50)
      }
    }
  }

  private fun drawTile(g: Graphics2D, value: Char, x: Int, y: Int) {
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE)

    val xOffset = offsetCoors(x)
    val yOffset = offsetCoors(y)
    g.color = getTileColor(value)
    g.fillRoundRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14)

    g.color = FONT_COLOR
    val size = 24
    val font = Font(FONT_NAME, Font.BOLD, size)
    g.font = font

    val s = value.toString()
    val fm = getFontMetrics(font)

    val w = fm.stringWidth(s)
    val h = -fm.getLineMetrics(s, g).baselineOffsets[2].toInt()

    g.drawString(
      s,
      xOffset + (TILE_SIZE - w) / 2,
      yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2
    )
  }
}

private const val GAME_NAME = "Maze"

private const val FONT_NAME = "Arial"

private const val TILE_SIZE = 32

private const val TILES_MARGIN = 8

private val FONT_COLOR = Color(0x303030)

private val RESULT_FONT_COLOR = Color(78, 139, 202)

private val BACKGROUND_COLOR: Color = Color(0xC0C0C0)

fun getTileColor(char: Char): Color? = when (char) {
  WALL -> Color(0x606060)
  FOOD -> Color(0xf2ec7e)
  MONSTER -> Color(0xd495f0)
  ROBOT -> Color(0x0066ff)
  EXIT -> Color(0x42f560)
  in BOMB_RANGE -> Color(0xe36b6b)
  in TELEPORT_RANGE -> Color(0x39b85b)
  else -> Color(0xA0A0A0)
}