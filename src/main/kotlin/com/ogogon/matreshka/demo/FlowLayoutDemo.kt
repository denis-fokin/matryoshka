import com.ogogon.matreshka.FlowLayout
import com.ogogon.matreshka.JButton
import com.ogogon.matreshka.JFrame
import com.ogogon.matreshka.onClick
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




