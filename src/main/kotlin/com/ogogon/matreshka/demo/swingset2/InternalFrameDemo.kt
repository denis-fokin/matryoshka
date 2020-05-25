package com.ogogon.matreshka.demo.swingset2

import java.awt.*
import java.awt.event.ActionEvent
import java.beans.PropertyVetoException
import javax.swing.*

class InternalFrameDemo(swingset: SwingSet2?) : DemoModule(swingset, "InternalFrameDemo", "toolbar/JDesktop.gif") {
    var windowCount = 0
    var desktop: JDesktopPane? = null
    var icon1: ImageIcon?
    var icon2: ImageIcon?
    var icon3: ImageIcon?
    var icon4: ImageIcon?
    var smIcon1: ImageIcon?
    var smIcon2: ImageIcon?
    var smIcon3: ImageIcon?
    var smIcon4: ImageIcon?
    var FIRST_FRAME_LAYER = 1
    var demoFrameLayer = 2
    var PALETTE_LAYER = 3
    var FRAME0_X = 15
    var FRAME0_Y = 280
    var FRAME0_WIDTH = 320
    var FRAME0_HEIGHT = 230
    var frameWidth = 225
    var frameHeight = 150
    var PALETTE_X = 375
    var PALETTE_Y = 20
    var PALETTE_WIDTH = 260
    var PALETTE_HEIGHT = 260
    var windowResizable: JCheckBox? = null
    var windowClosable: JCheckBox? = null
    var windowIconifiable: JCheckBox? = null
    var windowMaximizable: JCheckBox? = null
    var windowTitleField: JTextField? = null
    var windowTitleLabel: JLabel? = null

    /**
     * Create an internal frame and add a scrollable imageicon to it
     */
    fun createInternalFrame(icon: Icon?, layer: Int?, width: Int, height: Int): JInternalFrame {
        var jif = JInternalFrame()
        if (windowTitleField!!.text != getString("InternalFrameDemo.frame_label")) {
            jif.title = windowTitleField!!.text + "  "
        } else {
            jif = JInternalFrame(getString("InternalFrameDemo.frame_label") + " " + windowCount + "  ")
        }

        // set properties
        jif.isClosable = windowClosable!!.isSelected
        jif.isMaximizable = windowMaximizable!!.isSelected
        jif.isIconifiable = windowIconifiable!!.isSelected
        jif.isResizable = windowResizable!!.isSelected
        jif.setBounds(20 * (windowCount % 10), 20 * (windowCount % 10), width, height)
        jif.contentPane = ImageScroller(this, icon, 0, windowCount)
        windowCount++
        desktop!!.add(jif, layer!!)

        // Set this internal frame to be selected
        try {
            jif.isSelected = true
        } catch (e2: PropertyVetoException) {
        }
        jif.show()
        return jif
    }

    fun createInternalFramePalette(): JInternalFrame {
        val palette = JInternalFrame(
                getString("InternalFrameDemo.palette_label")
        )
        palette.putClientProperty("JInternalFrame.isPalette", java.lang.Boolean.TRUE)
        palette.contentPane.layout = BorderLayout()
        palette.setBounds(PALETTE_X, PALETTE_Y, PALETTE_WIDTH, PALETTE_HEIGHT)
        palette.isResizable = true
        palette.isIconifiable = true
        desktop!!.add(palette, PALETTE_LAYER)

        // *************************************
        // * Create create frame maker buttons *
        // *************************************
        val b1 = JButton(smIcon1)
        val b2 = JButton(smIcon2)
        val b3 = JButton(smIcon3)
        val b4 = JButton(smIcon4)

        // add frame maker actions
        b1.addActionListener(ShowFrameAction(this, icon1))
        b2.addActionListener(ShowFrameAction(this, icon2))
        b3.addActionListener(ShowFrameAction(this, icon3))
        b4.addActionListener(ShowFrameAction(this, icon4))

        // add frame maker buttons to panel
        var p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.Y_AXIS)
        val buttons1 = JPanel()
        buttons1.layout = BoxLayout(buttons1, BoxLayout.X_AXIS)
        val buttons2 = JPanel()
        buttons2.layout = BoxLayout(buttons2, BoxLayout.X_AXIS)
        buttons1.add(b1)
        buttons1.add(Box.createRigidArea(DemoModule.Companion.HGAP15))
        buttons1.add(b2)
        buttons2.add(b3)
        buttons2.add(Box.createRigidArea(DemoModule.Companion.HGAP15))
        buttons2.add(b4)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP10))
        p.add(buttons1)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        p.add(buttons2)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP10))
        palette.contentPane.add(p, BorderLayout.NORTH)

        // ************************************
        // * Create frame property checkboxes *
        // ************************************
        p = object : JPanel() {
            override fun getInsets(): Insets {
                return Insets(10, 15, 10, 5)
            }
        }
        p.setLayout(GridLayout(1, 2))
        var box = Box(BoxLayout.Y_AXIS)
        windowResizable = JCheckBox(getString("InternalFrameDemo.resizable_label"), true)
        windowIconifiable = JCheckBox(getString("InternalFrameDemo.iconifiable_label"), true)
        box.add(Box.createGlue())
        box.add(windowResizable)
        box.add(windowIconifiable)
        box.add(Box.createGlue())
        p.add(box)
        box = Box(BoxLayout.Y_AXIS)
        windowClosable = JCheckBox(getString("InternalFrameDemo.closable_label"), true)
        windowMaximizable = JCheckBox(getString("InternalFrameDemo.maximizable_label"), true)
        box.add(Box.createGlue())
        box.add(windowClosable)
        box.add(windowMaximizable)
        box.add(Box.createGlue())
        p.add(box)
        palette.contentPane.add(p, BorderLayout.CENTER)


        // ************************************
        // *   Create Frame title textfield   *
        // ************************************
        p = object : JPanel() {
            override fun getInsets(): Insets {
                return Insets(0, 0, 10, 0)
            }
        }
        windowTitleField = JTextField(getString("InternalFrameDemo.frame_label"))
        windowTitleLabel = JLabel(getString("InternalFrameDemo.title_text_field_label"))
        p.setLayout(BoxLayout(p, BoxLayout.X_AXIS))
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP5))
        p.add(windowTitleLabel, BorderLayout.WEST)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP5))
        p.add(windowTitleField, BorderLayout.CENTER)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP5))
        palette.contentPane.add(p, BorderLayout.SOUTH)
        palette.show()
        return palette
    }

    internal inner class ShowFrameAction(var demo: InternalFrameDemo, var icon: Icon?) : AbstractAction() {
        override fun actionPerformed(e: ActionEvent) {
            demo.createInternalFrame(icon,
                    demoFrameLayer,
                    frameWidth,
                    frameHeight
            )
        }

    }

    internal inner class ImageScroller(demo: InternalFrameDemo?, icon: Icon?, layer: Int, count: Int) : JScrollPane() {
        override fun getMinimumSize(): Dimension {
            return Dimension(25, 25)
        }

        init {
            val p = JPanel()
            p.background = Color.white
            p.layout = BorderLayout()
            p.add(JLabel(icon), BorderLayout.CENTER)
            getViewport().add(p)
            getHorizontalScrollBar().unitIncrement = 10
            getVerticalScrollBar().unitIncrement = 10
        }
    }

    public override fun updateDragEnabled(dragEnabled: Boolean) {
        windowTitleField!!.dragEnabled = dragEnabled
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = InternalFrameDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * InternalFrameDemo Constructor
     */
    init {

        // preload all the icons we need for this demo
        icon1 = createImageIcon("misc/toast.gif", getString("InternalFrameDemo.toast"))
        icon2 = createImageIcon("misc/duke.gif", getString("InternalFrameDemo.duke"))
        icon3 = createImageIcon("misc/duchess.gif", getString("InternalFrameDemo.duchess"))
        icon4 = createImageIcon("misc/cab.gif", getString("InternalFrameDemo.cab"))
        smIcon1 = createImageIcon("misc/toast_small.gif", getString("InternalFrameDemo.toast"))
        smIcon2 = createImageIcon("misc/duke_small.gif", getString("InternalFrameDemo.duke"))
        smIcon3 = createImageIcon("misc/duchess_small.gif", getString("InternalFrameDemo.duchess"))
        smIcon4 = createImageIcon("misc/cab_small.gif", getString("InternalFrameDemo.cab"))

        // Create the desktop pane
        desktop = JDesktopPane()
        demoPanel?.add(desktop, BorderLayout.CENTER)

        // Create the "frame maker" palette
        createInternalFramePalette()

        // Create an initial internal frame to show
        val frame1 = createInternalFrame(icon1, FIRST_FRAME_LAYER, 1, 1)
        frame1.setBounds(FRAME0_X, FRAME0_Y, FRAME0_WIDTH, FRAME0_HEIGHT)

        // Create four more starter windows
        createInternalFrame(icon1, demoFrameLayer, frameWidth, frameHeight)
        createInternalFrame(icon3, demoFrameLayer, frameWidth, frameHeight)
        createInternalFrame(icon4, demoFrameLayer, frameWidth, frameHeight)
        createInternalFrame(icon2, demoFrameLayer, frameWidth, frameHeight)
    }
}