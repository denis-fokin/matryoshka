package com.ogogon.matreshka.demo

import com.ogogon.matreshka.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import javax.swing.*

fun main() {
    JFrame {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        title = "Radio Button Demo"
        BorderLayout {
            JButton {
                text = "Disable middle button"
                icon = createImageIcon("images/left.gif")
                verticalTextPosition = AbstractButton.CENTER
                horizontalTextPosition = AbstractButton.LEADING //aka LEFT, for left-to-right locales
                mnemonic = KeyEvent.VK_D
                actionCommand = "disable"
                toolTipText = "Click this button to disable the middle button."
                onAction {}
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
class RadioButtonDemo : JPanel(BorderLayout()), ActionListener {
    var picture: JLabel

    /** Listens to the radio buttons.  */
    override fun actionPerformed(e: ActionEvent) {
        picture.icon = createImageIcon(
            "images/"
                    + e.actionCommand
                    + ".gif"
        )
    }

    companion object {
        var birdString = "Bird"
        var catString = "Cat"
        var dogString = "Dog"
        var rabbitString = "Rabbit"
        var pigString = "Pig"


        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event-dispatching thread.
         */
        private fun createAndShowGUI() {
            //Create and set up the window.
            val frame = JFrame("RadioButtonDemo")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

            //Create and set up the content pane.
            val newContentPane: JComponent = RadioButtonDemo()
            newContentPane.isOpaque = true //content panes must be opaque
            frame.contentPane = newContentPane

            //Display the window.
            frame.pack()
            frame.isVisible = true
        }

        @JvmStatic
        fun main(args: Array<String>) {
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            SwingUtilities.invokeLater { createAndShowGUI() }
        }
    }

    init {

        //Create the radio buttons.
        val birdButton = JRadioButton(birdString)
        birdButton.mnemonic = KeyEvent.VK_B
        birdButton.actionCommand = birdString
        birdButton.isSelected = true
        val catButton = JRadioButton(catString)
        catButton.mnemonic = KeyEvent.VK_C
        catButton.actionCommand = catString
        val dogButton = JRadioButton(dogString)
        dogButton.mnemonic = KeyEvent.VK_D
        dogButton.actionCommand = dogString
        val rabbitButton = JRadioButton(rabbitString)
        rabbitButton.mnemonic = KeyEvent.VK_R
        rabbitButton.actionCommand = rabbitString
        val pigButton = JRadioButton(pigString)
        pigButton.mnemonic = KeyEvent.VK_P
        pigButton.actionCommand = pigString

        //Group the radio buttons.
        val group = ButtonGroup()
        group.add(birdButton)
        group.add(catButton)
        group.add(dogButton)
        group.add(rabbitButton)
        group.add(pigButton)

        //Register a listener for the radio buttons.
        birdButton.addActionListener(this)
        catButton.addActionListener(this)
        dogButton.addActionListener(this)
        rabbitButton.addActionListener(this)
        pigButton.addActionListener(this)

        //Set up the picture label.
        picture = JLabel(
            createImageIcon(
                "images/"
                        + birdString
                        + ".gif"
            )
        )

        //The preferred size is hard-coded to be the width of the
        //widest image and the height of the tallest image.
        //A real program would compute this.
        picture.preferredSize = Dimension(177, 122)


        //Put the radio buttons in a column in a panel.
        val radioPanel = JPanel(GridLayout(0, 1))
        radioPanel.add(birdButton)
        radioPanel.add(catButton)
        radioPanel.add(dogButton)
        radioPanel.add(rabbitButton)
        radioPanel.add(pigButton)
        add(radioPanel, BorderLayout.LINE_START)
        add(picture, BorderLayout.CENTER)
        border = BorderFactory.createEmptyBorder(20, 20, 20, 20)
    }
}