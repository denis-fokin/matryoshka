package com.ogogon.matryoshka.demo.swingset2

import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ActionListener
import javax.swing.*

class ColorChooserDemo(swingset: SwingSet2?) : DemoModule(swingset, "ColorChooserDemo", "toolbar/JColorChooser.gif") {
    var bezAnim: BezierAnimationPanel
    var outerColorButton: JButton? = null
    var backgroundColorButton: JButton? = null
    var gradientAButton: JButton? = null
    var gradientBButton: JButton? = null

    // to store the color chosen from the JColorChooser
    private var chosen: Color? = null

    internal inner class ColorSwatch(var gradient: String, var bez: BezierAnimationPanel) : Icon {
        override fun getIconWidth(): Int {
            return 11
        }

        override fun getIconHeight(): Int {
            return 11
        }

        override fun paintIcon(c: Component, g: Graphics, x: Int, y: Int) {
            g.color = Color.black
            g.fillRect(x, y, iconWidth, iconHeight)
            if (gradient == "GradientA") {
                g.color = bez.gradientColorA
            } else if (gradient == "GradientB") {
                g.color = bez.gradientColorB
            } else if (gradient == "Background") {
                g.color = BezierAnimationPanel.getBackgroundColor(bez)
            } else if (gradient == "OuterLine") {
                g.color = bez.outerColor
            }
            g.fillRect(x + 2, y + 2, iconWidth - 4, iconHeight - 4)
        }

    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = ColorChooserDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * ColorChooserDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.

        // Create the bezier animation panel to put in the center of the panel.
        bezAnim = BezierAnimationPanel()
        outerColorButton = JButton(getString("ColorChooserDemo.outer_line"))
        outerColorButton!!.icon = ColorSwatch("OuterLine", bezAnim)
        backgroundColorButton = JButton(getString("ColorChooserDemo.background"))
        backgroundColorButton!!.icon = ColorSwatch("Background", bezAnim)
        gradientAButton = JButton(getString("ColorChooserDemo.grad_a"))
        gradientAButton!!.icon = ColorSwatch("GradientA", bezAnim)
        gradientBButton = JButton(getString("ColorChooserDemo.grad_b"))
        gradientBButton!!.icon = ColorSwatch("GradientB", bezAnim)
        val l = ActionListener { e ->
            var current = bezAnim.outerColor
            if (e.source === backgroundColorButton) {
                current = BezierAnimationPanel.getBackgroundColor(bezAnim)
            } else if (e.source === gradientAButton) {
                current = bezAnim.outerColor
            } else if (e.source === gradientBButton) {
                current = bezAnim.outerColor
            }
            val chooser = JColorChooser(current ?: Color.WHITE)
            if (swingSet2 != null && true) { //todo change to SwigSet2.isEnabled()
                chooser.dragEnabled = true
            }
            chosen = null
            val okListener = ActionListener { chosen = chooser.color }
            val dialog = JColorChooser.createDialog(demoPanel,
                    getString("ColorChooserDemo.chooser_title"),
                    true,
                    chooser,
                    okListener,
                    null)
            dialog.show()
            if (e.source === outerColorButton) {
                bezAnim.outerColor = chosen!!
            } else if (e.source === backgroundColorButton) {
                bezAnim.backgroundColor = chosen!!
            } else if (e.source === gradientAButton) {
                bezAnim.gradientColorA = chosen!!
            } else {
                bezAnim.gradientColorB = chosen!!
            }
        }
        outerColorButton!!.addActionListener(l)
        backgroundColorButton!!.addActionListener(l)
        gradientAButton!!.addActionListener(l)
        gradientBButton!!.addActionListener(l)

        // Add everything to the panel
        val p = demoPanel
        p!!.layout = BoxLayout(p, BoxLayout.Y_AXIS)

        // Add control buttons
        val buttonPanel = JPanel()
        buttonPanel.layout = BoxLayout(buttonPanel, BoxLayout.X_AXIS)
        buttonPanel.add(backgroundColorButton)
        buttonPanel.add(Box.createRigidArea(Dimension(15, 1)))
        buttonPanel.add(gradientAButton)
        buttonPanel.add(Box.createRigidArea(Dimension(15, 1)))
        buttonPanel.add(gradientBButton)
        buttonPanel.add(Box.createRigidArea(Dimension(15, 1)))
        buttonPanel.add(outerColorButton)

        // Add the panel midway down the panel
        p.add(Box.createRigidArea(Dimension(1, 10)))
        p.add(buttonPanel)
        p.add(Box.createRigidArea(Dimension(1, 5)))
        p.add(bezAnim)
    }
}