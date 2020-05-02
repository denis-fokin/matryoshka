package com.ogogon.matreshka

import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.LayoutManager
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

fun FlowLayout(c:Any? = null, f: FlowLayout.() -> Unit) = Layout(c, f)
fun BorderLayout(c:Any? = null, f: BorderLayout.() -> Unit) = Layout(c, f)
fun GridLayout(c:Any? = null, f: GridLayout.() -> Unit) = Layout(c, f)
