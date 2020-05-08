package com.ogogon.matreshka.demo

import com.ogogon.matreshka.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.KeyEvent
import javax.swing.*

val CAT_COMMAND = "CAT_COMAND"

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


/*
* RadioButtonDemo.java requires these files:
*   images/Bird.gif
*   images/Cat.gif
*   images/Dog.gif
*   images/Rabbit.gif
*   images/Pig.gif
*/
//class RadioButtonDemo : JPanel(BorderLayout()), ActionListener {
//    var picture: JLabel
//
//    /** Listens to the radio buttons.  */
//    override fun actionPerformed(e: ActionEvent) {
//        picture.preferredSize = Dimension(177, 122)
//        picture.icon = createImageIcon(
//            "images/"
//                    + e.actionCommand
//                    + ".gif"
//        )
//    }
//
//    companion object {
//        var birdString = "Bird"
//        var catString = "Cat"
//        var dogString = "Dog"
//        var rabbitString = "Rabbit"
//        var pigString = "Pig"
//
//
//        /**
//         * Create the GUI and show it.  For thread safety,
//         * this method should be invoked from the
//         * event-dispatching thread.
//         */
//        private fun createAndShowGUI() {
//            //Create and set up the window.
//            val frame = JFrame("RadioButtonDemo")
//            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
//
//            //Create and set up the content pane.
//            val newContentPane: JComponent = RadioButtonDemo()
//            newContentPane.isOpaque = true //content panes must be opaque
//            frame.contentPane = newContentPane
//
//            //Display the window.
//            frame.pack()
//            frame.isVisible = true
//        }
//
//        @JvmStatic
//        fun main(args: Array<String>) {
//            //Schedule a job for the event-dispatching thread:
//            //creating and showing this application's GUI.
//            SwingUtilities.invokeLater { createAndShowGUI() }
//        }
//    }
//
//    init {
//
//        //Create the radio buttons.
//
//        //Group the radio buttons.
//        val group = ButtonGroup()
//        group.add(birdButton)
//        group.add(catButton)
//        group.add(dogButton)
//        group.add(rabbitButton)
//        group.add(pigButton)
//
//        //Register a listener for the radio buttons.
//        birdButton.addActionListener(this)
//        catButton.addActionListener(this)
//        dogButton.addActionListener(this)
//        rabbitButton.addActionListener(this)
//        pigButton.addActionListener(this)
//
//        //Set up the picture label.
//        picture = JLabel(
//            createImageIcon(
//                "images/"
//                        + birdString
//                        + ".gif"
//            )
//        )
//
//        //The preferred size is hard-coded to be the width of the
//        //widest image and the height of the tallest image.
//        //A real program would compute this.
//        picture.preferredSize = Dimension(177, 122)
//
//
//        //Put the radio buttons in a column in a panel.
//        val radioPanel = JPanel(GridLayout(0, 1))
//        radioPanel.add(birdButton)
//        radioPanel.add(catButton)
//        radioPanel.add(dogButton)
//        radioPanel.add(rabbitButton)
//        radioPanel.add(pigButton)
//        add(radioPanel, BorderLayout.LINE_START)
//        add(picture, BorderLayout.CENTER)
//        border = BorderFactory.createEmptyBorder(20, 20, 20, 20)
//    }
//}