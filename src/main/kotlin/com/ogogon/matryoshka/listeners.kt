package com.ogogon.matryoshka

import java.awt.*
import java.awt.event.*
import javax.swing.AbstractButton
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.event.AncestorEvent
import javax.swing.event.AncestorListener

fun <T: JComponent> T?.onClick(function: (MouseEvent) -> Unit) {
    this?.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent?) {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onMousePress(function: (MouseEvent) -> Unit) {
    this?.addMouseListener(object : MouseAdapter() {
        override fun mousePressed(e: MouseEvent?) {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onMouseRelease(function: (MouseEvent) -> Unit) {
    this?.addMouseListener(object : MouseAdapter() {
        override fun mouseReleased(e: MouseEvent?) {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onKeyPress(function: (KeyEvent) -> Unit) {
    this?.addKeyListener(object : KeyAdapter() {
        override fun keyPressed(e: KeyEvent?) {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onKeyRelease(function: (KeyEvent) -> Unit) {
    this?.addKeyListener(object : KeyAdapter() {
        override fun keyReleased(e: KeyEvent?) {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onKeyTyped(function: (KeyEvent) -> Unit) {
    this?.addKeyListener(object : KeyAdapter() {
        override fun keyTyped(e: KeyEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: AbstractButton> T?.onAction(function: (ActionEvent) -> Unit) {
    this?.addActionListener { function.invoke(it!!) }
}

fun <T: JComboBox<Any?>> T?.onAction(function: (ActionEvent) -> Unit) {
    this?.addActionListener { function.invoke(it!!) }
}

fun <T: Adjustable> T?.onAdjustment(function: (AdjustmentEvent) -> Unit) {
    this?.addAdjustmentListener{ function.invoke(it!!) }
}

fun <T: JComponent> T?.onResize(function: (ComponentEvent) -> Unit) {
    this?.addComponentListener(object : ComponentAdapter() {
        override fun componentResized(e: ComponentEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onMove(function: (ComponentEvent) -> Unit) {
    this?.addComponentListener(object : ComponentAdapter() {
        override fun componentMoved(e: ComponentEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onShow(function: (ComponentEvent) -> Unit) {
    this?.addComponentListener(object : ComponentAdapter() {
        override fun componentShown(e: ComponentEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onHide(function: (ComponentEvent) -> Unit) {
    this?.addComponentListener(object : ComponentAdapter() {
        override fun componentHidden(e: ComponentEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onAdd(function: (ContainerEvent) -> Unit) {
    this?.addContainerListener(object : ContainerAdapter() {
        override fun componentAdded(e: ContainerEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onRemove(function: (ContainerEvent) -> Unit) {
    this?.addContainerListener(object : ContainerAdapter() {
        override fun componentRemoved(e: ContainerEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onFocusGain(function: (FocusEvent) -> Unit) {
    this?.addFocusListener(object : FocusAdapter() {
        override fun focusGained(e: FocusEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onFocusLost(function: (FocusEvent?) -> Unit) {
    this?.addFocusListener(object : FocusAdapter() {
        override fun focusLost(e: FocusEvent?)  {
            function.invoke(e!!)
        }
    })
}

open class AncestorAdapter : AncestorListener {
    override fun ancestorAdded(event: AncestorEvent?) {}

    override fun ancestorMoved(event: AncestorEvent?) {}

    override fun ancestorRemoved(event: AncestorEvent?) {}
}


fun <T: JComponent> T?.onAncestorAdd(function: (AncestorEvent?) -> Unit) {
    this?.addAncestorListener(object : AncestorAdapter() {
        override fun ancestorAdded(e: AncestorEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onAncestorRemove(function: (AncestorEvent) -> Unit) {
    this?.addAncestorListener(object : AncestorAdapter() {
        override fun ancestorRemoved(e: AncestorEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onAncestorMove(function: (AncestorEvent?) -> Unit) {
    this?.addAncestorListener(object : AncestorAdapter() {
        override fun ancestorMoved(e: AncestorEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onAncestorBoundsMove(function: (HierarchyEvent?) -> Unit) {
    this?.addHierarchyBoundsListener(object : HierarchyBoundsAdapter() {
        override fun ancestorMoved(e: HierarchyEvent?) {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onAncestorBoundsResized(function: (HierarchyEvent) -> Unit) {
    this?.addHierarchyBoundsListener(object : HierarchyBoundsAdapter() {
        override fun ancestorMoved(e: HierarchyEvent?){
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onHierarchyChange(function: (HierarchyEvent) -> Unit) {
    this?.addHierarchyListener{ function.invoke(it!!) }
}


fun <T: JComponent> T?.onInputMethodTextChange(function: (InputMethodEvent) -> Unit) {
    this?.addInputMethodListener(object : InputMethodListener {
        override fun inputMethodTextChanged(e: InputMethodEvent?) {
            function.invoke(e!!)
        }

        override fun caretPositionChanged(event: InputMethodEvent?) {}
    })
}

fun <T: JComponent> T?.onCaretPositionChange(function: (InputMethodEvent) -> Unit) {
    this?.addInputMethodListener(object : InputMethodListener {
        override fun inputMethodTextChanged(event: InputMethodEvent?) {}

        override fun caretPositionChanged(e: InputMethodEvent?) {
            function.invoke(e!!)
        }
    })
}

fun <T: ItemSelectable> T?.onItemStateChange(function: (ItemEvent) -> Unit) {
    this?.addItemListener { function.invoke(it!!) }
}

fun <T: JComponent> T?.onMouseMove(function: (MouseEvent) -> Unit) {
    this?.addMouseMotionListener(object : MouseMotionAdapter() {
        override fun mouseMoved(e: MouseEvent?) {
            function.invoke(e!!)
        }

    })
}

fun <T: JComponent> T?.onMouseDrag(function: (MouseEvent) -> Unit) {
    this?.addMouseMotionListener(object : MouseMotionAdapter() {
        override fun mouseDragged(e: MouseEvent?) {
            function.invoke(e!!)
        }
    })
}

fun <T: JComponent> T?.onMouseWheel(function: (MouseWheelEvent) -> Unit) {
    this?.addMouseWheelListener { function.invoke(it!!) }
}

fun <T: TextComponent> T?.onTextValueChange(function: (TextEvent) -> Unit) {
    this?.addTextListener { function.invoke(it!!) }
}

fun <T: Window> T?.onWindowOpen(function: (WindowEvent) -> Unit) {
    this?.addWindowListener(object : WindowAdapter() {
        override fun windowOpened(e: WindowEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: Window> T?.onWindowClosing(function: (WindowEvent) -> Unit) {
    this?.addWindowListener(object : WindowAdapter() {
        override fun windowClosing(e: WindowEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: Window> T?.onWindowClose(function: (WindowEvent) -> Unit) {
    this?.addWindowListener(object : WindowAdapter() {
        override fun windowClosed(e: WindowEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: Window> T?.onWindowIconified(function: (WindowEvent) -> Unit) {
    this?.addWindowListener(object : WindowAdapter() {
        override fun windowIconified(e: WindowEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: Window> T?.onWindowDeiconified(function: (WindowEvent) -> Unit) {
    this?.addWindowListener(object : WindowAdapter() {
        override fun windowDeiconified(e: WindowEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: Window> T?.onWindowActivation(function: (WindowEvent) -> Unit) {
    this?.addWindowListener(object : WindowAdapter() {
        override fun windowActivated(e: WindowEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: Window> T?.onWindowDeactivation(function: (WindowEvent) -> Unit) {
    this?.addWindowListener(object : WindowAdapter() {
        override fun windowDeactivated(e: WindowEvent?)  {
            function.invoke(e!!)
        }
    })
}

fun <T: Window> T?.onWindowStateChange(function: (WindowEvent) -> Unit) {
    this?.addWindowStateListener(object : WindowAdapter() {
        override fun windowStateChanged(e: WindowEvent?)   {
            function.invoke(e!!)
        }
    })
}

fun <T: Window> T?.onWindowFocusGain(function: (WindowEvent) -> Unit) {
    this?.addWindowFocusListener(object : WindowAdapter() {
        override fun windowGainedFocus(e: WindowEvent?) {
            function.invoke(e!!)
        }
    })
}

fun <T: Window> T?.onWindowFocusLost(function: (WindowEvent) -> Unit) {
    this?.addWindowFocusListener(object : WindowAdapter() {
        override fun windowLostFocus(e: WindowEvent?) {
            function.invoke(e!!)
        }
    })
}