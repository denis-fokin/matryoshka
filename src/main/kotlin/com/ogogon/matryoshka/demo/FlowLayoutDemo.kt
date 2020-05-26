import com.ogogon.matryoshka.FlowLayout
import com.ogogon.matryoshka.JButton
import com.ogogon.matryoshka.JFrame
import com.ogogon.matryoshka.onClick
import java.awt.Dimension

fun main() {
    JFrame {
        title = "New Frame"
        size = Dimension(200, 200)
        FlowLayout {
            JButton {
                text = "Button"
                onClick {
                    println("Mouse Clicked")
                }
            }
            JButton {
                text = "Button"
                onClick {
                    println("Mouse Clicked")
                }
            }
        }
    }
}




