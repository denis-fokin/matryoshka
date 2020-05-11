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

object ButtonTextController {
    lateinit var b1:JButton
    lateinit var b2:JButton
    lateinit var b3:JButton
    fun update (a : String) {

        if ("disable" == a) {
            b2.isEnabled = false;
            b1.isEnabled = false;
            b3.isEnabled = true;
        } else {
            b2.isEnabled = true;
            b1.isEnabled = true;
            b3.isEnabled = false;
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
                    ButtonTextController.update(it.actionCommand)
                }
                apply{
                    ButtonTextController.b1 = this
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
                   ButtonTextController.update(it.actionCommand)
                }
                apply{
                    ButtonTextController.b2 = this
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
                    ButtonTextController.update(it.actionCommand)
                }
                apply {
                    ButtonTextController.b3 = this
                }
            }
        }
    }
}


