package com.ogogon.matryoshka.demo

import com.ogogon.matryoshka.BorderLayout
import com.ogogon.matryoshka.JFrame
import com.ogogon.matryoshka.JScrollPane
import com.ogogon.matryoshka.JTable
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.table.DefaultTableModel

fun main() {

    val data = arrayOf(
        arrayOf<Any>(
            "Kathy", "Smith",
            "Snowboarding", 5, false
        ), arrayOf<Any>(
            "John", "Doe",
            "Rowing", 3, true
        ), arrayOf<Any>(
            "Sue", "Black",
            "Knitting", 2, false
        ), arrayOf<Any>(
            "Jane", "White",
            "Speed reading", 20, true
        ), arrayOf<Any>(
            "Joe", "Brown",
            "Pool", 10, false
        )
    )

    val columnNames = arrayOf(
        "First Name",
        "Last Name",
        "Sport",
        "# of Years",
        "Vegetarian"
    )

    JFrame {
        title = "New Frame"
        size = Dimension(200, 200)
        BorderLayout {
            JScrollPane (BorderLayout.CENTER) {
                JTable {
                    model = DefaultTableModel(data, columnNames)
                }
            }
        }
    }
}