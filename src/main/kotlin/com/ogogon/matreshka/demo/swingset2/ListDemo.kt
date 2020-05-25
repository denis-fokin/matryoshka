package com.ogogon.matreshka.demo.swingset2

import java.awt.BorderLayout
import java.awt.Component
import java.awt.Insets
import java.awt.Rectangle
import java.awt.event.ActionEvent
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.util.*
import javax.swing.*

class ListDemo(swingset: SwingSet2?) : DemoModule(swingset, "ListDemo", "toolbar/JList.gif") {
    var list: JList<Any?>
    var prefixList: JPanel? = null
    var suffixList: JPanel? = null
    var prefixAction: Action? = null
    var suffixAction: Action? = null
    var listModel: GeneratedListModel
    var checkboxes = Vector<Any?>()
    public override fun updateDragEnabled(dragEnabled: Boolean) {
        list.dragEnabled = dragEnabled
    }

    fun createControlPanel(): JPanel {
        val controlPanel: JPanel = object : JPanel() {
            override fun getInsets(): Insets {
                return Insets(0, 4, 10, 10)
            }
        }
        controlPanel.layout = BoxLayout(controlPanel, BoxLayout.X_AXIS)
        val prefixPanel = JPanel()
        prefixPanel.layout = BoxLayout(prefixPanel, BoxLayout.Y_AXIS)
        prefixPanel.add(JLabel(getString("ListDemo.prefixes")))
        val suffixPanel = JPanel()
        suffixPanel.layout = BoxLayout(suffixPanel, BoxLayout.Y_AXIS)
        suffixPanel.add(JLabel(getString("ListDemo.suffixes")))
        prefixList = object : JPanel() {
            override fun getInsets(): Insets {
                return Insets(0, 4, 0, 0)
            }
        }
        prefixList?.layout = BoxLayout(prefixList, BoxLayout.Y_AXIS)
        var scrollPane = JScrollPane(prefixList)
        scrollPane.verticalScrollBar.unitIncrement = 10
        prefixPanel.add(scrollPane)
        prefixPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        suffixList = object : JPanel() {
            override fun getInsets(): Insets {
                return Insets(0, 4, 0, 0)
            }
        }
        suffixList?.layout = BoxLayout(suffixList, BoxLayout.Y_AXIS)
        scrollPane = JScrollPane(suffixList)
        scrollPane.verticalScrollBar.unitIncrement = 10
        suffixPanel.add(scrollPane)
        suffixPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        controlPanel.add(prefixPanel)
        controlPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP15))
        controlPanel.add(suffixPanel)
        return controlPanel
    }

    private val listFocusListener: FocusListener = object : FocusAdapter() {
        override fun focusGained(e: FocusEvent) {
            val c = e.component as JComponent
            c.scrollRectToVisible(Rectangle(0, 0, c.width, c.height))
        }
    }

    fun addPrefix(prefix: String?, selected: Boolean) {
        if (prefixAction == null) {
            prefixAction = UpdatePrefixListAction(listModel)
        }
        val cb = prefixList!!.add(JCheckBox(prefix)) as JCheckBox
        checkboxes.addElement(cb)
        cb.isSelected = selected
        cb.addActionListener(prefixAction)
        if (selected) {
            listModel.addPrefix(prefix)
        }
        cb.addFocusListener(listFocusListener)
    }

    fun addSuffix(suffix: String?, selected: Boolean) {
        if (suffixAction == null) {
            suffixAction = UpdateSuffixListAction(listModel)
        }
        val cb = suffixList!!.add(JCheckBox(suffix)) as JCheckBox
        checkboxes.addElement(cb)
        cb.isSelected = selected
        cb.addActionListener(suffixAction)
        if (selected) {
            listModel.addSuffix(suffix)
        }
        cb.addFocusListener(listFocusListener)
    }

    internal inner class UpdatePrefixListAction(var listModel: GeneratedListModel) : AbstractAction() {
        override fun actionPerformed(e: ActionEvent) {
            val cb = e.source as JCheckBox
            if (cb.isSelected) {
                listModel.addPrefix(cb.text)
            } else {
                listModel.removePrefix(cb.text)
            }
        }

    }

    internal inner class UpdateSuffixListAction(var listModel: GeneratedListModel) : AbstractAction() {
        override fun actionPerformed(e: ActionEvent) {
            val cb = e.source as JCheckBox
            if (cb.isSelected) {
                listModel.addSuffix(cb.text)
            } else {
                listModel.removeSuffix(cb.text)
            }
        }

    }

    inner class GeneratedListModel(var demo: ListDemo) : AbstractListModel<Any?>() {
        private var permuter: Permuter? = null
        var prefix = Vector<Any?>()
        var suffix = Vector<Any?>()
        private fun update() {
            permuter = Permuter(size)
            fireContentsChanged(this, 0, size)
        }

        fun addPrefix(s: String?) {
            if (!prefix.contains(s)) {
                prefix.addElement(s)
                update()
            }
        }

        fun removePrefix(s: String?) {
            prefix.removeElement(s)
            update()
        }

        fun addSuffix(s: String?) {
            if (!suffix.contains(s)) {
                suffix.addElement(s)
                update()
            }
        }

        fun removeSuffix(s: String?) {
            suffix.removeElement(s)
            update()
        }

        override fun getSize(): Int {
            return prefix.size * suffix.size
        }

        override fun getElementAt(index: Int): Any? {
            if (permuter == null) {
                update()
            }
            // morph the index to another int -- this has the benefit of
            // causing the list to look random.
            val j = permuter!!.map(index)
            val ps = prefix.size
            val ss = suffix.size
            return prefix.elementAt(j % ps) as String + suffix.elementAt(j / ps % ss) as String
        }

    }

    var images = arrayOfNulls<ImageIcon>(7)
    fun loadImages() {
        images[0] = createImageIcon("list/red.gif", getString("ListDemo.red"))
        images[1] = createImageIcon("list/blue.gif", getString("ListDemo.blue"))
        images[2] = createImageIcon("list/yellow.gif", getString("ListDemo.yellow"))
        images[3] = createImageIcon("list/green.gif", getString("ListDemo.green"))
        images[4] = createImageIcon("list/gray.gif", getString("ListDemo.gray"))
        images[5] = createImageIcon("list/cyan.gif", getString("ListDemo.cyan"))
        images[6] = createImageIcon("list/magenta.gif", getString("ListDemo.magenta"))
    }

    internal inner class CompanyLogoListCellRenderer : DefaultListCellRenderer() {
        override fun getListCellRendererComponent(
                list: JList<*>?,
                value: Any,
                index: Int,
                isSelected: Boolean,
                cellHasFocus: Boolean): Component {
            val retValue = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
            )
            icon = images[index % 7]
            return retValue
        }
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = ListDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * ListDemo Constructor
     */
    init {
        loadImages()
        val description = JLabel(getString("ListDemo.description"))
        demoPanel?.add(description, BorderLayout.NORTH)
        val centerPanel = JPanel()
        centerPanel.layout = BoxLayout(centerPanel, BoxLayout.X_AXIS)
        centerPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP10))
        demoPanel?.add(centerPanel, BorderLayout.CENTER)
        val listPanel = JPanel()
        listPanel.layout = BoxLayout(listPanel, BoxLayout.Y_AXIS)
        listPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP10))
        centerPanel.add(listPanel)
        centerPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP30))

        // Create the list
        list = JList<Any?>()
        list.setCellRenderer(CompanyLogoListCellRenderer())
        listModel = GeneratedListModel(this)
        list.setModel(listModel)

        // Set the preferred row count. This affects the preferredSize
        // of the JList when it's in a scrollpane.
        list.visibleRowCount = 22

        // Add list to a scrollpane
        val scrollPane = JScrollPane(list)
        listPanel.add(scrollPane)
        listPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP10))

        // Add the control panel (holds the prefix/suffix list and prefix/suffix checkboxes)
        centerPanel.add(createControlPanel())

        // create prefixes and suffixes
        addPrefix("Tera", true)
        addPrefix("Micro", false)
        addPrefix("Southern", false)
        addPrefix("Net", true)
        addPrefix("YoYo", true)
        addPrefix("Northern", false)
        addPrefix("Tele", false)
        addPrefix("Eastern", false)
        addPrefix("Neo", false)
        addPrefix("Digi", false)
        addPrefix("National", false)
        addPrefix("Compu", true)
        addPrefix("Meta", true)
        addPrefix("Info", false)
        addPrefix("Western", false)
        addPrefix("Data", false)
        addPrefix("Atlantic", false)
        addPrefix("Advanced", false)
        addPrefix("Euro", false)
        addPrefix("Pacific", false)
        addPrefix("Mobile", false)
        addPrefix("In", false)
        addPrefix("Computa", false)
        addPrefix("Digital", false)
        addPrefix("Analog", false)
        addSuffix("Tech", true)
        addSuffix("Soft", true)
        addSuffix("Telecom", true)
        addSuffix("Solutions", false)
        addSuffix("Works", true)
        addSuffix("Dyne", false)
        addSuffix("Services", false)
        addSuffix("Vers", false)
        addSuffix("Devices", false)
        addSuffix("Software", false)
        addSuffix("Serv", false)
        addSuffix("Systems", true)
        addSuffix("Dynamics", true)
        addSuffix("Net", false)
        addSuffix("Sys", false)
        addSuffix("Computing", false)
        addSuffix("Scape", false)
        addSuffix("Com", false)
        addSuffix("Ware", false)
        addSuffix("Widgets", false)
        addSuffix("Media", false)
        addSuffix("Computer", false)
        addSuffix("Hardware", false)
        addSuffix("Gizmos", false)
        addSuffix("Concepts", false)
    }
}