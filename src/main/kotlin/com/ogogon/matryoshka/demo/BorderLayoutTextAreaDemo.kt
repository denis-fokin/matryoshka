import com.ogogon.matryoshka.*
import java.awt.BorderLayout
import java.awt.Dimension

fun main() {
    JFrame {
        title = "New Frame"
        size = Dimension(200, 200)
        FlowLayout {
            JTextArea {
                text = "N"
            }
            JTextArea {
                text = "E"
            }
            JTextArea {
                text = "S"
            }
            JTextArea {
                text = "W"
            }
        }
    }
}




