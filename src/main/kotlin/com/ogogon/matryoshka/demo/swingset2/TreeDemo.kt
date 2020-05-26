package com.ogogon.matryoshka.demo.swingset2

import java.awt.BorderLayout
import java.awt.Insets
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode

class TreeDemo(swingset: SwingSet2?) : DemoModule(swingset, "TreeDemo", "toolbar/JTree.gif") {
    var tree: JTree? = null
    fun createTree(): JScrollPane {
        val top = DefaultMutableTreeNode(getString("TreeDemo.music"))
        var catagory: DefaultMutableTreeNode? = null
        var artist: DefaultMutableTreeNode? = null
        var record: DefaultMutableTreeNode? = null

        // open tree data
        val url = javaClass.getResource("/resources/tree.txt")
        try {
            // convert url to buffered string
            val `is` = url.openStream()
            val isr = InputStreamReader(`is`, "UTF-8")
            val reader = BufferedReader(isr)

            // read one line at a time, put into tree
            var line = reader.readLine()
            while (line != null) {
                // System.out.println("reading in: ->" + line + "<-");
                val linetype = line[0]
                when (linetype) {
                    'C' -> {
                        catagory = DefaultMutableTreeNode(line.substring(2))
                        top.add(catagory)
                    }
                    'A' -> catagory?.add(DefaultMutableTreeNode(line.substring(2)).also { artist = it })
                    'R' -> if (artist != null) {
                        artist!!.add(DefaultMutableTreeNode(line.substring(2)).also { record = it })
                    }
                    'S' -> if (record != null) {
                        record!!.add(DefaultMutableTreeNode(line.substring(2)))
                    }
                    else -> {
                    }
                }
                line = reader.readLine()
            }
        } catch (e: IOException) {
        }
        tree = object : JTree(top) {
            override fun getInsets(): Insets {
                return Insets(5, 5, 5, 5)
            }
        }
        tree?.isEditable = true
        return JScrollPane(tree)
    }

    public override fun updateDragEnabled(dragEnabled: Boolean) {
        tree!!.dragEnabled = dragEnabled
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = TreeDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * TreeDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        demoPanel?.add(createTree(), BorderLayout.CENTER)
    }
}