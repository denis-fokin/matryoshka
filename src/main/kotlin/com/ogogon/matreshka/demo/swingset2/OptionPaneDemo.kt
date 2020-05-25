package com.ogogon.matreshka.demo.swingset2

import java.awt.Dimension
import java.awt.event.ActionEvent
import javax.swing.*

class OptionPaneDemo(swingset: SwingSet2?) : DemoModule(swingset, "OptionPaneDemo", "toolbar/JOptionPane.gif") {
    fun createWarningDialogButton(): JButton {
        val a: Action = object : AbstractAction(getString("OptionPaneDemo.warningbutton")) {
            override fun actionPerformed(e: ActionEvent) {
                JOptionPane.showMessageDialog(
                        demoPanel,
                        getString("OptionPaneDemo.warningtext"),
                        getString("OptionPaneDemo.warningtitle"),
                        JOptionPane.WARNING_MESSAGE
                )
            }
        }
        return createButton(a)
    }

    fun createMessageDialogButton(): JButton {
        val a: Action = object : AbstractAction(getString("OptionPaneDemo.messagebutton")) {
            var img = javaClass.getResource("/resources/images/optionpane/bottle.gif")
            var imagesrc = "<img src=\"$img\" width=\"284\" height=\"100\">"
            var message = getString("OptionPaneDemo.messagetext")
            override fun actionPerformed(e: ActionEvent) {
                JOptionPane.showMessageDialog(
                        demoPanel,
                        "<html>$imagesrc<br><center>$message</center><br></html>"
                )
            }
        }
        return createButton(a)
    }

    fun createConfirmDialogButton(): JButton {
        val a: Action = object : AbstractAction(getString("OptionPaneDemo.confirmbutton")) {
            override fun actionPerformed(e: ActionEvent) {
                val result = JOptionPane.showConfirmDialog(demoPanel, getString("OptionPaneDemo.confirmquestion"))
                if (result == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(demoPanel, getString("OptionPaneDemo.confirmyes"))
                } else if (result == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(demoPanel, getString("OptionPaneDemo.confirmno"))
                }
            }
        }
        return createButton(a)
    }

    fun createInputDialogButton(): JButton {
        val a: Action = object : AbstractAction(getString("OptionPaneDemo.inputbutton")) {
            override fun actionPerformed(e: ActionEvent) {
                val result = JOptionPane.showInputDialog(demoPanel, getString("OptionPaneDemo.inputquestion"))
                if (result != null && result.length > 0) {
                    JOptionPane.showMessageDialog(demoPanel,
                            result + ": " +
                                    getString("OptionPaneDemo.inputresponse"))
                }
            }
        }
        return createButton(a)
    }

    fun createComponentDialogButton(): JButton {
        val a: Action = object : AbstractAction(getString("OptionPaneDemo.componentbutton")) {
            override fun actionPerformed(e: ActionEvent) {
                // In a ComponentDialog, you can show as many message components and
                // as many options as you want:

                // Messages
                val message = arrayOfNulls<Any>(4)
                message[0] = getString("OptionPaneDemo.componentmessage")
                message[1] = JTextField(getString("OptionPaneDemo.componenttextfield"))
                val cb = JComboBox<Any?>()
                cb.addItem(getString("OptionPaneDemo.component_cb1"))
                cb.addItem(getString("OptionPaneDemo.component_cb2"))
                cb.addItem(getString("OptionPaneDemo.component_cb3"))
                message[2] = cb
                message[3] = getString("OptionPaneDemo.componentmessage2")

                // Options
                val options = arrayOf(
                        getString("OptionPaneDemo.component_op1"),
                        getString("OptionPaneDemo.component_op2"),
                        getString("OptionPaneDemo.component_op3"),
                        getString("OptionPaneDemo.component_op4"),
                        getString("OptionPaneDemo.component_op5")
                )
                val result = JOptionPane.showOptionDialog(
                        demoPanel,  // the parent that the dialog blocks
                        message,  // the dialog message array
                        getString("OptionPaneDemo.componenttitle"),  // the title of the dialog window
                        JOptionPane.DEFAULT_OPTION,  // option type
                        JOptionPane.INFORMATION_MESSAGE,  // message type
                        null,  // optional icon, use null to use the default icon
                        options,  // options string array, will be made into buttons
                        options[3] // option that should be made into a default button
                )
                when (result) {
                    0 -> JOptionPane.showMessageDialog(demoPanel, getString("OptionPaneDemo.component_r1"))
                    1 -> JOptionPane.showMessageDialog(demoPanel, getString("OptionPaneDemo.component_r2"))
                    2 -> JOptionPane.showMessageDialog(demoPanel, getString("OptionPaneDemo.component_r3"))
                    3 -> JOptionPane.showMessageDialog(demoPanel, getString("OptionPaneDemo.component_r4"))
                    else -> {
                    }
                }
            }
        }
        return createButton(a)
    }

    fun createButton(a: Action?): JButton {
        val b: JButton = object : JButton() {
            override fun getMaximumSize(): Dimension {
                val width = Short.MAX_VALUE.toInt()
                val height = super.getMaximumSize().height
                return Dimension(width, height)
            }
        }
        // setting the following client property informs the button to show
        // the action text as it's name. The default is to not show the
        // action text.
        b.putClientProperty("displayActionText", true)
        b.action = a
        // b.setAlignmentX(JButton.CENTER_ALIGNMENT);
        return b
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = OptionPaneDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * OptionPaneDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        val demo = demoPanel
        demo!!.layout = BoxLayout(demo, BoxLayout.X_AXIS)
        val bp: JPanel = object : JPanel() {
            override fun getMaximumSize(): Dimension {
                return Dimension(preferredSize.width, super.getMaximumSize().height)
            }
        }
        bp.layout = BoxLayout(bp, BoxLayout.Y_AXIS)
        bp.add(Box.createRigidArea(DemoModule.Companion.VGAP30))
        bp.add(Box.createRigidArea(DemoModule.Companion.VGAP30))
        bp.add(createInputDialogButton())
        bp.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        bp.add(createWarningDialogButton())
        bp.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        bp.add(createMessageDialogButton())
        bp.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        bp.add(createComponentDialogButton())
        bp.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        bp.add(createConfirmDialogButton())
        bp.add(Box.createVerticalGlue())
        demo.add(Box.createHorizontalGlue())
        demo.add(bp)
        demo.add(Box.createHorizontalGlue())
    }
}