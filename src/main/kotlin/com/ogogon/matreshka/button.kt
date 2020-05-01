package com.ogogon.matreshka

import javax.swing.JButton

fun JButton(constraints:Any? = null, function: JButton.() -> Unit): JButton = JButton().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}