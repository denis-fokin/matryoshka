package com.ogogon.matryoshka.demo.swingset2

import javax.swing.plaf.ColorUIResource
import javax.swing.plaf.metal.DefaultMetalTheme

class RubyTheme : DefaultMetalTheme() {
    override fun getName(): String {
        return "Ruby"
    }

    private val primary1 = ColorUIResource(80, 10, 22)
    private val primary2 = ColorUIResource(193, 10, 44)
    private val primary3 = ColorUIResource(244, 10, 66)
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