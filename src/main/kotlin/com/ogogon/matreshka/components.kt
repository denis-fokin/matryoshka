package com.ogogon.matreshka

import javax.swing.JButton
import javax.swing.JLabel

fun JButton(constraints:Any? = null, function: JButton.() -> Unit): JButton = JButton().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JLabel(constraints:Any? = null, function: JLabel.() -> Unit): JLabel = JLabel().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}