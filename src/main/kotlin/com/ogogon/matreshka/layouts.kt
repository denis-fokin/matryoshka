package com.ogogon.matreshka

import java.awt.*
import javax.swing.AbstractButton
import javax.swing.BoxLayout
import javax.swing.ButtonGroup
import javax.swing.JPanel

inline fun <reified T: LayoutManager> Layout(constraints:Any?, function: T.() -> Unit): T = T::class.java.newInstance().apply {
    val panel = JPanel(this)
    lastParent?.add(panel, constraints)
    panel.layout = this
    val previousParent = lastParent
    lastParent = panel
    function.invoke(this)
    lastParent = previousParent
}

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