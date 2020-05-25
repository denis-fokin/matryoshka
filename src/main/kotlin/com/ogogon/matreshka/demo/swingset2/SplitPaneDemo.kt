package com.ogogon.matreshka.demo.swingset2

import java.awt.*
import javax.swing.*

class SplitPaneDemo(swingset: SwingSet2?) : DemoModule(swingset, "SplitPaneDemo", "toolbar/JSplitPane.gif") {
    var splitPane: JSplitPane? = null
    var earth: JLabel? = null
    var moon: JLabel? = null
    var divSize: JTextField? = null
    var earthSize: JTextField? = null
    var moonSize: JTextField? = null

    /**
     * Creates controls to alter the JSplitPane.
     */
    protected fun createSplitPaneControls(): JPanel {
        val wrapper = JPanel()
        val group = ButtonGroup()
        var button: JRadioButton
        val buttonWrapper = Box(BoxLayout.X_AXIS)
        wrapper.layout = GridLayout(0, 1)

        /* Create a radio button to vertically split the split pane. */button = JRadioButton(getString("SplitPaneDemo.vert_split"))
        button.setMnemonic(getMnemonic("SplitPaneDemo.vert_split_mnemonic"))
        button.addActionListener { splitPane!!.orientation = JSplitPane.VERTICAL_SPLIT }
        group.add(button)
        buttonWrapper.add(button)

        /* Create a radio button the horizontally split the split pane. */button = JRadioButton(getString("SplitPaneDemo.horz_split"))
        button.setMnemonic(getMnemonic("SplitPaneDemo.horz_split_mnemonic"))
        button.isSelected = true
        button.addActionListener { splitPane!!.orientation = JSplitPane.HORIZONTAL_SPLIT }
        group.add(button)
        buttonWrapper.add(button)

        /* Create a check box as to whether or not the split pane continually
           lays out the component when dragging. */
        var checkBox = JCheckBox(getString("SplitPaneDemo.cont_layout"))
        checkBox.setMnemonic(getMnemonic("SplitPaneDemo.cont_layout_mnemonic"))
        checkBox.isSelected = true
        checkBox.addChangeListener { e -> splitPane!!.isContinuousLayout = (e.source as JCheckBox).isSelected }
        buttonWrapper.add(checkBox)

        /* Create a check box as to whether or not the split pane divider
           contains the oneTouchExpandable buttons. */checkBox = JCheckBox(getString("SplitPaneDemo.one_touch_expandable"))
        checkBox.setMnemonic(getMnemonic("SplitPaneDemo.one_touch_expandable_mnemonic"))
        checkBox.isSelected = true
        checkBox.addChangeListener { e -> splitPane!!.isOneTouchExpandable = (e.source as JCheckBox).isSelected }
        buttonWrapper.add(checkBox)
        wrapper.add(buttonWrapper)

        /* Create a text field to change the divider size. */
        var tfWrapper: JPanel
        var label: JLabel
        divSize = JTextField()
        divSize!!.text = splitPane!!.dividerSize.toString()
        divSize!!.columns = 5
        divSize!!.accessibleContext.accessibleName = getString("SplitPaneDemo.divider_size")
        divSize!!.addActionListener { e ->
            val value = (e.source as JTextField).text
            val newSize: Int
            newSize = try {
                value.toInt()
            } catch (ex: Exception) {
                -1
            }
            if (newSize > 0) {
                splitPane!!.dividerSize = newSize
            } else {
                JOptionPane.showMessageDialog(splitPane,
                        getString("SplitPaneDemo.invalid_divider_size"),
                        getString("SplitPaneDemo.error"),
                        JOptionPane.ERROR_MESSAGE)
            }
        }
        label = JLabel(getString("SplitPaneDemo.divider_size"))
        tfWrapper = JPanel(FlowLayout(FlowLayout.LEFT))
        tfWrapper.add(label)
        tfWrapper.add(divSize)
        label.labelFor = divSize
        label.setDisplayedMnemonic(getMnemonic("SplitPaneDemo.divider_size_mnemonic"))
        wrapper.add(tfWrapper)

        /* Create a text field that will change the preferred/minimum size
           of the earth component. */earthSize = JTextField(earth!!.minimumSize.width.toString())
        earthSize!!.columns = 5
        earthSize!!.accessibleContext.accessibleName = getString("SplitPaneDemo.first_component_min_size")
        earthSize!!.addActionListener { e ->
            val value = (e.source as JTextField).text
            val newSize: Int
            newSize = try {
                value.toInt()
            } catch (ex: Exception) {
                -1
            }
            if (newSize > 10) {
                earth!!.minimumSize = Dimension(newSize, newSize)
            } else {
                JOptionPane.showMessageDialog(splitPane,
                        getString("SplitPaneDemo.invalid_min_size") +
                                getString("SplitPaneDemo.must_be_greater_than") + 10,
                        getString("SplitPaneDemo.error"),
                        JOptionPane.ERROR_MESSAGE)
            }
        }
        label = JLabel(getString("SplitPaneDemo.first_component_min_size"))
        tfWrapper = JPanel(FlowLayout(FlowLayout.LEFT))
        tfWrapper.add(label)
        tfWrapper.add(earthSize)
        label.labelFor = earthSize
        label.setDisplayedMnemonic(getMnemonic("SplitPaneDemo.first_component_min_size_mnemonic"))
        wrapper.add(tfWrapper)

        /* Create a text field that will change the preferred/minimum size
           of the moon component. */moonSize = JTextField(moon!!.minimumSize.width.toString())
        moonSize!!.columns = 5
        moonSize!!.accessibleContext.accessibleName = getString("SplitPaneDemo.second_component_min_size")
        moonSize!!.addActionListener { e ->
            val value = (e.source as JTextField).text
            val newSize: Int
            newSize = try {
                value.toInt()
            } catch (ex: Exception) {
                -1
            }
            if (newSize > 10) {
                moon!!.minimumSize = Dimension(newSize, newSize)
            } else {
                JOptionPane.showMessageDialog(splitPane,
                        getString("SplitPaneDemo.invalid_min_size") +
                                getString("SplitPaneDemo.must_be_greater_than") + 10,
                        getString("SplitPaneDemo.error"),
                        JOptionPane.ERROR_MESSAGE)
            }
        }
        label = JLabel(getString("SplitPaneDemo.second_component_min_size"))
        tfWrapper = JPanel(FlowLayout(FlowLayout.LEFT))
        tfWrapper.add(label)
        tfWrapper.add(moonSize)
        label.labelFor = moonSize
        label.setDisplayedMnemonic(getMnemonic("SplitPaneDemo.second_component_min_size_mnemonic"))
        wrapper.add(tfWrapper)
        return wrapper
    }

    public override fun updateDragEnabled(dragEnabled: Boolean) {
        divSize!!.dragEnabled = dragEnabled
        earthSize!!.dragEnabled = dragEnabled
        moonSize!!.dragEnabled = dragEnabled
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = SplitPaneDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * SplitPaneDemo Constructor
     */
    init {
        earth = JLabel(createImageIcon("splitpane/earth.jpg", getString("SplitPaneDemo.earth")))
        earth!!.minimumSize = Dimension(20, 20)
        moon = JLabel(createImageIcon("splitpane/moon.jpg", getString("SplitPaneDemo.moon")))
        moon!!.minimumSize = Dimension(20, 20)
        splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, earth, moon)
        splitPane!!.isContinuousLayout = true
        splitPane!!.isOneTouchExpandable = true
        splitPane!!.dividerLocation = 200
        demoPanel?.add(splitPane, BorderLayout.CENTER)
        demoPanel?.background = Color.black
        demoPanel?.add(createSplitPaneControls(), BorderLayout.SOUTH)
    }
}