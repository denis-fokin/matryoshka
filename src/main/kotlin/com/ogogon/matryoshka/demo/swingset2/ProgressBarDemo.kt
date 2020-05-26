package com.ogogon.matryoshka.demo.swingset2

import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.border.BevelBorder
import javax.swing.border.SoftBevelBorder

class ProgressBarDemo(swingset: SwingSet2?) : DemoModule(swingset, "ProgressBarDemo", "toolbar/JProgressBar.gif") {
    var timer = Timer(18, createTextLoadAction())
    var loadAction: Action? = null
    var stopAction: Action? = null
    var progressBar: JProgressBar? = null
    var progressTextArea: JTextArea? = null
    public override fun updateDragEnabled(dragEnabled: Boolean) {
        progressTextArea!!.dragEnabled = dragEnabled
    }

    fun createProgressPanel() {
        demoPanel?.layout = BorderLayout()
        val textWrapper = JPanel(BorderLayout())
        textWrapper.border = SoftBevelBorder(BevelBorder.LOWERED)
        textWrapper.alignmentX = Component.LEFT_ALIGNMENT
        progressTextArea = MyTextArea()
        progressTextArea?.accessibleContext?.accessibleName = getString("ProgressBarDemo.accessible_text_area_name")
        progressTextArea?.accessibleContext?.accessibleName = getString("ProgressBarDemo.accessible_text_area_description")
        textWrapper.add(JScrollPane(progressTextArea), BorderLayout.CENTER)
        demoPanel?.add(textWrapper, BorderLayout.CENTER)
        val progressPanel = JPanel()
        demoPanel?.add(progressPanel, BorderLayout.SOUTH)
        progressBar = object : JProgressBar(HORIZONTAL, 0, text!!.length) {
            override fun getPreferredSize(): Dimension {
                return Dimension(300, super.getPreferredSize().height)
            }
        }
        progressBar?.accessibleContext?.accessibleName = getString("ProgressBarDemo.accessible_text_loading_progress")
        progressPanel.add(progressBar)
        progressPanel.add(createLoadButton())
        progressPanel.add(createStopButton())
    }

    fun createLoadButton(): JButton {
        loadAction = object : AbstractAction(getString("ProgressBarDemo.start_button")) {
            override fun actionPerformed(e: ActionEvent) {
                loadAction!!.isEnabled = false
                stopAction!!.isEnabled = true
                if (progressBar!!.value == progressBar!!.maximum) {
                    progressBar!!.value = 0
                    textLocation = 0
                    progressTextArea!!.text = ""
                }
                timer.start()
            }
        }
        return createButton(loadAction)
    }

    fun createStopButton(): JButton {
        stopAction = object : AbstractAction(getString("ProgressBarDemo.stop_button")) {
            override fun actionPerformed(e: ActionEvent) {
                timer.stop()
                loadAction!!.isEnabled = true
                stopAction!!.isEnabled = false
            }
        }
        return createButton(stopAction)
    }

    fun createButton(a: Action?): JButton {
        val b = JButton()
        // setting the following client property informs the button to show
        // the action text as it's name. The default is to not show the
        // action text.
        b.putClientProperty("displayActionText", java.lang.Boolean.TRUE)
        b.action = a
        return b
    }

    var textLocation = 0
    var text = getString("ProgressBarDemo.text")
    fun createTextLoadAction(): Action {
        return object : AbstractAction("text load action") {
            override fun actionPerformed(e: ActionEvent) {
                if (progressBar!!.value < progressBar!!.maximum) {
                    progressBar!!.value = progressBar!!.value + 1
                    progressTextArea!!.append(text!!.substring(textLocation, textLocation + 1))
                    textLocation++
                } else {
                    timer.stop()
                    loadAction!!.isEnabled = true
                    stopAction!!.isEnabled = false
                }
            }
        }
    }

    internal inner class MyTextArea : JTextArea(null, 0, 0) {
        override fun getAlignmentX(): Float {
            return Component.LEFT_ALIGNMENT
        }

        override fun getAlignmentY(): Float {
            return Component.TOP_ALIGNMENT
        }

        init {
            isEditable = false
            text = ""
        }
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = ProgressBarDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * ProgressBarDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        createProgressPanel()
    }
}