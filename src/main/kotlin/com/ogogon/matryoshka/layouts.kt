package com.ogogon.matryoshka

import java.awt.*
import java.util.*
import javax.swing.*
import javax.swing.border.Border

val layoutsToContainers = WeakHashMap<LayoutManager, JPanel?>()
val containersToButtonGroup = WeakHashMap<Container, ButtonGroup?>()

inline fun <reified T: LayoutManager> Layout(constraints:Any?, function: T.() -> Unit): T
        = T::class.java.getDeclaredConstructor().newInstance().apply {
    lastParent?.add(panel, constraints)
    panel.layout = this
    val previousParent = lastParent
    lastParent = panel
    function.invoke(this)
    lastParent = previousParent
}

val <T : LayoutManager> T.panel: JPanel
        get () {
            if (!layoutsToContainers.containsKey(this)) {
                layoutsToContainers[this] = JPanel()
            }
            return layoutsToContainers[this]!!
        }

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
    get () {
        if (!containersToButtonGroup.containsKey(this)) {
            containersToButtonGroup[this] = ButtonGroup()
        }
        return containersToButtonGroup[this]!!
    }

inline fun <reified T: Container> T.addToButtonGroup (button:AbstractButton) {
    buttonGroup.add(button)
}

fun FlowLayout(c:Any? = null, f: FlowLayout.() -> Unit) = Layout(c, f)
fun BorderLayout(c:Any? = null, f: BorderLayout.() -> Unit) = Layout(c, f)
fun GridLayout(c:Any? = null, f: GridLayout.() -> Unit) = Layout(c, f)

fun JScrollPane(constraints:Any? = null, f: JScrollPane.() -> Unit)
        = JScrollPane::class.java.getDeclaredConstructor().newInstance().apply {
    lastParent?.add(this, constraints)
    val previousParent = lastParent
    lastParent = this
    f.invoke(this)
    lastParent = previousParent
}

fun BoxLayout(panel:JPanel = JPanel(), axis:Int, function: BoxLayout.() -> Unit): BoxLayout = BoxLayout(panel, axis).apply {
    panel.layout = this
    lastParent?.add(panel)
    val previousParent = lastParent
    lastParent = panel
    function.invoke(this)
    lastParent = previousParent
}