package com.ogogon.matreshka.demo.swingset2

import javax.swing.plaf.ColorUIResource
import javax.swing.plaf.metal.DefaultMetalTheme

class AquaTheme : DefaultMetalTheme() {
    override fun getName(): String {
        return "Aqua"
    }

    private val primary1 = ColorUIResource(102, 153, 153)
    private val primary2 = ColorUIResource(128, 192, 192)
    private val primary3 = ColorUIResource(159, 235, 235)
    override fun getPrimary1(): ColorUIResource {
        return primary1
    }

    override fun getPrimary2(): ColorUIResource {
        return primary2
    }

    override fun getPrimary3(): ColorUIResource {
        return primary3
    }
}