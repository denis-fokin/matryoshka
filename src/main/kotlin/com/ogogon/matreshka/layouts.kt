package com.ogogon.matreshka

import java.awt.*
import javax.swing.*
import javax.swing.border.Border

inline fun <reified T: LayoutManager> Layout(constraints:Any?, function: T.() -> Unit): T = T::class.java.newInstance().apply {
    lastParent?.add(panel, constraints)
    panel.layout = this
    val previousParent = lastParent
    lastParent = panel
    function.invoke(this)
    lastParent = previousParent
}

val <T : LayoutManager> T.panel: JPanel
        by lazy { JPanel() }

var <T : LayoutManager> T.border: Border
        get() = panel.border
        set(value) {panel.border = value}

var <T : LayoutManager> T.alignmentX: Float
        get() = panel.alignmentX
        set(value) {panel.alignmentX = value}

var <T : LayoutManager> T.alignmentY: Float
    get() = panel.alignmentY
    set(value) {panel.alignmentY = value}

val <T : Container> T.buttonGroup: ButtonGroup
    by lazy { ButtonGroup() }

inline fun <reified T: Container> T.addToButtonGroup (button:AbstractButton) {
    buttonGroup.add(button)
}

fun FlowLayout(c:Any? = null, f: FlowLayout.() -> Unit) = Layout(c, f)
fun BorderLayout(c:Any? = null, f: BorderLayout.() -> Unit) = Layout(c, f)
fun GridLayout(c:Any? = null, f: GridLayout.() -> Unit) = Layout(c, f)

fun BoxLayout(panel:JPanel = JPanel(), axis:Int, function: BoxLayout.() -> Unit): BoxLayout = BoxLayout(panel, axis).apply {
    panel.layout = this
    lastParent?.add(panel)
    val previousParent = lastParent
    lastParent = panel
    function.invoke(this)
    lastParent = previousParent
}