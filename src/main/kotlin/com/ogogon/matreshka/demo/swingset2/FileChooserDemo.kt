package com.ogogon.matreshka.demo.swingset2

import java.awt.*
import java.awt.event.ActionEvent
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.io.File
import javax.swing.*
import javax.swing.border.BevelBorder
import javax.swing.filechooser.FileFilter
import javax.swing.filechooser.FileNameExtensionFilter

class FileChooserDemo(swingset: SwingSet2?) : DemoModule(swingset, "FileChooserDemo", "toolbar/JFileChooser.gif") {
    var theImage: JLabel? = null
    var jpgIcon: Icon? = null
    var gifIcon: Icon? = null
    fun createFileChooserDemo() {
        theImage = JLabel("")
        jpgIcon = createImageIcon("filechooser/jpgIcon.jpg", "jpg image")
        gifIcon = createImageIcon("filechooser/gifIcon.gif", "gif image")
        val demoPanel = demoPanel
        demoPanel!!.layout = BoxLayout(demoPanel, BoxLayout.Y_AXIS)
        val innerPanel = JPanel()
        innerPanel.layout = BoxLayout(innerPanel, BoxLayout.X_AXIS)
        demoPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP20))
        demoPanel.add(innerPanel)
        demoPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP20))
        innerPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP20))

        // Create a panel to hold buttons
        val buttonPanel: JPanel = object : JPanel() {
            override fun getMaximumSize(): Dimension {
                return Dimension(preferredSize.width, super.getMaximumSize().height)
            }
        }
        buttonPanel.layout = BoxLayout(buttonPanel, BoxLayout.Y_AXIS)
        buttonPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        buttonPanel.add(createPlainFileChooserButton())
        buttonPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        buttonPanel.add(createPreviewFileChooserButton())
        buttonPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        buttonPanel.add(createCustomFileChooserButton())
        buttonPanel.add(Box.createVerticalGlue())

        // Create a panel to hold the image
        val imagePanel = JPanel()
        imagePanel.layout = BorderLayout()
        imagePanel.border = BevelBorder(BevelBorder.LOWERED)
        val scroller = JScrollPane(theImage)
        scroller.verticalScrollBar.unitIncrement = 10
        scroller.horizontalScrollBar.unitIncrement = 10
        imagePanel.add(scroller, BorderLayout.CENTER)

        // add buttons and image panels to inner panel
        innerPanel.add(buttonPanel)
        innerPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP30))
        innerPanel.add(imagePanel)
        innerPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP20))
    }

    fun createFileChooser(): JFileChooser {
        // create a filechooser
        val fc = JFileChooser()
        if (swingSet2 != null && true) {
            fc.dragEnabled = true
        }

        // set the current directory to be the images directory
        val swingFile = File("resources/images/About.jpg")
        if (swingFile.exists()) {
            fc.currentDirectory = swingFile
            fc.selectedFile = swingFile
        }
        return fc
    }

    fun createPlainFileChooserButton(): JButton {
        val a: Action = object : AbstractAction(getString("FileChooserDemo.plainbutton")) {
            override fun actionPerformed(e: ActionEvent) {
                val fc = createFileChooser()

                // show the filechooser
                val result = fc.showOpenDialog(demoPanel)

                // if we selected an image, load the image
                if (result == JFileChooser.APPROVE_OPTION) {
                    loadImage(fc.selectedFile.path)
                }
            }
        }
        return createButton(a)
    }

    fun createPreviewFileChooserButton(): JButton {
        val a: Action = object : AbstractAction(getString("FileChooserDemo.previewbutton")) {
            override fun actionPerformed(e: ActionEvent) {
                val fc = createFileChooser()

                // Add filefilter & fileview
                val filter = createFileFilter(
                        getString("FileChooserDemo.filterdescription"),
                        "jpg", "gif")
                val fileView = ExampleFileView()
                fileView.putIcon("jpg", jpgIcon)
                fileView.putIcon("gif", gifIcon)
                fc.fileView = fileView
                fc.addChoosableFileFilter(filter)
                fc.fileFilter = filter

                // add preview accessory
                fc.accessory = FilePreviewer(fc)

                // show the filechooser
                val result = fc.showOpenDialog(demoPanel)

                // if we selected an image, load the image
                if (result == JFileChooser.APPROVE_OPTION) {
                    loadImage(fc.selectedFile.path)
                }
            }
        }
        return createButton(a)
    }

    var dialog: JDialog? = null
    var fc: JFileChooser? = null
    private fun createFileFilter(
            description: String?, vararg extensions: String): FileFilter {
        var description = description
        description = createFileNameFilterDescriptionFromExtensions(
                description, extensions)
        return FileNameExtensionFilter(description, *extensions)
    }

    private fun createFileNameFilterDescriptionFromExtensions(
            description: String?, extensions: Array<out String>): String {
        var fullDescription = if (description == null) "(" else "$description ("
        // build the description from the extension list
        fullDescription += "." + extensions[0]
        for (i in 1 until extensions.size) {
            fullDescription += ", ."
            fullDescription += extensions[i]
        }
        fullDescription += ")"
        return fullDescription
    }

    fun createCustomFileChooserButton(): JButton {
        val a: Action = object : AbstractAction(getString("FileChooserDemo.custombutton")) {
            override fun actionPerformed(e: ActionEvent) {
                fc = createFileChooser()

                // Add filefilter & fileview
                val filter = createFileFilter(
                        getString("FileChooserDemo.filterdescription"),
                        "jpg", "gif")
                val fileView = ExampleFileView()
                fileView.putIcon("jpg", jpgIcon)
                fileView.putIcon("gif", gifIcon)
                fc!!.fileView = fileView
                fc!!.addChoosableFileFilter(filter)

                // add preview accessory
                fc!!.accessory = FilePreviewer(fc)

                // remove the approve/cancel buttons
                fc!!.controlButtonsAreShown = false

                // make custom controls
                //wokka
                val custom = JPanel()
                custom.layout = BoxLayout(custom, BoxLayout.Y_AXIS)
                custom.add(Box.createRigidArea(DemoModule.Companion.VGAP10))
                val description = JLabel(getString("FileChooserDemo.description"))
                description.alignmentX = Component.CENTER_ALIGNMENT
                custom.add(description)
                custom.add(Box.createRigidArea(DemoModule.Companion.VGAP10))
                custom.add(fc)
                val okAction = createOKAction()
                fc!!.addActionListener(okAction)
                val buttons = JPanel()
                buttons.layout = BoxLayout(buttons, BoxLayout.X_AXIS)
                buttons.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
                buttons.add(createImageButton(createFindAction()))
                buttons.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
                buttons.add(createButton(createAboutAction()))
                buttons.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
                buttons.add(createButton(okAction))
                buttons.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
                buttons.add(createButton(createCancelAction()))
                buttons.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
                buttons.add(createImageButton(createHelpAction()))
                buttons.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
                custom.add(buttons)
                custom.add(Box.createRigidArea(DemoModule.Companion.VGAP10))

                // show the filechooser
                val parent = SwingUtilities.getAncestorOfClass(Frame::class.java, demoPanel) as Frame
                dialog = JDialog(parent, getString("FileChooserDemo.dialogtitle"), true)
                dialog!!.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
                dialog!!.contentPane.add(custom, BorderLayout.CENTER)
                dialog!!.pack()
                dialog!!.setLocationRelativeTo(demoPanel)
                dialog!!.show()
            }
        }
        return createButton(a)
    }

    fun createAboutAction(): Action {
        return object : AbstractAction(getString("FileChooserDemo.about")) {
            override fun actionPerformed(e: ActionEvent) {
                val file = fc!!.selectedFile
                var text: String?
                if (file == null) {
                    text = getString("FileChooserDemo.nofileselected")
                } else {
                    text = "<html>" + getString("FileChooserDemo.thefile")
                    text += "<br><font color=green>" + file.name + "</font><br>"
                    text += getString("FileChooserDemo.isprobably") + "</html>"
                }
                JOptionPane.showMessageDialog(demoPanel, text)
            }
        }
    }

    fun createOKAction(): Action {
        return object : AbstractAction(getString("FileChooserDemo.ok")) {
            override fun actionPerformed(e: ActionEvent) {
                dialog!!.dispose()
                if (e.actionCommand != JFileChooser.CANCEL_SELECTION
                        && fc!!.selectedFile != null) {
                    loadImage(fc!!.selectedFile.path)
                }
            }
        }
    }

    fun createCancelAction(): Action {
        return object : AbstractAction(getString("FileChooserDemo.cancel")) {
            override fun actionPerformed(e: ActionEvent) {
                dialog!!.dispose()
            }
        }
    }

    fun createFindAction(): Action {
        val icon: Icon? = createImageIcon("filechooser/find.gif", getString("FileChooserDemo.find"))
        return object : AbstractAction("", icon) {
            override fun actionPerformed(e: ActionEvent) {
                val result = JOptionPane.showInputDialog(demoPanel, getString("FileChooserDemo.findquestion"))
                if (result != null) {
                    JOptionPane.showMessageDialog(demoPanel, getString("FileChooserDemo.findresponse"))
                }
            }
        }
    }

    fun createHelpAction(): Action {
        val icon: Icon? = createImageIcon("filechooser/help.gif", getString("FileChooserDemo.help"))
        return object : AbstractAction("", icon) {
            override fun actionPerformed(e: ActionEvent) {
                JOptionPane.showMessageDialog(demoPanel, getString("FileChooserDemo.helptext"))
            }
        }
    }

    internal inner class MyImageIcon(filename: String?) : ImageIcon(filename) {
        @Synchronized
        override fun paintIcon(c: Component, g: Graphics, x: Int, y: Int) {
            g.color = Color.white
            g.fillRect(0, 0, c.width, c.height)
            if (imageObserver == null) {
                g.drawImage(
                        image,
                        c.width / 2 - iconWidth / 2,
                        c.height / 2 - iconHeight / 2,
                        c
                )
            } else {
                g.drawImage(
                        image,
                        c.width / 2 - iconWidth / 2,
                        c.height / 2 - iconHeight / 2,
                        imageObserver
                )
            }
        }
    }

    fun loadImage(filename: String?) {
        theImage!!.icon = MyImageIcon(filename)
    }

    fun createButton(a: Action?): JButton {
        return object : JButton(a) {
            override fun getMaximumSize(): Dimension {
                val width = Short.MAX_VALUE.toInt()
                val height = super.getMaximumSize().height
                return Dimension(width, height)
            }
        }
    }

    fun createImageButton(a: Action?): JButton {
        val b = JButton(a)
        b.margin = Insets(0, 0, 0, 0)
        return b
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = FileChooserDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * FileChooserDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        createFileChooserDemo()
    }
}

internal class FilePreviewer(fc: JFileChooser?) : JComponent(), PropertyChangeListener {
    var thumbnail: ImageIcon? = null
    fun loadImage(f: File?) {
        thumbnail = if (f == null) {
            null
        } else {
            val tmpIcon = ImageIcon(f.path)
            if (tmpIcon.iconWidth > 90) {
                ImageIcon(
                        tmpIcon.image.getScaledInstance(90, -1, Image.SCALE_DEFAULT))
            } else {
                tmpIcon
            }
        }
    }

    override fun propertyChange(e: PropertyChangeEvent) {
        val prop = e.propertyName
        if (prop === JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
            if (isShowing) {
                loadImage(e.newValue as File)
                repaint()
            }
        }
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        if (thumbnail != null) {
            var x = width / 2 - thumbnail!!.iconWidth / 2
            var y = height / 2 - thumbnail!!.iconHeight / 2
            if (y < 0) {
                y = 0
            }
            if (x < 5) {
                x = 5
            }
            thumbnail!!.paintIcon(this, g, x, y)
        }
    }

    init {
        preferredSize = Dimension(100, 50)
        fc!!.addPropertyChangeListener(this)
        border = BevelBorder(BevelBorder.LOWERED)
    }
}