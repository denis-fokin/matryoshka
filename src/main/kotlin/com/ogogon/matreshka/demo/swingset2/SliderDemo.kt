package com.ogogon.matreshka.demo.swingset2

import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.*
import javax.swing.border.BevelBorder
import javax.swing.border.TitledBorder
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

class SliderDemo(swingset: SwingSet2?) : DemoModule(swingset, "SliderDemo", "toolbar/JSlider.gif") {
    fun createSliderDemo() {
        var s: JSlider
        val hp: JPanel
        val vp: JPanel
        val g: GridLayout
        val tp: JPanel
        val tf: JLabel
        val listener: ChangeListener
        demoPanel?.layout = BorderLayout()
        tf = JLabel(getString("SliderDemo.slidervalue"))
        demoPanel?.add(tf, BorderLayout.SOUTH)
        tp = JPanel()
        g = GridLayout(1, 2)
        g.hgap = 5
        g.vgap = 5
        tp.layout = g
        demoPanel?.add(tp, BorderLayout.CENTER)
        listener = SliderListener(tf)
        val border = BevelBorder(BevelBorder.LOWERED)
        hp = JPanel()
        hp.layout = BoxLayout(hp, BoxLayout.Y_AXIS)
        hp.border = TitledBorder(
                border,
                getString("SliderDemo.horizontal"),
                TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP)
        tp.add(hp)
        vp = JPanel()
        vp.layout = BoxLayout(vp, BoxLayout.X_AXIS)
        vp.border = TitledBorder(
                border,
                getString("SliderDemo.vertical"),
                TitledBorder.LEFT,
                TitledBorder.ABOVE_TOP)
        tp.add(vp)

        // Horizontal Slider 1
        var p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.Y_AXIS)
        p.border = TitledBorder(getString("SliderDemo.plain"))
        s = JSlider(-10, 100, 20)
        s.accessibleContext.accessibleName = getString("SliderDemo.plain")
        s.accessibleContext.accessibleDescription = getString("SliderDemo.a_plain_slider")
        s.addChangeListener(listener)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP5))
        p.add(s)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP5))
        hp.add(p)
        hp.add(Box.createRigidArea(DemoModule.Companion.VGAP10))

        // Horizontal Slider 2
        p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.Y_AXIS)
        p.border = TitledBorder(getString("SliderDemo.majorticks"))
        s = JSlider(100, 1000, 400)
        s.paintTicks = true
        s.majorTickSpacing = 100
        s.accessibleContext.accessibleName = getString("SliderDemo.majorticks")
        s.accessibleContext.accessibleDescription = getString("SliderDemo.majorticksdescription")
        s.addChangeListener(listener)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP5))
        p.add(s)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP5))
        hp.add(p)
        hp.add(Box.createRigidArea(DemoModule.Companion.VGAP10))

        // Horizontal Slider 3
        p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.Y_AXIS)
        p.border = TitledBorder(getString("SliderDemo.ticks"))
        s = JSlider(0, 11, 6)
        s.putClientProperty("JSlider.isFilled", true)
        s.paintTicks = true
        s.majorTickSpacing = 5
        s.minorTickSpacing = 1
        s.paintLabels = true
        s.snapToTicks = true
        s.labelTable.put(11, JLabel(11.toString(), JLabel.CENTER))
        s.labelTable = s.labelTable
        s.accessibleContext.accessibleName = getString("SliderDemo.minorticks")
        s.accessibleContext.accessibleDescription = getString("SliderDemo.minorticksdescription")
        s.addChangeListener(listener)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP5))
        p.add(s)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP5))
        hp.add(p)
        hp.add(Box.createRigidArea(DemoModule.Companion.VGAP10))

        // Horizontal Slider 4
        p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.Y_AXIS)
        p.border = TitledBorder(getString("SliderDemo.disabled"))
        val brm: BoundedRangeModel = DefaultBoundedRangeModel(80, 0, 0, 100)
        s = JSlider(brm)
        s.paintTicks = true
        s.majorTickSpacing = 20
        s.minorTickSpacing = 5
        s.isEnabled = false
        s.accessibleContext.accessibleName = getString("SliderDemo.disabled")
        s.accessibleContext.accessibleDescription = getString("SliderDemo.disableddescription")
        s.addChangeListener(listener)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP5))
        p.add(s)
        p.add(Box.createRigidArea(DemoModule.Companion.VGAP5))
        hp.add(p)

        //////////////////////////////////////////////////////////////////////////////

        // Vertical Slider 1
        p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.X_AXIS)
        p.border = TitledBorder(getString("SliderDemo.plain"))
        s = JSlider(JSlider.VERTICAL, -10, 100, 20)
        s.accessibleContext.accessibleName = getString("SliderDemo.plain")
        s.accessibleContext.accessibleDescription = getString("SliderDemo.a_plain_slider")
        s.addChangeListener(listener)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        p.add(s)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        vp.add(p)
        vp.add(Box.createRigidArea(DemoModule.Companion.HGAP10))

        // Vertical Slider 2
        p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.X_AXIS)
        p.border = TitledBorder(getString("SliderDemo.majorticks"))
        s = JSlider(JSlider.VERTICAL, 100, 1000, 400)
        s.putClientProperty("JSlider.isFilled", true)
        s.paintTicks = true
        s.majorTickSpacing = 100
        s.accessibleContext.accessibleName = getString("SliderDemo.majorticks")
        s.accessibleContext.accessibleDescription = getString("SliderDemo.majorticksdescription")
        s.addChangeListener(listener)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP25))
        p.add(s)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP25))
        vp.add(p)
        vp.add(Box.createRigidArea(DemoModule.Companion.HGAP5))

        // Vertical Slider 3
        p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.X_AXIS)
        p.border = TitledBorder(getString("SliderDemo.minorticks"))
        s = JSlider(JSlider.VERTICAL, 0, 100, 60)
        s.paintTicks = true
        s.majorTickSpacing = 20
        s.minorTickSpacing = 5
        s.paintLabels = true
        s.accessibleContext.accessibleName = getString("SliderDemo.minorticks")
        s.accessibleContext.accessibleDescription = getString("SliderDemo.minorticksdescription")
        s.addChangeListener(listener)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        p.add(s)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        vp.add(p)
        vp.add(Box.createRigidArea(DemoModule.Companion.HGAP5))

        // Vertical Slider 4
        p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.X_AXIS)
        p.border = TitledBorder(getString("SliderDemo.disabled"))
        s = JSlider(JSlider.VERTICAL, 0, 100, 80)
        s.paintTicks = true
        s.majorTickSpacing = 20
        s.minorTickSpacing = 5
        s.isEnabled = false
        s.accessibleContext.accessibleName = getString("SliderDemo.disabled")
        s.accessibleContext.accessibleDescription = getString("SliderDemo.disableddescription")
        s.addChangeListener(listener)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP20))
        p.add(s)
        p.add(Box.createRigidArea(DemoModule.Companion.HGAP20))
        vp.add(p)
    }

    internal inner class SliderListener(var tf: JLabel) : ChangeListener {
        override fun stateChanged(e: ChangeEvent) {
            val s1 = e.source as JSlider
            tf.text = getString("SliderDemo.slidervalue") + s1.value
        }

    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = SliderDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * SliderDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        createSliderDemo()
    }
}