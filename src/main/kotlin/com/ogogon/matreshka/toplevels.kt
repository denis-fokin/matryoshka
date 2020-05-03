package com.ogogon.matreshka

import javax.swing.JFrame

fun JFrame(function: JFrame.() -> Unit): JFrame = JFrame().apply {
    saveParent(this) {
        function.invoke(this)
        this.pack()
        this.isVisible = true
    }
}