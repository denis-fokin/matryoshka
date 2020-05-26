package com.ogogon.matryoshka.demo.swingset2

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.*
import javax.swing.Timer

const val numImages = 6

class TabbedPaneDemo(swingset: SwingSet2?) : DemoModule(swingset, "TabbedPaneDemo", "toolbar/JTabbedPane.gif"), ActionListener {
    var spin: HeadSpin
    var tabbedpane: JTabbedPane
    var group: ButtonGroup
    var top: JRadioButton
    var bottom: JRadioButton
    var left: JRadioButton
    var right: JRadioButton
    override fun actionPerformed(e: ActionEvent) {
        if (e.source === top) {
            tabbedpane.tabPlacement = JTabbedPane.TOP
        } else if (e.source === left) {
            tabbedpane.tabPlacement = JTabbedPane.LEFT
        } else if (e.source === bottom) {
            tabbedpane.tabPlacement = JTabbedPane.BOTTOM
        } else if (e.source === right) {
            tabbedpane.tabPlacement = JTabbedPane.RIGHT
        }
    }

    inner class HeadSpin : JComponent(), ActionListener {
        var animator: Timer? = null
        var icon = arrayOfNulls<ImageIcon>(6)
        var tmpScale = 0
        var x = DoubleArray(numImages)
        var y = DoubleArray(numImages)
        var xh = IntArray(numImages)
        var yh = IntArray(numImages)
        var scale = DoubleArray(numImages)
        fun go() {
            animator = Timer(22 + 22 + 22, this)
            animator!!.start()
        }

        override fun paint(g: Graphics) {
            g.color = background
            g.fillRect(0, 0, width, height)
            for (i in 0 until numImages) {
                if (x[i] > 3 * i) {
                    nudge(i)
                    squish(g, icon[i], xh[i], yh[i], scale[i])
                } else {
                    x[i] += .05
                    y[i] += .05
                }
            }
        }

        var rand = Random()
        fun nudge(i: Int) {
            x[i] += rand.nextInt(1000).toDouble() / 8756
            y[i] += rand.nextInt(1000).toDouble() / 5432
            val tmpScale = (Math.abs(Math.sin(x[i])) * 10).toInt()
            scale[i] = tmpScale.toDouble() / 10
            val nudgeX = (width.toDouble() / 2 * .8).toInt()
            val nudgeY = (height.toDouble() / 2 * .60).toInt()
            xh[i] = (Math.sin(x[i]) * nudgeX).toInt() + nudgeX
            yh[i] = (Math.sin(y[i]) * nudgeY).toInt() + nudgeY
        }

        fun squish(g: Graphics, icon: ImageIcon?, x: Int, y: Int, scale: Double) {
            if (isVisible) {
                g.drawImage(icon!!.image, x, y,
                        (icon.iconWidth * scale).toInt(),
                        (icon.iconHeight * scale).toInt(),
                        this)
            }
        }

        override fun actionPerformed(e: ActionEvent) {
            if (isVisible) {
                repaint()
            } else {
                animator!!.stop()
            }
        }

        init {
            background = Color.black
            icon[0] = createImageIcon("tabbedpane/ewan.gif", getString("TabbedPaneDemo.ewan"))
            icon[1] = createImageIcon("tabbedpane/stephen.gif", getString("TabbedPaneDemo.stephen"))
            icon[2] = createImageIcon("tabbedpane/david.gif", getString("TabbedPaneDemo.david"))
            icon[3] = createImageIcon("tabbedpane/matthew.gif", getString("TabbedPaneDemo.matthew"))
            icon[4] = createImageIcon("tabbedpane/blake.gif", getString("TabbedPaneDemo.blake"))
            icon[5] = createImageIcon("tabbedpane/brooke.gif", getString("TabbedPaneDemo.brooke"))

            /*
            for(int i = 0; i < 6; i++) {
                x[i] = (double) rand.nextInt(500);
                y[i] = (double) rand.nextInt(500);
            }
            */
        }
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = TabbedPaneDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * TabbedPaneDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.

        // create tab position controls
        val tabControls = JPanel()
        tabControls.add(JLabel(getString("TabbedPaneDemo.label")))
        top = tabControls.add(JRadioButton(getString("TabbedPaneDemo.top"))) as JRadioButton
        left = tabControls.add(JRadioButton(getString("TabbedPaneDemo.left"))) as JRadioButton
        bottom = tabControls.add(JRadioButton(getString("TabbedPaneDemo.bottom"))) as JRadioButton
        right = tabControls.add(JRadioButton(getString("TabbedPaneDemo.right"))) as JRadioButton
        demoPanel?.add(tabControls, BorderLayout.NORTH)
        group = ButtonGroup()
        group.add(top)
        group.add(bottom)
        group.add(left)
        group.add(right)
        top.isSelected = true
        top.addActionListener(this)
        bottom.addActionListener(this)
        left.addActionListener(this)
        right.addActionListener(this)

        // create tab
        tabbedpane = JTabbedPane()
        demoPanel?.add(tabbedpane, BorderLayout.CENTER)
        var name = getString("TabbedPaneDemo.laine")
        var pix = JLabel(createImageIcon("tabbedpane/laine.jpg", name))
        tabbedpane.add(name, pix)
        name = getString("TabbedPaneDemo.ewan")
        pix = JLabel(createImageIcon("tabbedpane/ewan.jpg", name))
        tabbedpane.add(name, pix)
        name = getString("TabbedPaneDemo.hania")
        pix = JLabel(createImageIcon("tabbedpane/hania.jpg", name))
        tabbedpane.add(name, pix)
        name = getString("TabbedPaneDemo.bounce")
        spin = HeadSpin()
        tabbedpane.add(name, spin)
        tabbedpane.model.addChangeListener { e ->
            val model = e.source as SingleSelectionModel
            if (model.selectedIndex == tabbedpane.tabCount - 1) {
                spin.go()
            }
        }
    }
}