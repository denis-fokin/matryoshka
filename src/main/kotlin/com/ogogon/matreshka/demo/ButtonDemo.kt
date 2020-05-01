package com.ogogon.matreshka.demo

import com.ogogon.matreshka.FlowLayout
import com.ogogon.matreshka.JButton
import com.ogogon.matreshka.JFrame
import com.ogogon.matreshka.onAction
import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.*

// See https://docs.oracle.com/javase/tutorial/uiswing/components/button.html

private fun createImageIcon(path: String): ImageIcon? {
    val imgURL = Thread.currentThread().contextClassLoader.getResource(path)
    return if (imgURL != null) {
        ImageIcon(imgURL)
    } else {
        System.err.println("Couldn't find file: $path")
        null
    }
}

private val updateButtonsState: (Component, ActionEvent) -> Unit = { c, e ->
    if (c.parent.componentCount == 3) {
        if ("disable" == e.actionCommand) {
            c.parent.components[0].isEnabled = false
            c.parent.components[1].isEnabled = false
            c.parent.components[2].isEnabled = true

        } else {
            c.parent.components[0].isEnabled = true
            c.parent.components[1].isEnabled = true
            c.parent.components[2].isEnabled = false
        }
    }
}

fun main() {
    JFrame {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        title = "Button Demo"
        FlowLayout {
            JButton {
                text = "Disable middle button"
                icon = createImageIcon("images/left.gif")
                verticalTextPosition = AbstractButton.CENTER
                horizontalTextPosition = AbstractButton.LEADING //aka LEFT, for left-to-right locales
                mnemonic = KeyEvent.VK_D
                actionCommand = "disable"
                toolTipText = "Click this button to disable the middle button."
                onAction {
                    updateButtonsState.invoke(this, it)
                }
            }

            JButton {
                text = "Middle button"
                icon = createImageIcon("images/middle.gif")
                verticalTextPosition = AbstractButton.BOTTOM
                horizontalTextPosition = AbstractButton.CENTER
                mnemonic = KeyEvent.VK_M
                toolTipText = "This middle button does nothing when you click it."
                onAction {
                    updateButtonsState.invoke(this, it)
                }
            }
            JButton {
                text = "Enable middle button"
                icon = createImageIcon("images/right.gif")
                setMnemonic(KeyEvent.VK_E)
                actionCommand = "enable"
                isEnabled = false
                toolTipText = "Click this button to enable the middle button."
                onAction {
                    updateButtonsState.invoke(this, it)
                }
            }
        }
    }
}


