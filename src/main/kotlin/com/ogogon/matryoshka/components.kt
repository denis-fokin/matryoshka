package com.ogogon.matryoshka

import java.awt.FlowLayout
import javax.swing.*

/*
AbstractButton,
BasicInternalFrameTitlePane,
Box,
Box.Filler,
JColorChooser,
JComboBox,
JFileChooser,
JInternalFrame,
JInternalFrame.JDesktopIcon,
JLabel,
JLayer,
JLayeredPane,
JList,
JMenuBar,
JOptionPane,
JPanel,
JPopupMenu,
JProgressBar,
JRootPane,
JScrollBar,
JScrollPane,
JSeparator,
JSlider,
JSpinner,
JSplitPane,
JTabbedPane,
JTable,
JTableHeader,
JTextComponent,
JToolBar,
JToolTip,
JTree,
JViewport
 */

/*
JButton.java
JCheckBox.java
JCheckBoxMenuItem.java
JMenu
JMenu.java
JMenuItem.java
JPopupMenu
JRadioButton.java
JRadioButtonMenuItem.java
JTable
JToggleButton.java
JToolBar
 */

inline fun <reified T: JComponent> Komponent (constraints:Any? = null, function: T.() -> Unit) : T
        = T::class.java.getDeclaredConstructor().newInstance().apply {
    function.invoke(this)
    lastParent?.add(this, constraints)
}

fun JButton(c:Any? = null, f: JButton.() -> Unit) = Komponent(c, f)
fun JLabel(c:Any? = null, f: JLabel.() -> Unit) = Komponent(c, f)
fun JTextField(c:Any? = null, f: JTextField.() -> Unit) = Komponent(c, f)
fun JTextArea(c:Any? = null, f: JTextArea.() -> Unit) = Komponent(c, f)
fun JComboBox(c:Any? = null, f: JComboBox<Any?>.() -> Unit) = Komponent(c, f)

fun JRadioButton(constraints:Any? = null, function: JRadioButton.() -> Unit): JRadioButton = JRadioButton().apply {
    lastParent?.addToButtonGroup(this)
    function.invoke(this)
    lastParent?.add(this, constraints)
}
