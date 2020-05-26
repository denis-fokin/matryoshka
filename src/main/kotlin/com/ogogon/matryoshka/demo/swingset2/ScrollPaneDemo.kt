package com.ogogon.matryoshka.demo.swingset2

import java.awt.BorderLayout
import javax.swing.*

class ScrollPaneDemo(swingset: SwingSet2?) : DemoModule(swingset, "ScrollPaneDemo", "toolbar/JScrollPane.gif") {
    /**
     * ScrollPane class that demonstrates how to set the various column and row headers
     * and corners.
     */
    internal inner class ImageScroller(demo: ScrollPaneDemo, icon: Icon) : JScrollPane() {
        init {

            // Panel to hold the icon image
            val p = JPanel(BorderLayout())
            p.add(JLabel(icon), BorderLayout.CENTER)
            getViewport().add(p)

            // Create and add a column header to the scrollpane
            val colHeader = JLabel(
                    demo.createImageIcon("scrollpane/colheader.jpg", getString("ScrollPaneDemo.colheader")))
            setColumnHeaderView(colHeader)

            // Create and add a row header to the scrollpane
            val rowHeader = JLabel(
                    demo.createImageIcon("scrollpane/rowheader.jpg", getString("ScrollPaneDemo.rowheader")))
            setRowHeaderView(rowHeader)

            // Create and add the upper left corner
            val cornerUL = JLabel(
                    demo.createImageIcon("scrollpane/upperleft.jpg", getString("ScrollPaneDemo.upperleft")))
            setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, cornerUL)

            // Create and add the upper right corner
            val cornerUR = JLabel(
                    demo.createImageIcon("scrollpane/upperright.jpg", getString("ScrollPaneDemo.upperright")))
            setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, cornerUR)

            // Create and add the lower left corner
            val cornerLL = JLabel(
                    demo.createImageIcon("scrollpane/lowerleft.jpg", getString("ScrollPaneDemo.lowerleft")))
            setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, cornerLL)
            val vsb = getVerticalScrollBar()
            val hsb = getHorizontalScrollBar()
            vsb.value = icon.iconHeight
            hsb.value = icon.iconWidth / 10
        }
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = ScrollPaneDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * ScrollPaneDemo Constructor
     */
    init {
        val crayons = createImageIcon("scrollpane/crayons.jpg", getString("ScrollPaneDemo.crayons"))
        demoPanel?.add(ImageScroller(this, crayons!!), BorderLayout.CENTER)
    }
}