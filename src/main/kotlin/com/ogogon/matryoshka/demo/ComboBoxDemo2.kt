package com.ogogon.matryoshka.demo

import com.ogogon.matryoshka.*
import java.awt.*
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.*

private val patternExamples = arrayOf(
        "dd MMMMM yyyy",
        "dd.MM.yy",
        "MM/dd/yy",
        "yyyy.MM.dd G 'at' hh:mm:ss z",
        "EEE, MMM d, ''yy",
        "h:mm a",
        "H:mm:ss:SSS",
        "K:mm a,z",
        "yyyy.MMMMM.dd GGG hh:mm aaa"
)

var currentPattern = patternExamples[0]

object ComboboxDemoController2 {
    private lateinit var result: JLabel
    private val formatter
        get () = SimpleDateFormat(currentPattern)
    val dateString
        get () = formatter.format(Date())

    fun plug(pl: JLabel) {
        result = pl
    }

    fun update() {
        try {
            result.foreground = Color.black
            result.text = dateString
        } catch (iae: IllegalArgumentException) {
            result.foreground = Color.red
            result.text = "Error: " + iae.message
        }
    }

    fun updateCurrentSelection(s: String) {
        currentPattern = s
    }

}

fun main() {

    JFrame {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        title = "ComboBox Demo 2"


        // Content pane
        BoxLayout(axis = BoxLayout.PAGE_AXIS) {
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            // Pattern pane
            BoxLayout(axis = BoxLayout.PAGE_AXIS) {
                alignmentX = Component.LEFT_ALIGNMENT

                JLabel {
                    text = "Enter the pattern string or"
                }

                JLabel {
                    text = "select one from the list:"
                }

                JComboBox {
                    isEditable = true
                    alignmentX = Component.LEFT_ALIGNMENT
                    model = DefaultComboBoxModel(patternExamples)
                    onAction {
                        val cb = it.source as JComboBox<*>
                        ComboboxDemoController2.updateCurrentSelection(cb.selectedItem as String)
                        ComboboxDemoController2.update()
                    }
                }
            }

           // add(Box.createRigidArea(Dimension(0, 10)))

            // Result pane
            GridLayout {
                columns = 1
                rows = 0

                alignmentX = Component.LEFT_ALIGNMENT

                //ResultLabel label
                JLabel {
                    text = "Current Date/Time"
                    horizontalAlignment = JLabel.LEADING
                }

                //Result label
                JLabel {
                    foreground = Color.BLACK
                    border = BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.black),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5))
                    apply {
                        ComboboxDemoController2.plug(this)
                        ComboboxDemoController2.update()
                    }
                }
            }
        }
    }
}

