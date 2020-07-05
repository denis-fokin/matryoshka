package com.ogogon.matryoshka

import com.ogogon.matryoshka.demo.createImageIcon
import java.awt.Color
import java.awt.Component
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.AbstractButton
import javax.swing.JFrame

// See https://docs.oracle.com/javase/tutorial/uiswing/components/button.html

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
        FlowLayout {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            title = "Button Demo"
            FlowLayout {
                JButton {
                    text = "<html><center><b><u>E</u>nable</b><br><font color=#ffffdd>middle button</font>"
                    icon = createImageIcon("images/left.gif")
                    font = font.deriveFont(Font.PLAIN)
                    verticalTextPosition = AbstractButton.CENTER
                    horizontalTextPosition = AbstractButton.LEADING //aka LEFT, for left-to-right locales
                    mnemonic = KeyEvent.VK_D
                    actionCommand = "disable"
                    toolTipText = "Click this button to disable the middle button."
                    onAction {
                        updateButtonsState.invoke(this, it)
                    }
                }
            }
            JButton {
                text = "middle button"
                icon = createImageIcon("images/middle.gif")
                font = font.deriveFont(Font.PLAIN)
                foreground = Color(0xffffdd)
                verticalTextPosition = AbstractButton.BOTTOM
                horizontalTextPosition = AbstractButton.CENTER
                mnemonic = KeyEvent.VK_M
                toolTipText = "This middle button does nothing when you click it."
                onAction {
                    updateButtonsState.invoke(this, it)
                }
            }
            JButton {
                text = "<html><center><b><u>D</u>isable</b><br><font color=#ffffdd>middle button</font>"
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
