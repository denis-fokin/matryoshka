package com.ogogon.matreshka.demo.swingset2

import javax.swing.plaf.ColorUIResource
import javax.swing.plaf.metal.DefaultMetalTheme

class EmeraldTheme : DefaultMetalTheme() {
    override fun getName(): String {
        return "Emerald"
    }

    private val primary1 = ColorUIResource(51, 142, 71)
    private val primary2 = ColorUIResource(102, 193, 122)
    private val primary3 = ColorUIResource(153, 244, 173)
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