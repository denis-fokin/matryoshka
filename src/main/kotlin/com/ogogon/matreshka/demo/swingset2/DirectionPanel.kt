package com.ogogon.matreshka.demo.swingset2

import java.awt.Component
import java.awt.event.ActionListener
import java.util.*
import javax.swing.*
import javax.swing.border.Border

class DirectionPanel(enable: Boolean, selection: String, l: ActionListener?) : JPanel() {
    private val group: ButtonGroup
    var selection: String
        get() = group.selection.actionCommand
        set(selection) {
            val e: Enumeration<*> = group.elements
            while (e.hasMoreElements()) {
                val b = e.nextElement() as JRadioButton
                if (b.actionCommand == selection) {
                    b.isSelected = true
                }
            }
        }

    // Chester's way cool layout buttons
    var bl_dot = loadImageIcon("bl.gif", "bottom left layout button")
    var bldn_dot = loadImageIcon("bldn.gif", "selected bottom left layout button")
    var bm_dot = loadImageIcon("bm.gif", "bottom middle layout button")
    var bmdn_dot = loadImageIcon("bmdn.gif", "selected bottom middle layout button")
    var br_dot = loadImageIcon("br.gif", "bottom right layout button")
    var brdn_dot = loadImageIcon("brdn.gif", "selected bottom right layout button")
    var c_dot = loadImageIcon("c.gif", "center layout button")
    var cdn_dot = loadImageIcon("cdn.gif", "selected center layout button")
    var ml_dot = loadImageIcon("ml.gif", "middle left layout button")
    var mldn_dot = loadImageIcon("mldn.gif", "selected middle left layout button")
    var mr_dot = loadImageIcon("mr.gif", "middle right layout button")
    var mrdn_dot = loadImageIcon("mrdn.gif", "selected middle right layout button")
    var tl_dot = loadImageIcon("tl.gif", "top left layout button")
    var tldn_dot = loadImageIcon("tldn.gif", "selected top left layout button")
    var tm_dot = loadImageIcon("tm.gif", "top middle layout button")
    var tmdn_dot = loadImageIcon("tmdn.gif", "selected top middle layout button")
    var tr_dot = loadImageIcon("tr.gif", "top right layout button")
    var trdn_dot = loadImageIcon("trdn.gif", "selected top right layout button")
    fun loadImageIcon(filename: String, description: String?): ImageIcon {
        val path = "/resources/images/buttons/$filename"
        return ImageIcon(javaClass.getResource(path), description)
    }

    inner class DirectionButton(icon: Icon?, downIcon: Icon?, direction: String?,
                                description: String?, l: ActionListener?,
                                group: ButtonGroup, selected: Boolean) : JRadioButton() {
        override fun isFocusTraversable(): Boolean {
            return false
        }

        override fun setBorder(b: Border) {}

        /**
         * A layout direction button
         */
        init {
            addActionListener(l)
            isFocusPainted = false
            horizontalTextPosition = SwingConstants.CENTER
            group.add(this)
            setIcon(icon)
            selectedIcon = downIcon
            actionCommand = direction
            getAccessibleContext().accessibleName = direction
            getAccessibleContext().accessibleDescription = description
            isSelected = selected
        }
    }

    init {
        var selection = selection
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        alignmentY = Component.TOP_ALIGNMENT
        alignmentX = Component.LEFT_ALIGNMENT
        val firstThree = Box.createHorizontalBox()
        val secondThree = Box.createHorizontalBox()
        val thirdThree = Box.createHorizontalBox()
        if (!enable) {
            selection = "None"
        }
        group = ButtonGroup()
        var b: DirectionButton
        b = firstThree.add(DirectionButton(tl_dot, tldn_dot, "NW", "Sets the orientation to the North-West", l, group, selection == "NW")) as DirectionButton
        b.isEnabled = enable
        b = firstThree.add(DirectionButton(tm_dot, tmdn_dot, "N", "Sets the orientation to the North", l, group, selection == "N")) as DirectionButton
        b.isEnabled = enable
        b = firstThree.add(DirectionButton(tr_dot, trdn_dot, "NE", "Sets the orientation to the North-East", l, group, selection == "NE")) as DirectionButton
        b.isEnabled = enable
        b = secondThree.add(DirectionButton(ml_dot, mldn_dot, "W", "Sets the orientation to the West", l, group, selection == "W")) as DirectionButton
        b.isEnabled = enable
        b = secondThree.add(DirectionButton(c_dot, cdn_dot, "C", "Sets the orientation to the Center", l, group, selection == "C")) as DirectionButton
        b.isEnabled = enable
        b = secondThree.add(DirectionButton(mr_dot, mrdn_dot, "E", "Sets the orientation to the East", l, group, selection == "E")) as DirectionButton
        b.isEnabled = enable
        b = thirdThree.add(DirectionButton(bl_dot, bldn_dot, "SW", "Sets the orientation to the South-West", l, group, selection == "SW")) as DirectionButton
        b.isEnabled = enable
        b = thirdThree.add(DirectionButton(bm_dot, bmdn_dot, "S", "Sets the orientation to the South", l, group, selection == "S")) as DirectionButton
        b.isEnabled = enable
        b = thirdThree.add(DirectionButton(br_dot, brdn_dot, "SE", "Sets the orientation to the South-East", l, group, selection == "SE")) as DirectionButton
        b.isEnabled = enable
        add(firstThree)
        add(secondThree)
        add(thirdThree)
    }
}