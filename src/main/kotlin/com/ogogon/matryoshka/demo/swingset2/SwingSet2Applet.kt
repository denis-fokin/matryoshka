package com.ogogon.matryoshka.demo.swingset2

import java.awt.BorderLayout
import java.net.MalformedURLException
import java.net.URL
import javax.swing.JApplet

class SwingSet2Applet : JApplet() {
    override fun init() {
        contentPane.layout = BorderLayout()
        contentPane.add(SwingSet2(this), BorderLayout.CENTER)
    }

    fun getURL(filename: String?): URL? {
        val codeBase = this.codeBase
        var url: URL? = null
        try {
            url = URL(codeBase, filename)
            println(url)
        } catch (e: MalformedURLException) {
            println("Error: badly specified URL")
            return null
        }
        return url
    }
}