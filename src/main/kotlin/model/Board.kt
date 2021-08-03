package model

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

    }

    private fun placeNeighbors() {

    }

    private fun placeMines() {

    }

    fun forEachMine(callback: (Field) -> Unit) {
        fields.forEach { row -> row.forEach(callback) }
    }

}