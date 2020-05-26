package com.ogogon.matryoshka.demo

import com.ogogon.matryoshka.*
import java.awt.BorderLayout
import java.awt.event.ItemEvent
import java.awt.event.KeyEvent
import javax.swing.*

object CheckBoxDemoController {

    // This is the model
    var choices = StringBuffer("cght")

    var pictureLabel:JLabel? = null

    fun plug(label: JLabel) {
        pictureLabel = label
    }

    fun update (e: ItemEvent) {
        when ((e.itemSelectable as JCheckBox).text) {
            "Chin" -> Pair(0, 'c')
            "Glasses" -> Pair(1, 'g')
            "Hair" -> Pair(2, 'h')
            "Teeth" -> Pair(3, 't')
            else -> Pair(0, '-')
        }.let { (index, c) ->
            if (e.stateChange == ItemEvent.DESELECTED) {
                choices.setCharAt(index, '-')
            } else {
                choices.setCharAt(index, c)
            }
        }
    }

    fun update () {
        pictureLabel?.let {
            it.icon = createImageIcon(
                "images/geek/geek-"
                        + choices.toString()
                        + ".gif"
            )
            it.toolTipText = choices.toString()
            if (it.icon == null) {
                it.text = "Missing Image"
            } else {
                it.text = null
            }
        }
    }
}

fun main() {

    JFrame {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        title = "Checkbox Demo"
        BorderLayout {
            GridLayout (BorderLayout.LINE_START) {
                columns = 1
                rows = 0
                JCheckBox {
                    text = "Chin"
                    mnemonic = KeyEvent.VK_C
                    isSelected = true
                    onItemStateChange {
                        CheckBoxDemoController.update(it)
                        CheckBoxDemoController.update()
                    }
                }
                JCheckBox {
                    text = "Glasses"
                    mnemonic = KeyEvent.VK_G
                    isSelected = true
                    onItemStateChange {
                        CheckBoxDemoController.update(it)
                        CheckBoxDemoController.update()
                    }
                }
                JCheckBox {
                    text = "Hair"
                    mnemonic = KeyEvent.VK_H
                    isSelected = true
                    onItemStateChange {
                        CheckBoxDemoController.update(it)
                        CheckBoxDemoController.update()
                    }
                }
                JCheckBox {
                    text = "Teeth"
                    mnemonic = KeyEvent.VK_T
                    isSelected = true
                    onItemStateChange {
                        CheckBoxDemoController.update(it)
                        CheckBoxDemoController.update()
                    }
                }

            }
            JLabel(BorderLayout.CENTER) {
                icon = createImageIcon(
                    "images/geek/geek-"
                            + CheckBoxDemoController.choices.toString()
                            + ".gif"
                )
                toolTipText = CheckBoxDemoController.choices.toString()
                apply {
                    CheckBoxDemoController.plug(this)
                    if (icon == null) {
                        text = "Missing Image"
                    } else {
                        text = null
                    }
                }
            }
        }
    }
}