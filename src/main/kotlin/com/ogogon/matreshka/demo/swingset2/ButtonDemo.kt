package com.ogogon.matreshka.demo.swingset2

import java.awt.Component
import java.awt.Dimension
import java.awt.Insets
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import java.util.*
import javax.swing.*
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder
import javax.swing.border.TitledBorder
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

class ButtonDemo(swingset: SwingSet2?) : DemoModule(swingset, "ButtonDemo", "toolbar/JButton.gif"), ChangeListener {
    var tab: JTabbedPane
    var buttonPanel = JPanel()
    var checkboxPanel = JPanel()
    var radioButtonPanel = JPanel()
    var toggleButtonPanel = JPanel()
    var buttons = Vector<Any?>()
    var checkboxes = Vector<Any?>()
    var radiobuttons = Vector<Any?>()
    var togglebuttons = Vector<Any?>()
    var currentControls = buttons
    var button: JButton? = null
    var check: JCheckBox? = null
    var radio: JRadioButton? = null
    var toggle: JToggleButton? = null
    var border5 = EmptyBorder(5, 5, 5, 5)
    var border10 = EmptyBorder(10, 10, 10, 10)
    var buttonDisplayListener: ItemListener? = null
    var buttonPadListener: ItemListener? = null
    var insets0 = Insets(0, 0, 0, 0)
    var insets10 = Insets(10, 10, 10, 10)
    fun addButtons() {
        tab.addTab(getString("ButtonDemo.buttons"), buttonPanel)
        buttonPanel.layout = BoxLayout(buttonPanel, BoxLayout.X_AXIS)
        buttonPanel.border = border5
        val p1 = createVerticalPanel(true)
        p1!!.alignmentY = Component.TOP_ALIGNMENT
        buttonPanel.add(p1)

        // Text Buttons
        val p2 = createHorizontalPanel(false)
        p1.add(p2)
        p2!!.border = CompoundBorder(TitledBorder(null, getString("ButtonDemo.textbuttons"),
                TitledBorder.LEFT, TitledBorder.TOP), border5)
        val element = p2.add(JButton(getString("ButtonDemo.button1")))
        buttons.add(element)
        p2.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        buttons.add(p2.add(JButton(getString("ButtonDemo.button2"))))
        p2.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        buttons.add(p2.add(JButton(getString("ButtonDemo.button3"))))


        // Image Buttons
        p1.add(Box.createRigidArea(DemoModule.Companion.VGAP30))
        val p3 = createHorizontalPanel(false)
        p1.add(p3)
        p3!!.layout = BoxLayout(p3, BoxLayout.X_AXIS)
        p3.border = TitledBorder(null, getString("ButtonDemo.imagebuttons"),
                TitledBorder.LEFT, TitledBorder.TOP)

        // home image button
        var description = getString("ButtonDemo.phone")
        button = JButton(createImageIcon("buttons/b1.gif", description))
        button!!.pressedIcon = createImageIcon("buttons/b1p.gif", description)
        button!!.rolloverIcon = createImageIcon("buttons/b1r.gif", description)
        button!!.disabledIcon = createImageIcon("buttons/b1d.gif", description)
        button!!.margin = Insets(0, 0, 0, 0)
        p3.add(button)
        buttons.add(button)
        p3.add(Box.createRigidArea(DemoModule.Companion.HGAP10))

        // write image button
        description = getString("ButtonDemo.write")
        button = JButton(createImageIcon("buttons/b2.gif", description))
        button!!.pressedIcon = createImageIcon("buttons/b2p.gif", description)
        button!!.rolloverIcon = createImageIcon("buttons/b2r.gif", description)
        button!!.disabledIcon = createImageIcon("buttons/b2d.gif", description)
        button!!.margin = Insets(0, 0, 0, 0)
        p3.add(button)
        buttons.add(button)
        p3.add(Box.createRigidArea(DemoModule.Companion.HGAP10))

        // write image button
        description = getString("ButtonDemo.peace")
        button = JButton(createImageIcon("buttons/b3.gif", description))
        button!!.pressedIcon = createImageIcon("buttons/b3p.gif", description)
        button!!.rolloverIcon = createImageIcon("buttons/b3r.gif", description)
        button!!.disabledIcon = createImageIcon("buttons/b3d.gif", description)
        button!!.margin = Insets(0, 0, 0, 0)
        p3.add(button)
        buttons.add(button)
        p1.add(Box.createVerticalGlue())
        buttonPanel.add(Box.createHorizontalGlue())
        currentControls = buttons
        buttonPanel.add(createControls())
    }

    fun addRadioButtons() {
        var group = ButtonGroup()
        tab.addTab(getString("ButtonDemo.radiobuttons"), radioButtonPanel)
        radioButtonPanel.layout = BoxLayout(radioButtonPanel, BoxLayout.X_AXIS)
        radioButtonPanel.border = border5
        val p1 = createVerticalPanel(true)
        p1!!.alignmentY = Component.TOP_ALIGNMENT
        radioButtonPanel.add(p1)

        // Text Radio Buttons
        val p2 = createHorizontalPanel(false)
        p1.add(p2)
        p2!!.border = CompoundBorder(
                TitledBorder(
                        null, getString("ButtonDemo.textradiobuttons"),
                        TitledBorder.LEFT, TitledBorder.TOP), border5)
        radio = p2.add(
                JRadioButton(getString("ButtonDemo.radio1"))) as JRadioButton
        group.add(radio)
        radiobuttons.add(radio)
        p2.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        radio = p2.add(
                JRadioButton(getString("ButtonDemo.radio2"))) as JRadioButton
        group.add(radio)
        radiobuttons.add(radio)
        p2.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        radio = p2.add(
                JRadioButton(getString("ButtonDemo.radio3"))) as JRadioButton
        group.add(radio)
        radiobuttons.add(radio)

        // Image Radio Buttons
        group = ButtonGroup()
        p1.add(Box.createRigidArea(DemoModule.Companion.VGAP30))
        val p3 = createHorizontalPanel(false)
        p1.add(p3)
        p3!!.layout = BoxLayout(p3, BoxLayout.X_AXIS)
        p3.border = TitledBorder(null, getString("ButtonDemo.imageradiobuttons"),
                TitledBorder.LEFT, TitledBorder.TOP)

        // image radio button 1
        val description = getString("ButtonDemo.customradio")
        var text = getString("ButtonDemo.radio1")
        radio = JRadioButton(text, createImageIcon("buttons/rb.gif", description))
        radio!!.pressedIcon = createImageIcon("buttons/rbp.gif", description)
        radio!!.rolloverIcon = createImageIcon("buttons/rbr.gif", description)
        radio!!.rolloverSelectedIcon = createImageIcon("buttons/rbrs.gif", description)
        radio!!.selectedIcon = createImageIcon("buttons/rbs.gif", description)
        radio!!.margin = Insets(0, 0, 0, 0)
        group.add(radio)
        p3.add(radio)
        radiobuttons.add(radio)
        p3.add(Box.createRigidArea(DemoModule.Companion.HGAP20))

        // image radio button 2
        text = getString("ButtonDemo.radio2")
        radio = JRadioButton(text, createImageIcon("buttons/rb.gif", description))
        radio!!.pressedIcon = createImageIcon("buttons/rbp.gif", description)
        radio!!.rolloverIcon = createImageIcon("buttons/rbr.gif", description)
        radio!!.rolloverSelectedIcon = createImageIcon("buttons/rbrs.gif", description)
        radio!!.selectedIcon = createImageIcon("buttons/rbs.gif", description)
        radio!!.margin = Insets(0, 0, 0, 0)
        group.add(radio)
        p3.add(radio)
        radiobuttons.add(radio)
        p3.add(Box.createRigidArea(DemoModule.Companion.HGAP20))

        // image radio button 3
        text = getString("ButtonDemo.radio3")
        radio = JRadioButton(text, createImageIcon("buttons/rb.gif", description))
        radio!!.pressedIcon = createImageIcon("buttons/rbp.gif", description)
        radio!!.rolloverIcon = createImageIcon("buttons/rbr.gif", description)
        radio!!.rolloverSelectedIcon = createImageIcon("buttons/rbrs.gif", description)
        radio!!.selectedIcon = createImageIcon("buttons/rbs.gif", description)
        radio!!.margin = Insets(0, 0, 0, 0)
        group.add(radio)
        radiobuttons.add(radio)
        p3.add(radio)

        // verticaly glue fills out the rest of the box
        p1.add(Box.createVerticalGlue())
        radioButtonPanel.add(Box.createHorizontalGlue())
        currentControls = radiobuttons
        radioButtonPanel.add(createControls())
    }

    fun addCheckBoxes() {
        tab.addTab(getString("ButtonDemo.checkboxes"), checkboxPanel)
        checkboxPanel.layout = BoxLayout(checkboxPanel, BoxLayout.X_AXIS)
        checkboxPanel.border = border5
        val p1 = createVerticalPanel(true)
        p1!!.alignmentY = Component.TOP_ALIGNMENT
        checkboxPanel.add(p1)

        // Text Radio Buttons
        val p2 = createHorizontalPanel(false)
        p1.add(p2)
        p2!!.border = CompoundBorder(
                TitledBorder(
                        null, getString("ButtonDemo.textcheckboxes"),
                        TitledBorder.LEFT, TitledBorder.TOP), border5)
        checkboxes.add(p2.add(JCheckBox(getString("ButtonDemo.check1"))))
        p2.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        checkboxes.add(p2.add(JCheckBox(getString("ButtonDemo.check2"))))
        p2.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        checkboxes.add(p2.add(JCheckBox(getString("ButtonDemo.check3"))))

        // Image Radio Buttons
        p1.add(Box.createRigidArea(DemoModule.Companion.VGAP30))
        val p3 = createHorizontalPanel(false)
        p1.add(p3)
        p3!!.layout = BoxLayout(p3, BoxLayout.X_AXIS)
        p3.border = TitledBorder(null, getString("ButtonDemo.imagecheckboxes"),
                TitledBorder.LEFT, TitledBorder.TOP)

        // image checkbox 1
        val description = getString("ButtonDemo.customcheck")
        var text = getString("ButtonDemo.check1")
        check = JCheckBox(text, createImageIcon("buttons/cb.gif", description))
        check!!.rolloverIcon = createImageIcon("buttons/cbr.gif", description)
        check!!.rolloverSelectedIcon = createImageIcon("buttons/cbrs.gif", description)
        check!!.selectedIcon = createImageIcon("buttons/cbs.gif", description)
        check!!.margin = Insets(0, 0, 0, 0)
        p3.add(check)
        checkboxes.add(check)
        p3.add(Box.createRigidArea(DemoModule.Companion.HGAP20))

        // image checkbox 2
        text = getString("ButtonDemo.check2")
        check = JCheckBox(text, createImageIcon("buttons/cb.gif", description))
        check!!.rolloverIcon = createImageIcon("buttons/cbr.gif", description)
        check!!.rolloverSelectedIcon = createImageIcon("buttons/cbrs.gif", description)
        check!!.selectedIcon = createImageIcon("buttons/cbs.gif", description)
        check!!.margin = Insets(0, 0, 0, 0)
        p3.add(check)
        checkboxes.add(check)
        p3.add(Box.createRigidArea(DemoModule.Companion.HGAP20))

        // image checkbox 3
        text = getString("ButtonDemo.check3")
        check = JCheckBox(text, createImageIcon("buttons/cb.gif", description))
        check!!.rolloverIcon = createImageIcon("buttons/cbr.gif", description)
        check!!.rolloverSelectedIcon = createImageIcon("buttons/cbrs.gif", description)
        check!!.selectedIcon = createImageIcon("buttons/cbs.gif", description)
        check!!.margin = Insets(0, 0, 0, 0)
        p3.add(check)
        checkboxes.add(check)

        // verticaly glue fills out the rest of the box
        p1.add(Box.createVerticalGlue())
        checkboxPanel.add(Box.createHorizontalGlue())
        currentControls = checkboxes
        checkboxPanel.add(createControls())
    }

    fun addToggleButtons() {
        tab.addTab(getString("ButtonDemo.togglebuttons"), toggleButtonPanel)
    }

    fun createControls(): JPanel {
        val controls: JPanel = object : JPanel() {
            override fun getMaximumSize(): Dimension {
                return Dimension(300, super.getMaximumSize().height)
            }
        }
        controls.layout = BoxLayout(controls, BoxLayout.Y_AXIS)
        controls.alignmentY = Component.TOP_ALIGNMENT
        controls.alignmentX = Component.LEFT_ALIGNMENT
        val buttonControls = createHorizontalPanel(true)
        buttonControls!!.alignmentY = Component.TOP_ALIGNMENT
        buttonControls.alignmentX = Component.LEFT_ALIGNMENT
        val leftColumn = createVerticalPanel(false)
        leftColumn!!.alignmentX = Component.LEFT_ALIGNMENT
        leftColumn.alignmentY = Component.TOP_ALIGNMENT
        val rightColumn: JPanel = LayoutControlPanel(this)
        buttonControls.add(leftColumn)
        buttonControls.add(Box.createRigidArea(DemoModule.Companion.HGAP20))
        buttonControls.add(rightColumn)
        buttonControls.add(Box.createRigidArea(DemoModule.Companion.HGAP20))
        controls.add(buttonControls)
        createListeners()

        // Display Options
        var l = JLabel(getString("ButtonDemo.controlpanel_label"))
        leftColumn.add(l)
        val bordered = JCheckBox(getString("ButtonDemo.paintborder"))
        bordered.actionCommand = "PaintBorder"
        bordered.toolTipText = getString("ButtonDemo.paintborder_tooltip")
        bordered.setMnemonic(getMnemonic("ButtonDemo.paintborder_mnemonic"))
        if (currentControls === buttons) {
            bordered.isSelected = true
        }
        bordered.addItemListener(buttonDisplayListener)
        leftColumn.add(bordered)
        val focused = JCheckBox(getString("ButtonDemo.paintfocus"))
        focused.actionCommand = "PaintFocus"
        focused.toolTipText = getString("ButtonDemo.paintfocus_tooltip")
        focused.setMnemonic(getMnemonic("ButtonDemo.paintfocus_mnemonic"))
        focused.isSelected = true
        focused.addItemListener(buttonDisplayListener)
        leftColumn.add(focused)
        val enabled = JCheckBox(getString("ButtonDemo.enabled"))
        enabled.actionCommand = "Enabled"
        enabled.toolTipText = getString("ButtonDemo.enabled_tooltip")
        enabled.isSelected = true
        enabled.addItemListener(buttonDisplayListener)
        enabled.setMnemonic(getMnemonic("ButtonDemo.enabled_mnemonic"))
        leftColumn.add(enabled)
        val filled = JCheckBox(getString("ButtonDemo.contentfilled"))
        filled.actionCommand = "ContentFilled"
        filled.toolTipText = getString("ButtonDemo.contentfilled_tooltip")
        filled.isSelected = true
        filled.addItemListener(buttonDisplayListener)
        filled.setMnemonic(getMnemonic("ButtonDemo.contentfilled_mnemonic"))
        leftColumn.add(filled)
        leftColumn.add(Box.createRigidArea(DemoModule.Companion.VGAP20))
        l = JLabel(getString("ButtonDemo.padamount_label"))
        leftColumn.add(l)
        val group = ButtonGroup()
        val defaultPad = JRadioButton(getString("ButtonDemo.default"))
        defaultPad.toolTipText = getString("ButtonDemo.default_tooltip")
        defaultPad.setMnemonic(getMnemonic("ButtonDemo.default_mnemonic"))
        defaultPad.addItemListener(buttonPadListener)
        group.add(defaultPad)
        defaultPad.isSelected = true
        leftColumn.add(defaultPad)
        val zeroPad = JRadioButton(getString("ButtonDemo.zero"))
        zeroPad.actionCommand = "ZeroPad"
        zeroPad.toolTipText = getString("ButtonDemo.zero_tooltip")
        zeroPad.addItemListener(buttonPadListener)
        zeroPad.setMnemonic(getMnemonic("ButtonDemo.zero_mnemonic"))
        group.add(zeroPad)
        leftColumn.add(zeroPad)
        val tenPad = JRadioButton(getString("ButtonDemo.ten"))
        tenPad.actionCommand = "TenPad"
        tenPad.setMnemonic(getMnemonic("ButtonDemo.ten_mnemonic"))
        tenPad.toolTipText = getString("ButtonDemo.ten_tooltip")
        tenPad.addItemListener(buttonPadListener)
        group.add(tenPad)
        leftColumn.add(tenPad)
        leftColumn.add(Box.createRigidArea(DemoModule.Companion.VGAP20))
        return controls
    }

    fun createListeners() {
        buttonDisplayListener = object : ItemListener {
            var c: Component? = null
            var b: AbstractButton? = null
            override fun itemStateChanged(e: ItemEvent) {
                val cb = e.source as JCheckBox
                val command = cb.actionCommand
                if (command === "Enabled") {
                    for (i in currentControls.indices) {
                        c = currentControls.elementAt(i) as Component
                        c!!.isEnabled = cb.isSelected
                        c!!.invalidate()
                    }
                } else if (command === "PaintBorder") {
                    c = currentControls.elementAt(0) as Component
                    if (c is AbstractButton) {
                        for (i in currentControls.indices) {
                            b = currentControls.elementAt(i) as AbstractButton
                            b!!.isBorderPainted = cb.isSelected
                            b!!.invalidate()
                        }
                    }
                } else if (command === "PaintFocus") {
                    c = currentControls.elementAt(0) as Component
                    if (c is AbstractButton) {
                        for (i in currentControls.indices) {
                            b = currentControls.elementAt(i) as AbstractButton
                            b!!.isFocusPainted = cb.isSelected
                            b!!.invalidate()
                        }
                    }
                } else if (command === "ContentFilled") {
                    c = currentControls.elementAt(0) as Component
                    if (c is AbstractButton) {
                        for (i in currentControls.indices) {
                            b = currentControls.elementAt(i) as AbstractButton
                            b!!.isContentAreaFilled = cb.isSelected
                            b!!.invalidate()
                        }
                    }
                }
                invalidate()
                validate()
                repaint()
            }
        }
        buttonPadListener = object : ItemListener {
            var c: Component? = null
            var b: AbstractButton? = null
            override fun itemStateChanged(e: ItemEvent) {
                // *** pad = 0
                var pad = -1
                val rb = e.source as JRadioButton
                val command = rb.actionCommand
                if (command === "ZeroPad" && rb.isSelected) {
                    pad = 0
                } else if (command === "TenPad" && rb.isSelected) {
                    pad = 10
                }
                for (i in currentControls.indices) {
                    b = currentControls.elementAt(i) as AbstractButton
                    if (pad == -1) {
                        b!!.margin = null
                    } else if (pad == 0) {
                        b!!.margin = insets0
                    } else {
                        b!!.margin = insets10
                    }
                }
                invalidate()
                validate()
                repaint()
            }
        }
    }

    override fun stateChanged(e: ChangeEvent) {
        val model = e.source as SingleSelectionModel
        currentControls = if (model.selectedIndex == 0) {
            buttons
        } else if (model.selectedIndex == 1) {
            radiobuttons
        } else if (model.selectedIndex == 2) {
            checkboxes
        } else {
            togglebuttons
        }
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = ButtonDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * ButtonDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        tab = JTabbedPane()
        tab.model.addChangeListener(this)
        val demo = demoPanel
        demo!!.layout = BoxLayout(demo, BoxLayout.Y_AXIS)
        demo.add(tab)
        addButtons()
        addRadioButtons()
        addCheckBoxes()
        // addToggleButtons();
        currentControls = buttons
    }
}