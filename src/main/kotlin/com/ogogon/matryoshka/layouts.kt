package com.ogogon.matryoshka

import java.awt.*
import java.util.*
import javax.swing.*
import javax.swing.border.Border

val layoutsToContainers = WeakHashMap<LayoutManager, JPanel?>()
val containersToButtonGroup = WeakHashMap<Container, ButtonGroup?>()

inline fun <reified T: LayoutManager> Layout(container:Container, constraints:Any?, function: T.() -> Unit): T
        = T::class.java.getDeclaredConstructor().newInstance().apply {
    container.add(panel, constraints)
    panel.layout = this
    function.invoke(this)
}

var <T : LayoutManager> T.panel: JPanel
    set(p) {
        layoutsToContainers[this] = p
    }
    get() {
        if (!layoutsToContainers.containsKey(this)) {
            layoutsToContainers[this] = JPanel()
        }
        return layoutsToContainers[this]!!
    }

var <T : LayoutManager> T.border: Border
        get() = panel.border
        set(value) {panel.border = value}

var <T : LayoutManager> T.alignmentX: Float
        get() = panel.alignmentX
        set(value) {panel.alignmentX = value}

var <T : LayoutManager> T.alignmentY: Float
    get() = panel.alignmentY
    set(value) {panel.alignmentY = value}

val <T : Container> T.buttonGroup: ButtonGroup
    get () {
        if (!containersToButtonGroup.containsKey(this)) {
            containersToButtonGroup[this] = ButtonGroup()
        }
        return containersToButtonGroup[this]!!
    }

inline fun <reified T: Container> T.addToButtonGroup (button:AbstractButton) {
    buttonGroup.add(button)
}

fun Container.FlowLayout(c:Any? = null, f: FlowLayout.() -> Unit) = Layout(this, c, f)
fun Container.BorderLayout(c:Any? = null, f: BorderLayout.() -> Unit) = Layout(this, c, f)
fun Container.GridLayout(c:Any? = null, f: GridLayout.() -> Unit) = Layout(this, c, f)



/*fun Container.TransformLayout(image: Image, c:Any? = null, f: TransformLayout.() -> Unit) = TLayout(image, this, c, f)

inline fun <reified T: TransformLayout> TLayout(image:Image, container:Container, constraints:Any?, function: T.() -> Unit): T
        = T::class.java.getDeclaredConstructor().newInstance().apply {
    affineImage = AffineImage(this)
    container.add(affineImage, constraints)
    affineImage.layout = this
    function.invoke(this)
}*/



/*class TPanel(layout: LayoutManager?) : JPanel(layout) {
    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        g?.fillOval(0,0,300,300)
    }
}*/

class TransformLayout : LayoutManager {
    override fun layoutContainer(parent: Container?) {}

    override fun preferredLayoutSize(parent: Container?): Dimension {
        return Dimension(0,0)
    }

    override fun minimumLayoutSize(parent: Container?): Dimension {
        return Dimension(0,0)
    }

    override fun addLayoutComponent(name: String?, comp: Component?) {}

    override fun removeLayoutComponent(comp: Component?) {}

}

fun JScrollPane(constraints:Any? = null, f: JScrollPane.() -> Unit)
        = JScrollPane::class.java.getDeclaredConstructor().newInstance().apply {
    f.invoke(this)
}

fun BoxLayout(panel:JPanel = JPanel(), axis:Int, function: BoxLayout.() -> Unit): BoxLayout = BoxLayout(panel, axis).apply {
    panel.layout = this
    function.invoke(this)
}