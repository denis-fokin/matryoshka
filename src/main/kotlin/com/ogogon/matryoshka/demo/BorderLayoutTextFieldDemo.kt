import com.ogogon.matryoshka.*
import java.awt.BorderLayout
import java.awt.Dimension

fun main() {
    JFrame {
        title = "New Frame"
        size = Dimension(200, 200)
        FlowLayout {
            JTextField {
                text = "N"
            }
            JTextField {
                text = "E"
            }
            JTextField {
                text = "S"
            }
            JTextField {
                text = "W"
            }
        }
    }
}




