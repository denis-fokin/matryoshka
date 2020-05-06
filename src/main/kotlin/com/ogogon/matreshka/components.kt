package com.ogogon.matreshka

import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.JRadioButton

fun JButton(constraints:Any? = null, function: JButton.() -> Unit): JButton = JButton().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JLabel(constraints:Any? = null, function: JLabel.() -> Unit): JLabel = JLabel().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JComboBox(constraints:Any? = null, function: JComboBox<Any?>.() -> Unit): JComboBox<Any?> = JComboBox<Any?>().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JRadioButton(constraints:Any? = null, function: JRadioButton.() -> Unit): JRadioButton = JRadioButton().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}
