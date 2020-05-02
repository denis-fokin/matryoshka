package com.ogogon.matreshka.demo

import javax.swing.ImageIcon

fun createImageIcon(path: String): ImageIcon? {
    val imgURL = Thread.currentThread().contextClassLoader.getResource(path)
    return if (imgURL != null) {
        ImageIcon(imgURL)
    } else {
        System.err.println("Couldn't find file: $path")
        null
    }
}