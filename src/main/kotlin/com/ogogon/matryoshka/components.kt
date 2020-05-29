package com.ogogon.matryoshka

import javax.swing.*

fun JButton(constraints:Any? = null, function: JButton.() -> Unit): JButton = JButton().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JLabel(constraints:Any? = null, function: JLabel.() -> Unit): JLabel = JLabel().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JTextField(constraints:Any? = null, function: JTextField.() -> Unit): JTextField = JTextField().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JTextArea(constraints:Any? = null, function: JTextArea.() -> Unit): JTextArea = JTextArea().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JComboBox(constraints:Any? = null, function: JComboBox<Any?>.() -> Unit): JComboBox<Any?> = JComboBox<Any?>().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JRadioButton(constraints:Any? = null, function: JRadioButton.() -> Unit): JRadioButton = JRadioButton().apply {
    lastParent?.addToButtonGroup(this)
    function.invoke(this)
    lastParent?.add(this, constraints)
}
