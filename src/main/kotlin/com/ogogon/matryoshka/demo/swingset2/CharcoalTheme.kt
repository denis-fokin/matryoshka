package com.ogogon.matryoshka.demo.swingset2

import javax.swing.plaf.ColorUIResource
import javax.swing.plaf.metal.DefaultMetalTheme

class CharcoalTheme : DefaultMetalTheme() {
    override fun getName(): String {
        return "Charcoal"
    }

    private val primary1 = ColorUIResource(66, 33, 66)
    private val primary2 = ColorUIResource(90, 86, 99)
    private val primary3 = ColorUIResource(99, 99, 99)
    private val secondary1 = ColorUIResource(0, 0, 0)
    private val secondary2 = ColorUIResource(51, 51, 51)
    private val secondary3 = ColorUIResource(102, 102, 102)
    private val black = ColorUIResource(222, 222, 222)
    private val white = ColorUIResource(0, 0, 0)
    override fun getPrimary1(): ColorUIResource {
        return primary1
    }

    override fun getPrimary2(): ColorUIResource {
        return primary2
    }

    override fun getPrimary3(): ColorUIResource {
        return primary3
    }

    override fun getSecondary1(): ColorUIResource {
        return secondary1
    }

    override fun getSecondary2(): ColorUIResource {
        return secondary2
    }

    override fun getSecondary3(): ColorUIResource {
        return secondary3
    }

    override fun getBlack(): ColorUIResource {
        return black
    }

    override fun getWhite(): ColorUIResource {
        return white
    }
}