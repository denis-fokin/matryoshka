package com.ogogon.matryoshka

import java.awt.FlowLayout
import javax.swing.*
import javax.swing.table.JTableHeader
import javax.swing.text.JTextComponent

inline fun <reified T: JComponent> Komponent (constraints:Any? = null, function: T.() -> Unit) : T
        = T::class.java.getDeclaredConstructor().newInstance().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun AbstractButton(c:Any? = null, f: AbstractButton.() -> Unit) = Komponent(c, f)
fun JButton(c:Any? = null, f: JButton.() -> Unit) = Komponent(c, f)
fun JCheckBox(c:Any? = null, f: JCheckBox.() -> Unit) = Komponent(c, f)
fun JTable(c:Any? = null, f: JTable.() -> Unit) = Komponent(c, f)
fun JToggleButton(c:Any? = null, f: JToggleButton.() -> Unit) = Komponent(c, f)
fun JToolBar(c:Any? = null, f: JToolBar.() -> Unit) = Komponent(c, f)
fun JLabel(c:Any? = null, f: JLabel.() -> Unit) = Komponent(c, f)
fun JTextField(c:Any? = null, f: JTextField.() -> Unit) = Komponent(c, f)
fun JTextArea(c:Any? = null, f: JTextArea.() -> Unit) = Komponent(c, f)
fun JComboBox(c:Any? = null, f: JComboBox<Any?>.() -> Unit) = Komponent(c, f)
fun JList(c:Any? = null, f: JList<Any?>.() -> Unit) = Komponent(c, f)
fun JMenuBar(c:Any? = null, f: JMenuBar.() -> Unit) = Komponent(c, f)
fun JPanel(c:Any? = null, f: JPanel.() -> Unit) = Komponent(c, f)
fun JScrollBar(c:Any? = null, f: JScrollBar.() -> Unit) = Komponent(c, f)
fun JScrollPane(c:Any? = null, f: JScrollPane.() -> Unit) = Komponent(c, f)
fun JSeparator(c:Any? = null, f: JSeparator.() -> Unit) = Komponent(c, f)
fun JSlider(c:Any? = null, f: JSlider.() -> Unit) = Komponent(c, f)
fun JSpinner(c:Any? = null, f: JSpinner.() -> Unit) = Komponent(c, f)
fun JSplitPane(c:Any? = null, f: JSplitPane.() -> Unit) = Komponent(c, f)
fun JTextComponent(c:Any? = null, f: JTextComponent.() -> Unit) = Komponent(c, f)
fun JTabbedPane(c:Any? = null, f: JTabbedPane.() -> Unit) = Komponent(c, f)
fun JTableHeader(c:Any? = null, f: JTableHeader.() -> Unit) = Komponent(c, f)
fun JToolTip(c:Any? = null, f: JToolTip.() -> Unit) = Komponent(c, f)
fun JTree(c:Any? = null, f: JTree.() -> Unit) = Komponent(c, f)
fun JViewportJTree(c:Any? = null, f: JViewport.() -> Unit) = Komponent(c, f)


fun JRadioButton(constraints:Any? = null, function: JRadioButton.() -> Unit): JRadioButton = JRadioButton().apply {
    lastParent?.addToButtonGroup(this)
    function.invoke(this)
    lastParent?.add(this, constraints)
}
