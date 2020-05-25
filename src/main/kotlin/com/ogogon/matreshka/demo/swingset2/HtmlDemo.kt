package com.ogogon.matreshka.demo.swingset2

import java.awt.BorderLayout
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import javax.swing.JEditorPane
import javax.swing.JScrollPane
import javax.swing.event.HyperlinkEvent
import javax.swing.event.HyperlinkListener
import javax.swing.text.html.HTMLDocument
import javax.swing.text.html.HTMLFrameHyperlinkEvent

class HtmlDemo(swingset: SwingSet2?) : DemoModule(swingset, "HtmlDemo", "toolbar/JEditorPane.gif") {
    var html: JEditorPane? = null
    fun createHyperLinkListener(): HyperlinkListener {
        return HyperlinkListener { e ->
            if (e.eventType == HyperlinkEvent.EventType.ACTIVATED) {
                if (e is HTMLFrameHyperlinkEvent) {
                    (html!!.document as HTMLDocument).processHTMLFrameHyperlinkEvent(
                            e)
                } else {
                    try {
                        html!!.page = e.url
                    } catch (ioe: IOException) {
                        println("IOE: $ioe")
                    }
                }
            }
        }
    }

    public override fun updateDragEnabled(dragEnabled: Boolean) {
        html!!.dragEnabled = dragEnabled
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = HtmlDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * HtmlDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        try {
            var url: URL? = null
            // System.getProperty("user.dir") +
            // System.getProperty("file.separator");
            var path: String? = null
            try {
                path = "/resources/index.html"
                url = javaClass.getResource(path)
            } catch (e: Exception) {
                System.err.println("Failed to open $path")
                url = null
            }
            if (url != null) {
                html = JEditorPane(url)
                html!!.isEditable = false
                html!!.addHyperlinkListener(createHyperLinkListener())
                val scroller = JScrollPane()
                val vp = scroller.viewport
                vp.add(html)
                demoPanel?.add(scroller, BorderLayout.CENTER)
            }
        } catch (e: MalformedURLException) {
            println("Malformed URL: $e")
        } catch (e: IOException) {
            println("IOException: $e")
        }
    }
}