package com.ogogon.matryoshka.demo

import com.ogogon.matryoshka.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import javax.swing.*

object ComboboxDemoController {
    // This is the model
    val petStrings = arrayOf<String?>("Bird", "Cat", "Dog", "Rabbit", "Pig")
    var pictureLabel: JLabel? = null
    var currentSelection: String? = null

    fun plug(pl: JLabel) {
        pictureLabel = pl
    }

    fun update() {
        val icon = createImageIcon("images/$currentSelection.gif")
        pictureLabel?.icon = icon
        pictureLabel?.toolTipText = "A drawing of a " + currentSelection?.toLowerCase()
        if (icon != null) {
            pictureLabel?.text = null
        } else {
            pictureLabel?.text = "Image not found"
        }
    }

    fun updateCurrentSelection(s: String) {
        currentSelection = s
    }

}

fun main() {

    JFrame {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        title = "ComboBox Demo"

        BorderLayout(BorderLayout.PAGE_START) {
            border = BorderFactory.createEmptyBorder(20, 20, 20, 20)
            JComboBox {
                model = DefaultComboBoxModel(ComboboxDemoController.petStrings)
                selectedIndex = 4
                onAction {
                    val cb = it.source as JComboBox<*>
                    val petName = cb.selectedItem as String
                    ComboboxDemoController.updateCurrentSelection(petName)
                    ComboboxDemoController.update()
                }
                apply {
                    ComboboxDemoController.updateCurrentSelection(selectedItem as String)
                }
            }
            JLabel(BorderLayout.PAGE_END) {
                font = font.deriveFont(Font.ITALIC)
                horizontalAlignment = JLabel.CENTER
                border = BorderFactory.createEmptyBorder(10, 0, 0, 0)
                preferredSize = Dimension(177, 122 + 10)
                apply {
                    ComboboxDemoController.plug(this)
                    ComboboxDemoController.update()
                }
            }
        }
    }
}