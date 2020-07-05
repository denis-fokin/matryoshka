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



inline fun <reified T : JComponent> Komponent(constraints: Any? = null, layout:LayoutManager, function: T.() -> Unit): T = T::class.java.getDeclaredConstructor().newInstance().apply {

    function.invoke(this)

    if (layout.panel is JScrollPane) {
        (layout.panel as JScrollPane).setViewportView(this)
    } else {
        layout.panel.add(this, constraints)
    }
}

fun LayoutManager.AbstractButton(c:Any? = null, f: AbstractButton.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JButton(c:Any? = null,f: JButton.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JCheckBox(c:Any? = null,f: JCheckBox.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JTable(c:Any? = null,f: JTable.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JToggleButton(c:Any? = null,f: JToggleButton.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JToolBar(c:Any? = null,f: JToolBar.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JLabel(c:Any? = null,f: JLabel.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JTextField(c:Any? = null,f: JTextField.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JTextArea(c:Any? = null,f: JTextArea.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JComboBox(c:Any? = null,f: JComboBox<Any?>.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JList(c:Any? = null,f: JList<Any?>.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JMenuBar(c:Any? = null,f: JMenuBar.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JPanel(c:Any? = null,f: JPanel.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JScrollBar(c:Any? = null,f: JScrollBar.() -> Unit) = Komponent(c,  this, f)
//fun JScrollPane(c:Any? = null, f: JScrollPane.() -> Unit) = Komponent(c, f)
fun LayoutManager.JSeparator(c:Any? = null,f: JSeparator.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JSlider(c:Any? = null,f: JSlider.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JSpinner(c:Any? = null,f: JSpinner.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JSplitPane(c:Any? = null,f: JSplitPane.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JTextComponent(c:Any? = null, f: JTextComponent.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JTabbedPane(c:Any? = null,f: JTabbedPane.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JTableHeader(c:Any? = null,f: JTableHeader.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JToolTip(c:Any? = null,f: JToolTip.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JTree(c:Any? = null,f: JTree.() -> Unit) = Komponent(c,  this, f)
fun LayoutManager.JViewportJTree(c:Any? = null,f: JViewport.() -> Unit) = Komponent(c,  this, f)

inline fun <reified T : JRadioButton> JRadioButtonKomponent(constraints: Any? = null, layout:LayoutManager, function: T.() -> Unit): T = T::class.java.getDeclaredConstructor().newInstance().apply {
    layout.panel.addToButtonGroup(this)
    function.invoke(this)
    layout.panel.add(this, constraints)
}

fun LayoutManager.JRadioButton(constraints:Any? = null, function: JRadioButton.() -> Unit): JRadioButton =
    JRadioButtonKomponent(constraints = constraints, layout = this, function = function )


fun LayoutManager.AffineImage (c:Any? = null, f: AffineImage.() -> Unit) = Komponent(c,  this, f)

inline fun <reified T : AffineImage> AffineImageKomponent(constraints: Any? = null, layout:LayoutManager, function: T.() -> Unit): T = T::class.java.getDeclaredConstructor().newInstance().apply {
    function.invoke(this)
    layout.panel.add(this, constraints)
}


class AffineImage : JPanel() {
    var image:Image? = null
    var tx: AffineTransform? = null

    override fun paintComponent(g: Graphics?) {
        val g2d = g?.create()!! as Graphics2D
        try {
            g2d.transform.translate(
                    - image?.getWidth(null)!!.toDouble()/2, - image?.getHeight(null)!!.toDouble()/2
            )
            g2d.drawImage(image, tx, null)
        } finally {
            g2d.dispose()
        }
    }
}
