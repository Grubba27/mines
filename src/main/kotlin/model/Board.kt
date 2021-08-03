package model

import java.util.*
import kotlin.collections.ArrayList

enum class BoardEvent { WIN, LOST }

class Board(val qtRows: Int, val qtCols: Int, private val qtMines: Int) {
    private val fields = ArrayList<ArrayList<Field>>()
    private val callbacks = ArrayList<(BoardEvent) -> Unit>()

    init {
        generateFields()
        placeNeighbors()
        placeMines()
    }

    private fun generateFields() {
        for (row in 0 until qtRows) {
            fields.add(ArrayList())
            for (column in 0 until qtCols) {
                val newField = Field(row, column)
                newField.onEvent(this::verifyVictory)
                fields[row].add(newField)
            }
        }
    }

    private fun placeNeighbors() {
        forEachMine { placeNeighbors(it) }

    }

    private fun placeNeighbors(field: Field) {
        val (row, col) = field
        val rows = arrayOf(row - 1, row, row + 1)
        val cols = arrayOf(col - 1, col, col + 1)

        rows.forEach { r ->
            cols.forEach { c ->
                val now = fields.getOrNull(r)?.getOrNull(c)
                now?.takeIf { field != it }?.let { field.addNeighbor(it) }
            }
        }
    }

    private fun placeMines() {
        val randomGenerator = Random()

        var selectedRow = -1
        var selectedCol = -1
        var numberOfMines = 0

        while (numberOfMines < this.qtMines) {
            selectedRow = randomGenerator.nextInt(qtRows)
            selectedCol = randomGenerator.nextInt(qtCols)
            val selectedField = fields[selectedRow][selectedCol]
            if (selectedField.safe) {
                selectedField.mining()
                numberOfMines++
            }
        }
    }

    private fun hasWon(): Boolean {
        var hasWon = true
        forEachMine { if (!it.isWon) hasWon = false }
        return hasWon
    }

    private fun verifyVictory(field: Field, event: FieldEvent) {
        if (event == FieldEvent.EXPLODED) {
            callbacks.forEach { it(BoardEvent.LOST) }
        } else if (hasWon()) {
            callbacks.forEach { it(BoardEvent.WIN) }
        }
    }


    fun forEachMine(callback: (Field) -> Unit) {
        fields.forEach { row -> row.forEach(callback) }
    }

    fun onEvent(callback: (BoardEvent) -> Unit) {
        callbacks.add(callback)
    }

    fun restart() {
        forEachMine { it.restart() }
        placeMines()
    }
}