package com.ogogon.matryoshka.demo

import com.ogogon.matryoshka.BorderLayout
import com.ogogon.matryoshka.JFrame
import com.ogogon.matryoshka.JTree
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeNode

data class BookInfo(val title:String)


private fun createNodes() : TreeNode {
    val top = DefaultMutableTreeNode("Root")
    var category: DefaultMutableTreeNode? = null
    var book: DefaultMutableTreeNode? = null
    category = DefaultMutableTreeNode("Books for Java Programmers")
    top.add(category)

    //original Tutorial
    book = DefaultMutableTreeNode(
        BookInfo(
            "The Java Tutorial: A Short Course on the Basics"
        )
    )
    category.add(book)

    //Tutorial Continued
    book = DefaultMutableTreeNode(
        BookInfo(
            "The Java Tutorial Continued: The Rest of the JDK"
        )
    )
    category.add(book)

    //Swing Tutorial
    book = DefaultMutableTreeNode(
        BookInfo(
            "The Swing Tutorial: A Guide to Constructing GUIs"
        )
    )
    category.add(book)

    //...add more books for programmers...
    category = DefaultMutableTreeNode("Books for Java Implementers")
    top.add(category)

    //VM
    book = DefaultMutableTreeNode(
        BookInfo(
            "The Java Virtual Machine Specification"
        )
    )
    category.add(book)

    //Language Spec
    book = DefaultMutableTreeNode(
        BookInfo(
            "The Java Language Specification"
        )
    )
    category.add(book)
    return top
}

fun main() {
    JFrame {
        title = "New Frame"
        size = Dimension(200, 200)
        BorderLayout {
            JTree(BorderLayout.CENTER) {
                model = DefaultTreeModel(createNodes())
            }
        }
    }
}