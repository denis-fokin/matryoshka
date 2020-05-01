package com.ogogon.matreshka

import java.awt.BorderLayout
import java.awt.FlowLayout

fun FlowLayout(function: (thisLayout: FlowLayout) -> Unit): FlowLayout = FlowLayout().apply {
    lastParent?.layout = this
    function.invoke(this)
}

fun BorderLayout(function: (thisLayout: BorderLayout) -> Unit): BorderLayout = BorderLayout().apply {
    lastParent?.layout = this
    function.invoke(this)
}