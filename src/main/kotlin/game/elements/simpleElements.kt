package game.elements

import game.EXIT
import game.FOOD
import game.StaticElement
import game.WALL

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
}