package matrix

import org.junit.Assert
import org.junit.Test

class TestMatrix {
  @Test
  fun testAdditionOfOneElement() {
    val matrix = MatrixImpl<Int>(5, 5)
    val position = Cell(2, 3)
    matrix.add(42, position)
    Assert.assertEquals(
      "Element wasn't added:",
      setOf(42), matrix.allAt(position)
    )
    assertConsistentState(matrix)
  }

  @Test
  fun testAdditionOfSeveralElements() {
    val matrix = MatrixImpl<Int>(5, 5)
    val position = Cell(2, 3)
    matrix.add(42, position)
    matrix.add(50, position)
    Assert.assertEquals(
      "Element wasn't added:",
      setOf(42, 50), matrix.allAt(position)
    )
    assertConsistentState(matrix)
  }

  @Test
  fun testPosition() {
    val matrix = MatrixImpl<Int>(5, 5)
    val position = Cell(2, 3)
    matrix.add(42, position)
    Assert.assertEquals(
      "Element wasn't added:",
      position, matrix.position(42)
    )
    assertConsistentState(matrix)
  }


  @Test
  fun testAll() {
    val matrix = MatrixImpl<Int>(5, 5)
    matrix.add(1, Cell(1, 2))
    matrix.add(2, Cell(3, 4))
    val position = Cell(2, 3)
    matrix.add(3, position)
    matrix.add(4, position)
    Assert.assertEquals(
      "Element wasn't added:",
      setOf(1, 2, 3, 4), matrix.all()
    )
    assertConsistentState(matrix)
  }

  @Test(expected = IllegalArgumentException::class)
  fun testPresentElement() {
    val matrix = MatrixImpl<Int>(5, 5)
    matrix.add(1, Cell(1, 1))
    matrix.add(1, Cell(2, 3))
  }

  @Test
  fun testRemoval() {
    val matrix = MatrixImpl<Int>(5, 5)
    val position = Cell(2, 3)
    matrix.add(42, position)
    matrix.add(50, position)
    matrix.remove(50)
    Assert.assertEquals(
      "Element wasn't removed:",
      setOf(42), matrix.allAt(position)
    )
    assertConsistentState(matrix)
  }

  @Test
  fun testMatrix() {
    fun values(i: Int, j: Int) =
      setOf("$i-$j-1", "$i-$j-2", "$i-$j-3")

    val matrix = MatrixImpl<String>(5, 5)
    for (i in 0..4) {
      for (j in 0..4) {
        values(i, j).forEach {
          matrix.add(it, Cell(i, j))
        }
      }
    }
    for (i in 0..4) {
      for (j in 0..4) {
        Assert.assertEquals(
          "Wrong values for Position($i, $j):",
          values(i, j),
          matrix.allAt(Cell(i, j))
        )
      }
    }
    assertConsistentState(matrix)
  }

  private fun <E> assertConsistentState(matrix: Matrix<E>) {
    for (x in 0 until matrix.width) {
      for (y in 0 until matrix.height) {
        val position = Cell(x, y)
        val elements = matrix.allAt(position)
        elements.forEach { element ->
          val storedPosition = matrix.position(element)
          if (storedPosition != position) {
            throw AssertionError(
              "Inconsistent stored positions for element $element: " +
                "$storedPosition != $position"
            )
          }
        }
      }
    }
    for (element in matrix.all()) {
      val position = matrix.position(element)
        ?: throw AssertionError(
          "Inconsistent stored positions for element $element: " +
            "no position for $element"
        )
      val elements = matrix.allAt(position)
      if (!elements.contains(element)) {
        throw AssertionError(
          "Inconsistent stored positions for element $element: " +
            "no such element at $position"
        )
      }
    }
  }
}