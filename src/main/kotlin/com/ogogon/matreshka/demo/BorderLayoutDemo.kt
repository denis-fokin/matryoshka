import com.ogogon.matreshka.BorderLayout
import com.ogogon.matreshka.JButton
import com.ogogon.matreshka.JFrame
import com.ogogon.matreshka.onClick
import java.awt.BorderLayout
import java.awt.Dimension

fun main() {
    JFrame {
        title = "New Frame"
        size = Dimension(200, 200)
        BorderLayout {
            JButton(BorderLayout.NORTH) {
                text = "N"
                onClick {
                    println("Mouse Clicked")
                }
            }
            JButton(BorderLayout.EAST) {
                text = "E"
                onClick {
                    println("Mouse Clicked")
                }
            }
            JButton(BorderLayout.SOUTH) {
                text = "S"
                onClick {
                    println("Mouse Clicked")
                }
            }
            JButton(BorderLayout.WEST) {
                text = "W"
                onClick {
                    println("Mouse Clicked")
                }
            }
        }
    }
}




