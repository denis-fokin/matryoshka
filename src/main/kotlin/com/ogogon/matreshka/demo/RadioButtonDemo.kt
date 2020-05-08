package com.ogogon.matreshka.demo

import com.ogogon.matreshka.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.event.KeyEvent
import javax.swing.*

object RadioButtonDemoController {
    var pictureLabel: JLabel? = null
    var currentSelection: String? = "Bird"

    fun plug(pl: JLabel) {
        pictureLabel = pl
    }

    fun update() {
        val icon = createImageIcon("images/${currentSelection}.gif")
        pictureLabel?.icon = icon
        pictureLabel?.toolTipText = "Picture of a " + currentSelection?.toLowerCase()
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
        title = "Radio Button Demo"

        BorderLayout {

            GridLayout (BorderLayout.LINE_START) {
                columns = 1
                rows = 0

                JRadioButton {
                    text = "Bird"
                    mnemonic = KeyEvent.VK_B
                    isSelected = true
                    onAction {
                        RadioButtonDemoController.updateCurrentSelection(this.text)
                        RadioButtonDemoController.update()
                    }
                }

                JRadioButton {
                    text = "Cat"
                    mnemonic = KeyEvent.VK_C
                    isSelected = false
                    onAction {
                        RadioButtonDemoController.updateCurrentSelection(this.text)
                        RadioButtonDemoController.update()
                    }                }

                JRadioButton {
                    text = "Dog"
                    mnemonic = KeyEvent.VK_D
                    isSelected = false
                    onAction {
                        RadioButtonDemoController.updateCurrentSelection(this.text)
                        RadioButtonDemoController.update()
                    }
                }

                JRadioButton {
                    text = "Rabbit"
                    mnemonic = KeyEvent.VK_R
                    isSelected = false
                    onAction {
                        RadioButtonDemoController.updateCurrentSelection(this.text)
                        RadioButtonDemoController.update()
                    }
                }

                JRadioButton {
                    text = "Pig"
                    mnemonic = KeyEvent.VK_P
                    isSelected = false
                    onAction {
                        RadioButtonDemoController.updateCurrentSelection(this.text)
                        RadioButtonDemoController.update()
                    }
                }
            }

            JLabel(BorderLayout.CENTER) {
                font = font.deriveFont(Font.ITALIC)
                horizontalAlignment = JLabel.CENTER
                border = BorderFactory.createEmptyBorder(10, 0, 0, 0)
                preferredSize = Dimension(177, 122 + 10)
                apply {
                    RadioButtonDemoController.plug(this)
                    RadioButtonDemoController.update()
                }
            }
        }
    }
}