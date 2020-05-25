package com.ogogon.matreshka.demo.swingset2

import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.swing.*
import javax.swing.border.Border
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder
import javax.swing.border.SoftBevelBorder

open class DemoModule @JvmOverloads constructor(swingset: SwingSet2?, private var resourceName: String? = null, iconPath: String? = null) : JApplet() {
    // The preferred size of the demo
    private val PREFERRED_WIDTH = 680
    private val PREFERRED_HEIGHT = 600
    var loweredBorder: Border = CompoundBorder(SoftBevelBorder(SoftBevelBorder.LOWERED),
            EmptyBorder(5, 5, 5, 5))
    var swingSet2: SwingSet2? = null
    var demoPanel: JPanel? = null
    private val iconPath: String? = null
    var sourceCode: String? = null
        private set

    fun getString(key: String): String {
        return if (swingSet2 != null) {
            SwingSet2.Companion.getString(key)
        } else {
            "nada"
        }
    }

    fun getMnemonic(key: String): Char {
        return getString(key)[0]
    }

    fun createImageIcon(filename: String?, description: String?): ImageIcon? {
        return if (swingSet2 != null) {
            createImageIcon(filename, description)
        } else {
            val path = "/resources/images/$filename"
            ImageIcon(javaClass.getResource(path), description)
        }
    }

    fun loadSourceCode() {
        if (resourceName != null) {
            val filename = "$resourceName.java"
            sourceCode = "<html><body bgcolor=\"#ffffff\"><pre>"
            val `is`: InputStream
            val isr: InputStreamReader
            val url: URL
            try {
                url = javaClass.getResource(filename)
                `is` = url.openStream()
                isr = InputStreamReader(`is`, "UTF-8")
                val reader = BufferedReader(isr)

                // Read one line at a time, htmlize using super-spiffy
                // html java code formating utility from www.CoolServlets.com
                var line = reader.readLine()
                while (line != null) {
                    sourceCode += "$line \n "
                    line = reader.readLine()
                }
                sourceCode += "</pre></body></html>"
            } catch (ex: Exception) {
                sourceCode = "Could not load file: $filename"
            }
        }
    }

    override fun getName(): String {
        return getString("$resourceName.name")
    }

    val icon: Icon?
        get() = createImageIcon(iconPath, "$resourceName.name")

    val toolTip: String
        get() = getString("$resourceName.tooltip")

    fun mainImpl() {
        val frame = JFrame(name)
        frame.contentPane.layout = BorderLayout()
        frame.contentPane.add(demoPanel, BorderLayout.CENTER)
        demoPanel!!.preferredSize = Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT)
        frame.pack()
        frame.show()
    }

    fun createHorizontalPanel(threeD: Boolean): JPanel {
        val p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.X_AXIS)
        p.alignmentY = Component.TOP_ALIGNMENT
        p.alignmentX = Component.LEFT_ALIGNMENT
        if (threeD) {
            p.border = loweredBorder
        }
        return p
    }

    fun createVerticalPanel(threeD: Boolean): JPanel {
        val p = JPanel()
        p.layout = BoxLayout(p, BoxLayout.Y_AXIS)
        p.alignmentY = Component.TOP_ALIGNMENT
        p.alignmentX = Component.LEFT_ALIGNMENT
        if (threeD) {
            p.border = loweredBorder
        }
        return p
    }

    override fun init() {
        contentPane.layout = BorderLayout()
        contentPane.add(demoPanel, BorderLayout.CENTER)
    }

    open fun updateDragEnabled(dragEnabled: Boolean) {}

    companion object {
        // Premade convenience dimensions, for use wherever you need 'em.
        var HGAP2 = Dimension(2, 1)
        var VGAP2 = Dimension(1, 2)
        var HGAP5 = Dimension(5, 1)
        var VGAP5 = Dimension(1, 5)
        var HGAP10 = Dimension(10, 1)
        var VGAP10 = Dimension(1, 10)
        var HGAP15 = Dimension(15, 1)
        var VGAP15 = Dimension(1, 15)
        var HGAP20 = Dimension(20, 1)
        var VGAP20 = Dimension(1, 20)
        var HGAP25 = Dimension(25, 1)
        var VGAP25 = Dimension(1, 25)
        var HGAP30 = Dimension(30, 1)
        var VGAP30 = Dimension(1, 30)

        @JvmStatic
        fun main(args: Array<String>) {
            val demo = DemoModule(null)
            demo.mainImpl()
        }
    }

    init {
        UIManager.put("swing.boldMetal", java.lang.Boolean.FALSE)
        demoPanel = JPanel()
        swingSet2 = swingset
        loadSourceCode()
    }
}