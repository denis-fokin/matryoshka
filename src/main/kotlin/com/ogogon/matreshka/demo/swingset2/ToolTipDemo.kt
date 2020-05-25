package com.ogogon.matreshka.demo.swingset2

import java.awt.*
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JLabel

class ToolTipDemo(swingset: SwingSet2?) : DemoModule(swingset, "ToolTipDemo", "toolbar/ToolTip.gif") {
    internal inner class Cow : JLabel(createImageIcon("tooltip/cow.gif", getString("ToolTipDemo.bessie"))) {
        var cowgon = Polygon()
        var moo = false
        var milk = false
        var tail = false

        // Use the contains method to set the tooltip text depending
        // on where the mouse is over the cow.
        override fun contains(x: Int, y: Int): Boolean {
            if (!cowgon.contains(Point(x, y))) {
                return false
            }
            if (x > 30 && x < 60 && y > 60 && y < 85) {
                if (!moo) {
                    toolTipText = "<html><center><font color=blue size=+2>" +
                            getString("ToolTipDemo.moo") + "</font></center></html>"
                    moo = true
                    milk = false
                    tail = false
                }
            } else if (x > 150 && x < 260 && y > 90 && y < 145) {
                if (!milk) {
                    toolTipText = "<html><center><font face=AvantGarde size=+1 color=#D2691E>" +
                            getString("ToolTipDemo.got_milk") + "</font></center></html>"
                    milk = true
                    moo = false
                    tail = false
                }
            } else if (x > 280 && x < 300 && y > 20 && y < 175) {
                if (!tail) {
                    toolTipText = "<html><em><b>" + getString("ToolTipDemo.tail") + "</b></em></html>"
                    tail = true
                    moo = false
                    milk = false
                }
            } else if (moo || milk || tail) {
                toolTipText = getString("ToolTipDemo.tooltip_features")
                moo = false
                tail = false
                milk = false
            }
            return true
        }

        init {
            alignmentX = Component.CENTER_ALIGNMENT

            // Set polygon points that define the outline of the cow.
            cowgon.addPoint(3, 20)
            cowgon.addPoint(44, 4)
            cowgon.addPoint(79, 15)
            cowgon.addPoint(130, 11)
            cowgon.addPoint(252, 5)
            cowgon.addPoint(181, 17)
            cowgon.addPoint(301, 45)
            cowgon.addPoint(292, 214)
            cowgon.addPoint(269, 209)
            cowgon.addPoint(266, 142)
            cowgon.addPoint(250, 161)
            cowgon.addPoint(235, 218)
            cowgon.addPoint(203, 206)
            cowgon.addPoint(215, 137)
            cowgon.addPoint(195, 142)
            cowgon.addPoint(143, 132)
            cowgon.addPoint(133, 189)
            cowgon.addPoint(160, 200)
            cowgon.addPoint(97, 196)
            cowgon.addPoint(107, 182)
            cowgon.addPoint(118, 185)
            cowgon.addPoint(110, 144)
            cowgon.addPoint(59, 77)
            cowgon.addPoint(30, 82)
            cowgon.addPoint(30, 35)
            cowgon.addPoint(15, 36)
        }
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = ToolTipDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * ToolTipDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.

        // Set the layout manager.
        val p = demoPanel
        p!!.layout = BoxLayout(p, BoxLayout.Y_AXIS)
        p.background = Color.white

        // Create a Cow to put in the center of the panel.
        val cow = Cow()
        cow.accessibleContext.accessibleName = getString("ToolTipDemo.accessible_cow")

        // Set the tooltip text. Note, for fun, we also set more tooltip text
        // descriptions for the cow down below in the Cow.contains() method.
        cow.toolTipText = getString("ToolTipDemo.cow")

        // Add the cow midway down the panel
        p.add(Box.createRigidArea(Dimension(1, 150)))
        p.add(cow)
    }
}