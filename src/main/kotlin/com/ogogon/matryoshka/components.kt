package com.ogogon.matryoshka


import java.awt.*
import java.awt.geom.AffineTransform
import javax.swing.*
import javax.swing.table.JTableHeader
import javax.swing.text.JTextComponent


// How transform decorator works
// Layout { -> save parent
//   Transform { -> mark the scope
//     JComponent {} -> add to parent
//     JComponent {} -> add to parent
//   } -> apply transform when leaving the scope
// }
/*
inline fun Scale (factor: Float, function: () -> Unit) {

    function.invoke()

    lastParent?.components?.forEach { component ->
        val layerUI: LayerUI<JComponent> = ScaleDecorator()
        val jLayer = JLayer(component as JComponent, layerUI)
    }
}



class Transform {}*/

class ScaleDecorator(private val scaleFactor: Double) : LayerUI<JComponent>() {
    override fun paint(g: Graphics?, c: JComponent?) {
        val g2d = g as Graphics2D
        val oldTransform = g2d.transform
        g2d.transform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor)
        super.paint(g, c)
        g2d.transform = oldTransform
    }
}

class Decorator {

    private var scaleFactor = 1.0

    fun scale(factor:Double): Decorator {
        scaleFactor = factor
        return this
    }

    fun decorate(component:JComponent) {
        val layerUI: LayerUI<JComponent> = ScaleDecorator(scaleFactor)
        val jLayer = JLayer(component, layerUI)
        lastParent?.add(jLayer)
    }
}

inline fun <reified T : JComponent> Komponent(constraints: Any? = null, decorator: Decorator? = null, layout:LayoutManager? = null, function: T.() -> Unit): T = T::class.java.getDeclaredConstructor().newInstance().apply {

    function.invoke(this)

    if (lastParent is JScrollPane) {
        (lastParent as JScrollPane).setViewportView(this)
    } else {
        if (decorator == null) {
            lastParent?.add(this, constraints)
        } else {
            decorator.decorate(this)
        }
    }
}

fun AbstractButton(c:Any? = null, decorator: Decorator? = null, f: AbstractButton.() -> Unit) = Komponent(c, decorator, f)
fun LayoutManager.JButton(c:Any? = null, decorator: Decorator? = null,f: JButton.() -> Unit) = Komponent(c, decorator, this, f)
fun JCheckBox(c:Any? = null, decorator: Decorator? = null,f: JCheckBox.() -> Unit) = Komponent(c, decorator, f)
fun JTable(c:Any? = null, decorator: Decorator? = null,f: JTable.() -> Unit) = Komponent(c, decorator, f)
fun JToggleButton(c:Any? = null, decorator: Decorator? = null,f: JToggleButton.() -> Unit) = Komponent(c, decorator, f)
fun JToolBar(c:Any? = null, decorator: Decorator? = null,f: JToolBar.() -> Unit) = Komponent(c, decorator, f)
fun JLabel(c:Any? = null, decorator: Decorator? = null,f: JLabel.() -> Unit) = Komponent(c, decorator, f)
fun JTextField(c:Any? = null, decorator: Decorator? = null,f: JTextField.() -> Unit) = Komponent(c, decorator, f)
fun JTextArea(c:Any? = null, decorator: Decorator? = null,f: JTextArea.() -> Unit) = Komponent(c, decorator, f)
fun JComboBox(c:Any? = null, decorator: Decorator? = null,f: JComboBox<Any?>.() -> Unit) = Komponent(c, decorator, f)
fun JList(c:Any? = null, decorator: Decorator? = null,f: JList<Any?>.() -> Unit) = Komponent(c, decorator, f)
fun JMenuBar(c:Any? = null, decorator: Decorator? = null,f: JMenuBar.() -> Unit) = Komponent(c, decorator, f)
fun JPanel(c:Any? = null, decorator: Decorator? = null,f: JPanel.() -> Unit) = Komponent(c, decorator, f)
fun JScrollBar(c:Any? = null, decorator: Decorator? = null,f: JScrollBar.() -> Unit) = Komponent(c, decorator, f)
//fun JScrollPane(c:Any? = null, f: JScrollPane.() -> Unit) = Komponent(c, f)
fun JSeparator(c:Any? = null, decorator: Decorator? = null,f: JSeparator.() -> Unit) = Komponent(c, decorator, f)
fun JSlider(c:Any? = null, decorator: Decorator? = null,f: JSlider.() -> Unit) = Komponent(c, decorator, f)
fun JSpinner(c:Any? = null, decorator: Decorator? = null,f: JSpinner.() -> Unit) = Komponent(c, decorator, f)
fun JSplitPane(c:Any? = null, decorator: Decorator? = null,f: JSplitPane.() -> Unit) = Komponent(c, decorator, f)
fun JTextComponent(c:Any? = null, decorator: Decorator? = null, f: JTextComponent.() -> Unit) = Komponent(c, decorator, f)
fun JTabbedPane(c:Any? = null, decorator: Decorator? = null,f: JTabbedPane.() -> Unit) = Komponent(c, decorator, f)
fun JTableHeader(c:Any? = null, decorator: Decorator? = null,f: JTableHeader.() -> Unit) = Komponent(c, decorator, f)
fun JToolTip(c:Any? = null, decorator: Decorator? = null,f: JToolTip.() -> Unit) = Komponent(c, decorator, f)
fun JTree(c:Any? = null, decorator: Decorator? = null,f: JTree.() -> Unit) = Komponent(c, decorator, f)
fun JViewportJTree(c:Any? = null, decorator: Decorator? = null,f: JViewport.() -> Unit) = Komponent(c, decorator, f)


fun JRadioButton(constraints:Any? = null, function: JRadioButton.() -> Unit): JRadioButton = JRadioButton().apply {
    lastParent?.addToButtonGroup(this)
    function.invoke(this)
    lastParent?.add(this, constraints)
}
