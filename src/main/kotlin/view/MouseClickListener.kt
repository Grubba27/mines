package view

import model.Field
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MouseClickListener(
    private val field: Field,
    private val onLeftClick: (Field) -> Unit,
    private val onRightClick: (Field) -> Unit,
) : MouseListener {
    override fun mousePressed(e: MouseEvent?) {
        when (e?.button) {
            MouseEvent.BUTTON1 -> onLeftClick(field)
            MouseEvent.BUTTON3 -> onRightClick(field)
        }
    }

    override fun mouseClicked(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseEntered(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseExited(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseReleased(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}