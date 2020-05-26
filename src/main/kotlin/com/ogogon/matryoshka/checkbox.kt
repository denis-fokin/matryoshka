package com.ogogon.matryoshka

import javax.swing.JCheckBox

fun JCheckBox(constraints:Any? = null, function: JCheckBox.() -> Unit): JCheckBox = JCheckBox().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}