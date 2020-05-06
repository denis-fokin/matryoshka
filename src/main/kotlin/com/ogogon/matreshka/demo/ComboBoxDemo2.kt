package com.ogogon.matreshka.demo

import com.ogogon.matreshka.*
import java.awt.*
import javax.swing.*

private val patternExamples = arrayOf<String?>(
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


        // Content pane
        BoxLayout(axis = BoxLayout.PAGE_AXIS) {
            //todo
            //border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            // Pattern pane
            BoxLayout(axis = BoxLayout.PAGE_AXIS) {
                //todo
                //panel.alignmentX = Component.LEFT_ALIGNMENT
                JLabel {
                    text = "Enter the pattern string or"
                }
                JLabel {
                    text = "select one from the list:"
                }


                JComboBox {
                    isEditable = true
                    alignmentX = Component.LEFT_ALIGNMENT
                    onAction {
                       /* val cb = e.source as JComboBox<*>
                        val newSelection = cb.selectedItem as String
                        currentPattern = newSelection
                        val today = Date()
                        val formatter = SimpleDateFormat(currentPattern)
                        try {
                            val dateString = formatter.format(today)
                            result.foreground = Color.black
                            result.text = dateString
                        } catch (iae: IllegalArgumentException) {
                            result.foreground = Color.red
                            result.text = "Error: " + iae.message
                        }*/
                    }
                }
            }

           // add(Box.createRigidArea(Dimension(0, 10)))

            // Result pane
            GridLayout {
                columns = 1
                rows = 0

                //resultPanel.alignmentX = Component.LEFT_ALIGNMENT

                //ResultLabel label
                JLabel {
                    text = "Current Date/Time"
                    horizontalAlignment = JLabel.LEADING
                }
                //Result label
                JLabel {
                    text = " "
                    foreground = Color.BLACK
                    border = BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.black),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5))
                }

            }
        }
    }
}

