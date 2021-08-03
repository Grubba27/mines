package model

enum class FieldEvent {
    OPEN,
    FLAGGED,
    UNFLAGGED,
    EXPLODED,
    RESTART
}

data class Field(val row: Int, val column: Int) {
    private val neighbors = ArrayList<Field>()
    private val callbacks = ArrayList<(Field, FieldEvent) -> Unit>()

    var flagged = false
    var open = false
    var mined = false


    val unflagged get() = !flagged
    val closed get() = !open
    val safe get() = !mined

    val isWon get() = safe && open || mined && flagged
    val numberOfNeighbors get() = neighbors.filter { it.mined }.size
    val isNeighborhoodSafe get() = neighbors.map { it.safe }.reduce { acc, safe -> acc && safe }


    fun addNeighbor(neighbor: Field) {
        neighbors.add(neighbor)
    }

    fun onEvent(callback: (Field, FieldEvent) -> Unit) {
        callbacks.add(callback)
    }

    fun open() {
        if (closed) {
            open = true
            if (mined) {
                callbacks.forEach { it(this, FieldEvent.EXPLODED) }
            } else {
                callbacks.forEach { it(this, FieldEvent.OPEN) }
                neighbors.filter { it.closed && it.safe && isNeighborhoodSafe }.forEach { it.open() }
            }
        }
    }

    fun alterField() {
        if (closed) {
            flagged = !flagged
            val event = if (flagged) FieldEvent.FLAGGED else FieldEvent.UNFLAGGED
            callbacks.forEach { it(this, event) }
        }
    }

    fun mining() {
        mined = true
    }

    fun restart() {
        open = false
        mined = false
        flagged = false
        callbacks.forEach { it(this, FieldEvent.RESTART) }
    }
}