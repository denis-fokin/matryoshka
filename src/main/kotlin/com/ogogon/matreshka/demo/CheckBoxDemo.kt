package com.ogogon.matreshka.swing.demo

import java.awt.BorderLayout
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import java.awt.event.KeyEvent
import javax.swing.*


/*
* CheckBoxDemo.java requires 16 image files in the images/geek
* directory:
* geek-----.gif, geek-c---.gif, geek--g--.gif, geek---h-.gif, geek----t.gif,
* geek-cg--.gif, ..., geek-cght.gif.
*/
class CheckBoxDemo : JPanel(BorderLayout()), ItemListener {
    var chinButton: JCheckBox
    var glassesButton: JCheckBox
    var hairButton: JCheckBox
    var teethButton: JCheckBox

    /*
     * Four accessory choices provide for 16 different
     * combinations. The image for each combination is
     * contained in a separate image file whose name indicates
     * the accessories. The filenames are "geek-XXXX.gif"
     * where XXXX can be one of the following 16 choices.
     * The "choices" StringBuffer contains the string that
     * indicates the current selection and is used to generate
     * the file name of the image to display.

       ----             //zero accessories

       c---             //one accessory
       -g--
       --h-
       ---t

       cg--             //two accessories
       c-h-
       c--t
       -gh-
       -g-t
       --ht

       -ght             //three accessories
       c-ht
       cg-t
       cgh-

       cght             //all accessories
     */
    var choices: StringBuffer
    var pictureLabel: JLabel

    /** Listens to the check boxes.  */
    override fun itemStateChanged(e: ItemEvent) {
        var index = 0
        var c = '-'
        val source: Any = e.itemSelectable
        if (source === chinButton) {
            index = 0
            c = 'c'
        } else if (source === glassesButton) {
            index = 1
            c = 'g'
        } else if (source === hairButton) {
            index = 2
            c = 'h'
        } else if (source === teethButton) {
            index = 3
            c = 't'
        }

        //Now that we know which button was pushed, find out
        //whether it was selected or deselected.
        if (e.stateChange == ItemEvent.DESELECTED) {
            c = '-'
        }

        //Apply the change to the string.
        choices.setCharAt(index, c)
        updatePicture()
    }

    protected fun updatePicture() {
        //Get the icon corresponding to the image.
        val icon = createImageIcon(
                "images/geek/geek-"
                        + choices.toString()
                        + ".gif")
        pictureLabel.icon = icon
        pictureLabel.toolTipText = choices.toString()
        if (icon == null) {
            pictureLabel.text = "Missing Image"
        } else {
            pictureLabel.text = null
        }
    }

    companion object {
        /** Returns an ImageIcon, or null if the path was invalid.  */
        protected fun createImageIcon(path: String): ImageIcon? {
            val imgURL = Thread.currentThread().contextClassLoader.getResource(path)
            return if (imgURL != null) {
                ImageIcon(imgURL)
            } else {
                System.err.println("Couldn't find file: $path")
                null
            }
        }

        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event-dispatching thread.
         */
        private fun createAndShowGUI() {
            //Create and set up the window.
            val frame = JFrame("CheckBoxDemo")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

            //Create and set up the content pane.
            val newContentPane: JComponent = CheckBoxDemo()
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

        //Create the check boxes.
        chinButton = JCheckBox("Chin")
        chinButton.mnemonic = KeyEvent.VK_C
        chinButton.isSelected = true
        glassesButton = JCheckBox("Glasses")
        glassesButton.mnemonic = KeyEvent.VK_G
        glassesButton.isSelected = true
        hairButton = JCheckBox("Hair")
        hairButton.mnemonic = KeyEvent.VK_H
        hairButton.isSelected = true
        teethButton = JCheckBox("Teeth")
        teethButton.mnemonic = KeyEvent.VK_T
        teethButton.isSelected = true

        //Register a listener for the check boxes.
        chinButton.addItemListener(this)
        glassesButton.addItemListener(this)
        hairButton.addItemListener(this)
        teethButton.addItemListener(this)

        //Indicates what's on the geek.
        choices = StringBuffer("cght")

        //Set up the picture label
        pictureLabel = JLabel()
        pictureLabel.font = pictureLabel.font.deriveFont(Font.ITALIC)
        updatePicture()

        //Put the check boxes in a column in a panel
        val checkPanel = JPanel(GridLayout(0, 1))
        checkPanel.add(chinButton)
        checkPanel.add(glassesButton)
        checkPanel.add(hairButton)
        checkPanel.add(teethButton)
        add(checkPanel, BorderLayout.LINE_START)
        add(pictureLabel, BorderLayout.CENTER)
        border = BorderFactory.createEmptyBorder(20, 20, 20, 20)
    }
}