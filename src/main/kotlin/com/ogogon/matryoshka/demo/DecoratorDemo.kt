package com.ogogon.matryoshka.demo

import com.ogogon.matryoshka.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.geom.AffineTransform
import java.io.File
import javax.imageio.ImageIO

fun main() {
    JFrame {
        title = "New Frame"
        size = Dimension(200, 200)
        BorderLayout {
            val bufferedImage = ImageIO.read(Thread.currentThread().contextClassLoader.getResource("box_fight.jpg"))
            AffineImage(c = BorderLayout.CENTER) {
                image = bufferedImage
                var at = AffineTransform()
                at.preConcatenate(AffineTransform.getRotateInstance(Math.toRadians(45.0)))
                at.preConcatenate(AffineTransform.getScaleInstance(0.2, 0.2))
                tx = at
            }
        }
    }
}


