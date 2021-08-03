package view

import model.Board
import model.BoardEvent
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    MainScreen()
}

class MainScreen : JFrame() {
    private val board = Board(qtCols = 20, qtRows = 30, qtMines = 50)
    private val boardPanel = BoardPanel(board)

    init {
        board.onEvent(this::showResult)
        add(boardPanel)
        setSize(690, 538)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Mined Field"
        isVisible = true
    }

    private fun showResult(event: BoardEvent) {
        SwingUtilities.invokeLater {
            val msg = when (event) {
                BoardEvent.WIN -> "You Won"
                BoardEvent.LOST -> "You Lost"
            }
            JOptionPane.showMessageDialog(this, msg)
            board.restart()
            boardPanel.repaint()
            boardPanel.revalidate()
        }
    }
}