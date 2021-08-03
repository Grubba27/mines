package view

import model.Field
import model.FieldEvent
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.SwingUtilities


private val COLOR_BG_NORMAL = Color(184, 184, 184)
private val COLOR_BG_FLAGGED = Color(180, 17, 247)
private val COLOR_BG_EXPLODED = Color(189, 66, 68)
private val COLOR_BG_GREEN = Color(0, 100, 0)

class ButtonField(private val field: Field) : JButton() {
    init {
        font = font.deriveFont(Font.BOLD)
        background = COLOR_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
        addMouseListener(MouseClickListener(field, { it.open() }, { it.alterField() }))
        field.onEvent(this::changeStyle)
    }

    private fun changeStyle(field: Field, event: FieldEvent) {
        when (event) {
            FieldEvent.EXPLODED -> applyStyleExploded()
            FieldEvent.OPEN -> applyStyleOpened()
            FieldEvent.FLAGGED -> applyStyleFlagged()
            else -> applyStyleStandard()
        }
        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

    private fun applyStyleStandard() {
        background = COLOR_BG_NORMAL
        border = BorderFactory.createBevelBorder(0)
        text = ""
    }

    private fun applyStyleExploded() {
        background = COLOR_BG_EXPLODED
        text = "X"
    }

    private fun applyStyleOpened() {
        background = COLOR_BG_NORMAL
        border = BorderFactory.createLineBorder(Color.gray)
        foreground = when (field.numberOfNeighbors) {
            1 -> COLOR_BG_GREEN
            2 -> Color.BLUE
            3 -> Color.YELLOW
            4, 5, 6 -> Color.RED
            else -> Color.PINK
        }
        text = if (field.numberOfNeighbors > 0) field.numberOfNeighbors.toString() else ""
    }

    fun applyStyleFlagged() {
        background = COLOR_BG_FLAGGED
        foreground = Color.BLACK
        text = "F"
    }
}