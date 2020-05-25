package com.ogogon.matreshka.demo.swingset2

import javax.swing.UIDefaults
import javax.swing.border.Border
import javax.swing.border.CompoundBorder
import javax.swing.border.LineBorder
import javax.swing.plaf.BorderUIResource
import javax.swing.plaf.ColorUIResource
import javax.swing.plaf.basic.BasicBorders.MarginBorder
import javax.swing.plaf.metal.DefaultMetalTheme

class ContrastTheme : DefaultMetalTheme() {
    override fun getName(): String {
        return "Contrast"
    }

    private val primary1 = ColorUIResource(0, 0, 0)
    private val primary2 = ColorUIResource(204, 204, 204)
    private val primary3 = ColorUIResource(255, 255, 255)
    private val primaryHighlight = ColorUIResource(102, 102, 102)
    private val secondary2 = ColorUIResource(204, 204, 204)
    private val secondary3 = ColorUIResource(255, 255, 255)
    private val controlHighlight = ColorUIResource(102, 102, 102)
    override fun getPrimary1(): ColorUIResource {
        return primary1
    }

    override fun getPrimary2(): ColorUIResource {
        return primary2
    }

    override fun getPrimary3(): ColorUIResource {
        return primary3
    }

    override fun getPrimaryControlHighlight(): ColorUIResource {
        return primaryHighlight
    }

    override fun getSecondary2(): ColorUIResource {
        return secondary2
    }

    override fun getSecondary3(): ColorUIResource {
        return secondary3
    }

    override fun getControlHighlight(): ColorUIResource {
        return super.getSecondary3()
    }

    override fun getFocusColor(): ColorUIResource {
        return black
    }

    override fun getTextHighlightColor(): ColorUIResource {
        return black
    }

    override fun getHighlightedTextColor(): ColorUIResource {
        return white
    }

    override fun getMenuSelectedBackground(): ColorUIResource {
        return black
    }

    override fun getMenuSelectedForeground(): ColorUIResource {
        return white
    }

    override fun getAcceleratorForeground(): ColorUIResource {
        return black
    }

    override fun getAcceleratorSelectedForeground(): ColorUIResource {
        return white
    }

    override fun addCustomEntriesToTable(table: UIDefaults) {
        val blackLineBorder: Border = BorderUIResource(LineBorder(black))
        val textBorder: Any = BorderUIResource(CompoundBorder(
                blackLineBorder,
                MarginBorder()))
        table["ToolTip.border"] = blackLineBorder
        table["TitledBorder.border"] = blackLineBorder
        table["TextField.border"] = textBorder
        table["PasswordField.border"] = textBorder
        table["TextArea.border"] = textBorder
        table["TextPane.border"] = textBorder
        table["EditorPane.border"] = textBorder
    }
}