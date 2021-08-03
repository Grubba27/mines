package view

import model.Board
import java.awt.GridLayout
import javax.swing.JPanel

class BoardPanel(board: Board) : JPanel() {
    init {
        layout = GridLayout(board.qtRows, board.qtRows)
        board.forEachMine { mine ->
            val clickable = ButtonField(mine)
            add(clickable)
        }
    }
}