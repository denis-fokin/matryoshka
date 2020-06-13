package com.ogogon.matryoshka.demo

import com.ogogon.matryoshka.*
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JButton

fun main() {
    JFrame {
        title = "New Frame"
        size = Dimension(200, 200)
        BorderLayout {
            JButton(BorderLayout.NORTH, decorator = Decorator().scale(2.0)) {
                text = "N"
                onClick {
                    println("Mouse Clicked")
                }
            }
            JButton(BorderLayout.EAST) {
                text = "E"
                onClick {
                    println("Mouse Clicked")
                }
            }
            JButton(BorderLayout.SOUTH) {
                text = "S"
                onClick {
                    println("Mouse Clicked")
                }
            }
            JButton(BorderLayout.WEST) {
                text = "W"
                onClick {
                    println("Mouse Clicked")
                }
            }
        }
    }
}


