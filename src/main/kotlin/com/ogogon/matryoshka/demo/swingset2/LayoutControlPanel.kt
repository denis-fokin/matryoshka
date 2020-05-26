package com.ogogon.matryoshka.demo.swingset2

import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import javax.swing.*

class LayoutControlPanel internal constructor(demo: ButtonDemo) : JPanel(), SwingConstants {
    private var absolutePositions: Boolean
    private var textPosition: DirectionPanel? = null
    private var labelAlignment: DirectionPanel? = null
    private var demo: ButtonDemo? = null

    internal inner class OrientationChangeListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            if (e.actionCommand != "OrientationChanged") {
                return
            }
            if (absolutePositions) {
                return
            }
            val currentTextPosition = textPosition?.selection
            if (currentTextPosition == "NW") textPosition?.selection = "NE" else if (currentTextPosition == "NE") textPosition?.selection = "NW" else if (currentTextPosition == "E") textPosition?.selection = "W" else if (currentTextPosition == "W") textPosition?.selection = "E" else if (currentTextPosition == "SE") textPosition?.selection = "SW" else if (currentTextPosition == "SW") textPosition?.selection = "SE"
            val currentLabelAlignment = labelAlignment?.selection
            if (currentLabelAlignment == "NW") labelAlignment?.selection = "NE" else if (currentLabelAlignment == "NE") labelAlignment?.selection = "NW" else if (currentLabelAlignment == "E") labelAlignment?.selection = "W" else if (currentLabelAlignment == "W") labelAlignment?.selection = "E" else if (currentLabelAlignment == "SE") labelAlignment?.selection = "SW" else if (currentLabelAlignment == "SW") labelAlignment?.selection = "SE"
        }
    }

    internal inner class PositioningListener : ItemListener {
        override fun itemStateChanged(e: ItemEvent) {
            val rb = e.source as JRadioButton
            if (rb.text == "Absolute" && rb.isSelected) {
                absolutePositions = true
            } else if (rb.text == "Relative" && rb.isSelected) {
                absolutePositions = false
            }
            for (i in demo?.currentControls?.indices!!) {
                val c = demo!!.currentControls.elementAt(i) as Component
                var hPos: Int
                var vPos: Int
                var hAlign: Int
                var vAlign: Int
                if (c is AbstractButton) {
                    hPos = c.horizontalTextPosition
                    vPos = c.verticalTextPosition
                    hAlign = c.horizontalAlignment
                    vAlign = c.verticalAlignment
                } else if (c is JLabel) {
                    hPos = c.horizontalTextPosition
                    vPos = c.verticalTextPosition
                    hAlign = c.horizontalAlignment
                    vAlign = c.verticalAlignment
                } else {
                    continue
                }
                setPosition(c, hPos, vPos)
                setAlignment(c, hAlign, vAlign)
            }
            demo!!.invalidate()
            demo!!.validate()
            demo!!.repaint()
        }
    }

    // Text Position Listener
    internal inner class TextPositionListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            val rb = e.source as JRadioButton
            if (!rb.isSelected) {
                return
            }
            val cmd = rb.actionCommand
            val hPos: Int
            val vPos: Int
            if (cmd == "NW") {
                hPos = SwingConstants.LEFT
                vPos = SwingConstants.TOP
            } else if (cmd == "N") {
                hPos = SwingConstants.CENTER
                vPos = SwingConstants.TOP
            } else if (cmd == "NE") {
                hPos = SwingConstants.RIGHT
                vPos = SwingConstants.TOP
            } else if (cmd == "W") {
                hPos = SwingConstants.LEFT
                vPos = SwingConstants.CENTER
            } else if (cmd == "C") {
                hPos = SwingConstants.CENTER
                vPos = SwingConstants.CENTER
            } else if (cmd == "E") {
                hPos = SwingConstants.RIGHT
                vPos = SwingConstants.CENTER
            } else if (cmd == "SW") {
                hPos = SwingConstants.LEFT
                vPos = SwingConstants.BOTTOM
            } else if (cmd == "S") {
                hPos = SwingConstants.CENTER
                vPos = SwingConstants.BOTTOM
            } else  /*if(cmd.equals("SE"))*/ {
                hPos = SwingConstants.RIGHT
                vPos = SwingConstants.BOTTOM
            }
            for (i in demo?.currentControls?.indices!!) {
                val c = demo?.currentControls?.elementAt(i) as Component
                setPosition(c, hPos, vPos)
            }
            demo!!.invalidate()
            demo!!.validate()
            demo!!.repaint()
        }
    }

    // Label Alignment Listener
    internal inner class LabelAlignmentListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            val rb = e.source as JRadioButton
            if (!rb.isSelected) {
                return
            }
            val cmd = rb.actionCommand
            val hPos: Int
            val vPos: Int
            if (cmd == "NW") {
                hPos = SwingConstants.LEFT
                vPos = SwingConstants.TOP
            } else if (cmd == "N") {
                hPos = SwingConstants.CENTER
                vPos = SwingConstants.TOP
            } else if (cmd == "NE") {
                hPos = SwingConstants.RIGHT
                vPos = SwingConstants.TOP
            } else if (cmd == "W") {
                hPos = SwingConstants.LEFT
                vPos = SwingConstants.CENTER
            } else if (cmd == "C") {
                hPos = SwingConstants.CENTER
                vPos = SwingConstants.CENTER
            } else if (cmd == "E") {
                hPos = SwingConstants.RIGHT
                vPos = SwingConstants.CENTER
            } else if (cmd == "SW") {
                hPos = SwingConstants.LEFT
                vPos = SwingConstants.BOTTOM
            } else if (cmd == "S") {
                hPos = SwingConstants.CENTER
                vPos = SwingConstants.BOTTOM
            } else  /*if(cmd.equals("SE"))*/ {
                hPos = SwingConstants.RIGHT
                vPos = SwingConstants.BOTTOM
            }
            for (i in demo?.currentControls?.indices!!) {
                val c = demo?.currentControls?.elementAt(i) as Component
                setAlignment(c, hPos, vPos)
                c.invalidate()
            }
            demo!!.invalidate()
            demo!!.validate()
            demo!!.repaint()
        }
    }

    // Position
    fun setPosition(c: Component, hPos: Int, vPos: Int) {
        var hPos = hPos
        var ltr = true
        ltr = c.componentOrientation.isLeftToRight
        if (absolutePositions) {
            if (hPos == SwingConstants.LEADING) {
                hPos = if (ltr) SwingConstants.LEFT else SwingConstants.RIGHT
            } else if (hPos == SwingConstants.TRAILING) {
                hPos = if (ltr) SwingConstants.RIGHT else SwingConstants.LEFT
            }
        } else {
            if (hPos == SwingConstants.LEFT) {
                hPos = if (ltr) SwingConstants.LEADING else SwingConstants.TRAILING
            } else if (hPos == SwingConstants.RIGHT) {
                hPos = if (ltr) SwingConstants.TRAILING else SwingConstants.LEADING
            }
        }
        if (c is AbstractButton) {
            val x = c
            x.horizontalTextPosition = hPos
            x.verticalTextPosition = vPos
        } else if (c is JLabel) {
            val x = c
            x.horizontalTextPosition = hPos
            x.verticalTextPosition = vPos
        }
    }

    fun setAlignment(c: Component, hPos: Int, vPos: Int) {
        var hPos = hPos
        var ltr = true
        ltr = c.componentOrientation.isLeftToRight
        if (absolutePositions) {
            if (hPos == SwingConstants.LEADING) {
                hPos = if (ltr) SwingConstants.LEFT else SwingConstants.RIGHT
            } else if (hPos == SwingConstants.TRAILING) {
                hPos = if (ltr) SwingConstants.RIGHT else SwingConstants.LEFT
            }
        } else {
            if (hPos == SwingConstants.LEFT) {
                hPos = if (ltr) SwingConstants.LEADING else SwingConstants.TRAILING
            } else if (hPos == SwingConstants.RIGHT) {
                hPos = if (ltr) SwingConstants.TRAILING else SwingConstants.LEADING
            }
        }
        if (c is AbstractButton) {
            val x = c
            x.horizontalAlignment = hPos
            x.verticalAlignment = vPos
        } else if (c is JLabel) {
            val x = c
            x.horizontalAlignment = hPos
            x.verticalAlignment = vPos
        }
    }

    // private ComponentOrientChanger componentOrientChanger = null;
    init {
        this.demo = demo

        // this.componentOrientationChanger = componentOrientationChanger;
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        alignmentX = Component.LEFT_ALIGNMENT
        alignmentY = Component.TOP_ALIGNMENT
        var l: JLabel

        // If SwingSet has a ComponentOrientationChanger, then include control
        // for choosing between absolute and relative positioning.  This will
        // only happen when we're running on JDK 1.2 or above.
        //
        // if(componentOrientationChanger != null ) {
        //     l = new JLabel("Positioning:");
        //     add(l);
        //
        //    ButtonGroup group = new ButtonGroup();
        //    PositioningListener positioningListener = new PositioningListener();
        //    JRadioButton absolutePos = new JRadioButton("Absolute");
        //    absolutePos.setMnemonic('a');
        //    absolutePos.setToolTipText("Text/Content positioning is independant of line direction");
        //    group.add(absolutePos);
        //    absolutePos.addItemListener(positioningListener);
        //    add(absolutePos);
        //
        //    JRadioButton relativePos = new JRadioButton("Relative");
        //    relativePos.setMnemonic('r');
        //    relativePos.setToolTipText("Text/Content positioning depends on line direction.");
        //    group.add(relativePos);
        //    relativePos.addItemListener(positioningListener);
        //    add(relativePos);
        //
        //    add(Box.createRigidArea(demo.VGAP20));
        //
        //    absolutePositions = false;
        //    relativePos.setSelected(true);
        //
        //    componentOrientationChanger.addActionListener( new OrientationChangeListener() );
        //} else {
        absolutePositions = true
        //}
        textPosition = DirectionPanel(true, "E", TextPositionListener())
        labelAlignment = DirectionPanel(true, "C", LabelAlignmentListener())

        // Make sure the controls' text position and label alignment match
        // the initial value of the associated direction panel.
        for (i in demo.currentControls.indices) {
            val c = demo.currentControls.elementAt(i) as Component
            setPosition(c, SwingConstants.RIGHT, SwingConstants.CENTER)
            setAlignment(c, SwingConstants.CENTER, SwingConstants.CENTER)
        }
        l = JLabel(demo.getString("LayoutControlPanel.textposition_label"))
        add(l)
        add(textPosition)
        add(Box.createRigidArea(DemoModule.Companion.VGAP20))
        l = JLabel(demo.getString("LayoutControlPanel.contentalignment_label"))
        add(l)
        add(labelAlignment)
        add(Box.createGlue())
    }
}