package com.ogogon.matryoshka

import java.awt.Container

var lastParent: Container? = null

fun saveParent (currentParent: Container, function: () -> Unit) {
    val previousParent = lastParent
    lastParent = currentParent
    function.invoke()
    lastParent = previousParent
}