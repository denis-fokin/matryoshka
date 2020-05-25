package com.ogogon.matreshka.demo.swingset2

import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ItemListener
import java.awt.print.PrinterException
import java.text.MessageFormat
import java.util.*
import javax.accessibility.Accessible
import javax.accessibility.AccessibleContext
import javax.accessibility.AccessibleRelation
import javax.swing.*
import javax.swing.JTable.PrintMode
import javax.swing.border.TitledBorder
import javax.swing.table.AbstractTableModel
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableModel
import javax.swing.table.TableRowSorter

class TableDemo(swingset: SwingSet2?) : DemoModule(swingset, "TableDemo", "toolbar/JTable.gif") {
    var tableView: JTable? = null
    var scrollpane: JScrollPane? = null
    var origin = Dimension(0, 0)
    var isColumnReorderingAllowedCheckBox: JCheckBox
    var showHorizontalLinesCheckBox: JCheckBox
    var showVerticalLinesCheckBox: JCheckBox
    var isColumnSelectionAllowedCheckBox: JCheckBox
    var isRowSelectionAllowedCheckBox: JCheckBox
    var interCellSpacingLabel: JLabel
    var rowHeightLabel: JLabel
    var interCellSpacingSlider: JSlider
    var rowHeightSlider: JSlider
    val selectionModeComboBox: JComboBox<String> = object : JComboBox<String>() {
        override fun getMaximumSize(): Dimension {
            return preferredSize
        }
    }
    var resizeModeComboBox: JComboBox<String> = object : JComboBox<String>() {
        override fun getMaximumSize(): Dimension {
            return preferredSize
        }
    }
    var headerLabel: JLabel
    var footerLabel: JLabel
    var headerTextField: JTextField
    var footerTextField: JTextField
    var fitWidth: JCheckBox
    var printButton: JButton
    var controlPanel: JPanel
    var tableAggregate: JScrollPane
    var path = "food/"
    val INITIAL_ROWHEIGHT = 33

    /**
     * Sets the Accessibility MEMBER_OF property to denote that
     * these components work together as a group. Each object
     * is set to be a MEMBER_OF an array that contains all of
     * the objects in the group, including itself.
     *
     * @param components The list of objects that are related
     */
    fun buildAccessibleGroup(components: Vector<*>) {
        var context: AccessibleContext? = null
        val numComponents = components.size
        val group = components.toTypedArray()
        var `object`: Any? = null
        for (i in 0 until numComponents) {
            `object` = components.elementAt(i)
            if (`object` is Accessible) {
                context = (components.elementAt(i) as Accessible).accessibleContext
                context.accessibleRelationSet.add(
                        AccessibleRelation(
                                AccessibleRelation.MEMBER_OF, group))
            }
        }
    } // buildAccessibleGroup()

    /**
     * This sets CONTROLLER_FOR on the controls that manipulate the
     * table and CONTROLLED_BY relationships on the table to point
     * back to the controllers.
     */
    private fun setTableControllers() {

        // Set up the relationships to show what controls the table
        setAccessibleController(isColumnReorderingAllowedCheckBox,
                tableAggregate)
        setAccessibleController(showHorizontalLinesCheckBox,
                tableAggregate)
        setAccessibleController(showVerticalLinesCheckBox,
                tableAggregate)
        setAccessibleController(isColumnSelectionAllowedCheckBox,
                tableAggregate)
        setAccessibleController(isRowSelectionAllowedCheckBox,
                tableAggregate)
        setAccessibleController(interCellSpacingSlider,
                tableAggregate)
        setAccessibleController(rowHeightSlider,
                tableAggregate)
        setAccessibleController(selectionModeComboBox,
                tableAggregate)
        setAccessibleController(resizeModeComboBox,
                tableAggregate)
    } // setTableControllers()

    /**
     * Sets up accessibility relationships to denote that one
     * object controls another. The CONTROLLER_FOR property is
     * set on the controller object, and the CONTROLLED_BY
     * property is set on the target object.
     */
    private fun setAccessibleController(controller: JComponent?,
                                        target: JComponent) {
        val controllerRelations = controller!!.accessibleContext.accessibleRelationSet
        val targetRelations = target.accessibleContext.accessibleRelationSet
        controllerRelations.add(
                AccessibleRelation(
                        AccessibleRelation.CONTROLLER_FOR, target))
        targetRelations.add(
                AccessibleRelation(
                        AccessibleRelation.CONTROLLED_BY, controller))
    } // setAccessibleController()

    fun createTable(): JScrollPane {

        // final
        val names = arrayOf(
                getString("TableDemo.first_name"),
                getString("TableDemo.last_name"),
                getString("TableDemo.favorite_color"),
                getString("TableDemo.favorite_movie"),
                getString("TableDemo.favorite_number"),
                getString("TableDemo.favorite_food")
        )
        val apple = createImageIcon("food/apple.jpg", getString("TableDemo.apple"))
        val asparagus = createImageIcon("food/asparagus.gif", getString("TableDemo.asparagus"))
        val banana = createImageIcon("food/banana.gif", getString("TableDemo.banana"))
        val broccoli = createImageIcon("food/broccoli.gif", getString("TableDemo.broccoli"))
        val cantaloupe = createImageIcon("food/cantaloupe.gif", getString("TableDemo.cantaloupe"))
        val carrot = createImageIcon("food/carrot.gif", getString("TableDemo.carrot"))
        val corn = createImageIcon("food/corn.gif", getString("TableDemo.corn"))
        val grapes = createImageIcon("food/grapes.gif", getString("TableDemo.grapes"))
        val grapefruit = createImageIcon("food/grapefruit.gif", getString("TableDemo.grapefruit"))
        val kiwi = createImageIcon("food/kiwi.gif", getString("TableDemo.kiwi"))
        val onion = createImageIcon("food/onion.gif", getString("TableDemo.onion"))
        val pear = createImageIcon("food/pear.gif", getString("TableDemo.pear"))
        val peach = createImageIcon("food/peach.gif", getString("TableDemo.peach"))
        val pepper = createImageIcon("food/pepper.gif", getString("TableDemo.pepper"))
        val pickle = createImageIcon("food/pickle.gif", getString("TableDemo.pickle"))
        val pineapple = createImageIcon("food/pineapple.gif", getString("TableDemo.pineapple"))
        val raspberry = createImageIcon("food/raspberry.gif", getString("TableDemo.raspberry"))
        val sparegrass = createImageIcon("food/asparagus.gif", getString("TableDemo.sparegrass"))
        val strawberry = createImageIcon("food/strawberry.gif", getString("TableDemo.strawberry"))
        val tomato = createImageIcon("food/tomato.gif", getString("TableDemo.tomato"))
        val watermelon = createImageIcon("food/watermelon.gif", getString("TableDemo.watermelon"))
        val aqua = NamedColor(Color(127, 255, 212), getString("TableDemo.aqua"))
        val beige = NamedColor(Color(245, 245, 220), getString("TableDemo.beige"))
        val black = NamedColor(Color.black, getString("TableDemo.black"))
        val blue = NamedColor(Color(0, 0, 222), getString("TableDemo.blue"))
        val eblue = NamedColor(Color.blue, getString("TableDemo.eblue"))
        val jfcblue = NamedColor(Color(204, 204, 255), getString("TableDemo.jfcblue"))
        val jfcblue2 = NamedColor(Color(153, 153, 204), getString("TableDemo.jfcblue2"))
        val cybergreen = NamedColor(Color.green.darker().brighter(), getString("TableDemo.cybergreen"))
        val darkgreen = NamedColor(Color(0, 100, 75), getString("TableDemo.darkgreen"))
        val forestgreen = NamedColor(Color.green.darker(), getString("TableDemo.forestgreen"))
        val gray = NamedColor(Color.gray, getString("TableDemo.gray"))
        val green = NamedColor(Color.green, getString("TableDemo.green"))
        val orange = NamedColor(Color(255, 165, 0), getString("TableDemo.orange"))
        val purple = NamedColor(Color(160, 32, 240), getString("TableDemo.purple"))
        val red = NamedColor(Color.red, getString("TableDemo.red"))
        val rustred = NamedColor(Color.red.darker(), getString("TableDemo.rustred"))
        val sunpurple = NamedColor(Color(100, 100, 255), getString("TableDemo.sunpurple"))
        val suspectpink = NamedColor(Color(255, 105, 180), getString("TableDemo.suspectpink"))
        val turquoise = NamedColor(Color(0, 255, 255), getString("TableDemo.turquoise"))
        val violet = NamedColor(Color(238, 130, 238), getString("TableDemo.violet"))
        val yellow = NamedColor(Color.yellow, getString("TableDemo.yellow"))

        // Create the dummy data (a few rows of names)
        val data = arrayOf<Array<Any?>>(arrayOf<Any?>("Mike", "Albers", green, getString("TableDemo.brazil"), 44.0, strawberry), arrayOf<Any?>("Mark", "Andrews", blue, getString("TableDemo.curse"), 3, grapes), arrayOf<Any?>("Brian", "Beck", black, getString("TableDemo.bluesbros"), 2.7182818285, raspberry), arrayOf<Any?>("Lara", "Bunni", red, getString("TableDemo.airplane"), 15, strawberry), arrayOf<Any?>("Roger", "Brinkley", blue, getString("TableDemo.man"), 13, peach), arrayOf<Any?>("Brent", "Christian", black, getString("TableDemo.bladerunner"), 23, broccoli), arrayOf<Any?>("Mark", "Davidson", darkgreen, getString("TableDemo.brazil"), 27, asparagus), arrayOf<Any?>("Jeff", "Dinkins", blue, getString("TableDemo.ladyvanishes"), 8, kiwi), arrayOf<Any?>("Ewan", "Dinkins", yellow, getString("TableDemo.bugs"), 2, strawberry), arrayOf<Any?>("Amy", "Fowler", violet, getString("TableDemo.reservoir"), 3, raspberry), arrayOf<Any?>("Hania", "Gajewska", purple, getString("TableDemo.jules"), 5, raspberry), arrayOf<Any?>("David", "Geary", blue, getString("TableDemo.pulpfiction"), 3, watermelon), arrayOf<Any?>("Eric", "Hawkes", blue, getString("TableDemo.bladerunner"), .693, pickle), arrayOf<Any?>("Shannon", "Hickey", green, getString("TableDemo.shawshank"), 2, grapes), arrayOf<Any?>("Earl", "Johnson", green, getString("TableDemo.pulpfiction"), 8, carrot), arrayOf<Any?>("Robi", "Khan", green, getString("TableDemo.goodfellas"), 89, apple), arrayOf<Any?>("Robert", "Kim", blue, getString("TableDemo.mohicans"), 655321, strawberry), arrayOf<Any?>("Janet", "Koenig", turquoise, getString("TableDemo.lonestar"), 7, peach), arrayOf<Any?>("Jeff", "Kesselman", blue, getString("TableDemo.stuntman"), 17, pineapple), arrayOf<Any?>("Onno", "Kluyt", orange, getString("TableDemo.oncewest"), 8, broccoli), arrayOf<Any?>("Peter", "Korn", sunpurple, getString("TableDemo.musicman"), 12, sparegrass), arrayOf<Any?>("Rick", "Levenson", black, getString("TableDemo.harold"), 1327, raspberry), arrayOf<Any?>("Brian", "Lichtenwalter", jfcblue, getString("TableDemo.fifthelement"), 22, pear), arrayOf<Any?>("Malini", "Minasandram", beige, getString("TableDemo.joyluck"), 9, corn), arrayOf<Any?>("Michael", "Martak", green, getString("TableDemo.city"), 3, strawberry), arrayOf<Any?>("David", "Mendenhall", forestgreen, getString("TableDemo.schindlerslist"), 7, peach), arrayOf<Any?>("Phil", "Milne", suspectpink, getString("TableDemo.withnail"), 3, banana), arrayOf<Any?>("Lynn", "Monsanto", cybergreen, getString("TableDemo.dasboot"), 52, peach), arrayOf<Any?>("Hans", "Muller", rustred, getString("TableDemo.eraserhead"), 0, pineapple), arrayOf<Any?>("Joshua", "Outwater", blue, getString("TableDemo.labyrinth"), 3, pineapple), arrayOf<Any?>("Tim", "Prinzing", blue, getString("TableDemo.firstsight"), 69, pepper), arrayOf<Any?>("Raj", "Premkumar", jfcblue2, getString("TableDemo.none"), 7, broccoli), arrayOf<Any?>("Howard", "Rosen", green, getString("TableDemo.defending"), 7, strawberry), arrayOf<Any?>("Ray", "Ryan", black, getString("TableDemo.buckaroo"), 3.141592653589793238462643383279502884197169399375105820974944, banana), arrayOf<Any?>("Georges", "Saab", aqua, getString("TableDemo.bicycle"), 290, cantaloupe), arrayOf<Any?>("Tom", "Santos", blue, getString("TableDemo.spinaltap"), 241, pepper), arrayOf<Any?>("Rich", "Schiavi", blue, getString("TableDemo.repoman"), 0xFF, pepper), arrayOf<Any?>("Nancy", "Schorr", green, getString("TableDemo.fifthelement"), 47, watermelon), arrayOf<Any?>("Keith", "Sprochi", darkgreen, getString("TableDemo.2001"), 13, watermelon), arrayOf<Any?>("Matt", "Tucker", eblue, getString("TableDemo.starwars"), 2, broccoli), arrayOf<Any?>("Dmitri", "Trembovetski", red, getString("TableDemo.aliens"), 222, tomato), arrayOf<Any?>("Scott", "Violet", violet, getString("TableDemo.raiders"), -97, banana), arrayOf<Any?>("Kathy", "Walrath", darkgreen, getString("TableDemo.thinman"), 8, pear), arrayOf<Any?>("Nathan", "Walrath", black, getString("TableDemo.chusingura"), 3, grapefruit), arrayOf<Any?>("Steve", "Wilson", green, getString("TableDemo.raiders"), 7, onion), arrayOf<Any?>("Kathleen", "Zelony", gray, getString("TableDemo.dog"), 13, grapes))

        // Create a model of the data.
        val dataModel: TableModel = object : AbstractTableModel() {
            override fun getColumnCount(): Int {
                return names.size
            }

            override fun getRowCount(): Int {
                return data.size
            }

            override fun getValueAt(row: Int, col: Int): Any? {
                return data[row][col]
            }

            override fun getColumnName(column: Int): String {
                return names[column]
            }

            override fun getColumnClass(c: Int): Class<Any>? {
                return getValueAt(0, c)?.javaClass
            }

            override fun isCellEditable(row: Int, col: Int): Boolean {
                return col != 5
            }

            override fun setValueAt(aValue: Any, row: Int, column: Int) {
                data[row][column] = aValue
            }
        }


        // Create the table
        tableView = JTable(dataModel)
        val sorter = TableRowSorter<TableModel>(dataModel)
        tableView!!.rowSorter = sorter

        // Show colors by rendering them in their own color.
        val colorRenderer: DefaultTableCellRenderer = object : DefaultTableCellRenderer() {
            public override fun setValue(value: Any) {
                if (value is NamedColor) {
                    val c = value
                    background = c
                    foreground = c.textColor
                    text = c.toString()
                } else {
                    super.setValue(value)
                }
            }
        }

        // Create a combo box to show that you can use one in a table.
        val comboBox = JComboBox<Any?>()
        comboBox.addItem(aqua)
        comboBox.addItem(beige)
        comboBox.addItem(black)
        comboBox.addItem(blue)
        comboBox.addItem(eblue)
        comboBox.addItem(jfcblue)
        comboBox.addItem(jfcblue2)
        comboBox.addItem(cybergreen)
        comboBox.addItem(darkgreen)
        comboBox.addItem(forestgreen)
        comboBox.addItem(gray)
        comboBox.addItem(green)
        comboBox.addItem(orange)
        comboBox.addItem(purple)
        comboBox.addItem(red)
        comboBox.addItem(rustred)
        comboBox.addItem(sunpurple)
        comboBox.addItem(suspectpink)
        comboBox.addItem(turquoise)
        comboBox.addItem(violet)
        comboBox.addItem(yellow)
        val colorColumn = tableView!!.getColumn(getString("TableDemo.favorite_color"))
        // Use the combo box as the editor in the "Favorite Color" column.
        colorColumn.cellEditor = DefaultCellEditor(comboBox)
        colorRenderer.horizontalAlignment = JLabel.CENTER
        colorColumn.cellRenderer = colorRenderer
        tableView!!.rowHeight = INITIAL_ROWHEIGHT
        scrollpane = JScrollPane(tableView)
        return scrollpane!!
    }

    private fun printTable() {
        val headerFmt: MessageFormat?
        val footerFmt: MessageFormat?
        val printMode = if (fitWidth.isSelected) PrintMode.FIT_WIDTH else PrintMode.NORMAL
        var text: String?
        text = headerTextField.text
        headerFmt = if (text != null && text.length > 0) {
            MessageFormat(text)
        } else {
            null
        }
        text = footerTextField.text
        footerFmt = if (text != null && text.length > 0) {
            MessageFormat(text)
        } else {
            null
        }
        try {
            val status = tableView!!.print(printMode, headerFmt, footerFmt)
            if (status) {
                JOptionPane.showMessageDialog(tableView!!.parent,
                        getString("TableDemo.printingComplete"),
                        getString("TableDemo.printingResult"),
                        JOptionPane.INFORMATION_MESSAGE)
            } else {
                JOptionPane.showMessageDialog(tableView!!.parent,
                        getString("TableDemo.printingCancelled"),
                        getString("TableDemo.printingResult"),
                        JOptionPane.INFORMATION_MESSAGE)
            }
        } catch (pe: PrinterException) {
            val errorMessage = MessageFormat.format(getString("TableDemo.printingFailed"),
                    *arrayOf<Any?>(pe.message))
            JOptionPane.showMessageDialog(tableView!!.parent,
                    errorMessage,
                    getString("TableDemo.printingResult"),
                    JOptionPane.ERROR_MESSAGE)
        } catch (se: SecurityException) {
            val errorMessage = MessageFormat.format(getString("TableDemo.printingFailed"),
                    *arrayOf<Any?>(se.message))
            JOptionPane.showMessageDialog(tableView!!.parent,
                    errorMessage,
                    getString("TableDemo.printingResult"),
                    JOptionPane.ERROR_MESSAGE)
        }
    }

    internal inner class NamedColor(color: Color, var name: String) : Color(color.rgb) {
        val textColor: Color
            get() {
                val r = red
                val g = green
                val b = blue
                return if (r > 240 || g > 240) {
                    black
                } else {
                    white
                }
            }

        override fun toString(): String {
            return name
        }

    }

    internal inner class ColumnLayout : LayoutManager {
        var xInset = 5
        var yInset = 5
        var yGap = 2
        override fun addLayoutComponent(s: String, c: Component) {}
        override fun layoutContainer(c: Container) {
            val insets = c.insets
            var height = yInset + insets.top
            val children = c.components
            var compSize: Dimension? = null
            for (i in children.indices) {
                compSize = children[i].preferredSize
                children[i].setSize(compSize.width, compSize.height)
                children[i].setLocation(xInset + insets.left, height)
                height += compSize.height + yGap
            }
        }

        override fun minimumLayoutSize(c: Container): Dimension {
            val insets = c.insets
            var height = yInset + insets.top
            var width = 0 + insets.left + insets.right
            val children = c.components
            var compSize: Dimension? = null
            for (i in children.indices) {
                compSize = children[i].preferredSize
                height += compSize.height + yGap
                width = Math.max(width, compSize.width + insets.left + insets.right + xInset * 2)
            }
            height += insets.bottom
            return Dimension(width, height)
        }

        override fun preferredLayoutSize(c: Container): Dimension {
            return minimumLayoutSize(c)
        }

        override fun removeLayoutComponent(c: Component) {}
    }

    public override fun updateDragEnabled(dragEnabled: Boolean) {
        tableView!!.dragEnabled = dragEnabled
        headerTextField.dragEnabled = dragEnabled
        footerTextField.dragEnabled = dragEnabled
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = TableDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * TableDemo Constructor
     */
    init {
        demoPanel?.layout = BorderLayout()
        controlPanel = JPanel()
        controlPanel.layout = BoxLayout(controlPanel, BoxLayout.X_AXIS)
        val cbPanel = JPanel(GridLayout(3, 2))
        val labelPanel: JPanel = object : JPanel(GridLayout(2, 1)) {
            override fun getMaximumSize(): Dimension {
                return Dimension(preferredSize.width, super.getMaximumSize().height)
            }
        }
        val sliderPanel: JPanel = object : JPanel(GridLayout(2, 1)) {
            override fun getMaximumSize(): Dimension {
                return Dimension(preferredSize.width, super.getMaximumSize().height)
            }
        }
        val comboPanel = JPanel(GridLayout(2, 1))
        val printPanel = JPanel(ColumnLayout())
        demoPanel?.add(controlPanel, BorderLayout.NORTH)
        val relatedComponents = Vector<Any?>()


        // check box panel
        isColumnReorderingAllowedCheckBox = JCheckBox(getString("TableDemo.reordering_allowed"), true)
        isColumnReorderingAllowedCheckBox.addActionListener { e ->
            val flag = (e.source as JCheckBox).isSelected
            tableView!!.tableHeader.reorderingAllowed = flag
            tableView!!.repaint()
        }
        showHorizontalLinesCheckBox = JCheckBox(getString("TableDemo.horz_lines"), true)
        showHorizontalLinesCheckBox.addActionListener { e ->
            val flag = (e.source as JCheckBox).isSelected
            tableView!!.showHorizontalLines = flag
            tableView!!.repaint()
        }
        showVerticalLinesCheckBox = JCheckBox(getString("TableDemo.vert_lines"), true)
        showVerticalLinesCheckBox.addActionListener { e ->
            val flag = (e.source as JCheckBox).isSelected
            tableView!!.showVerticalLines = flag
            tableView!!.repaint()
        }

        // Show that showHorizontal/Vertical controls are related
        relatedComponents.removeAllElements()
        relatedComponents.add(showHorizontalLinesCheckBox)
        relatedComponents.add(showVerticalLinesCheckBox)
        buildAccessibleGroup(relatedComponents)
        isRowSelectionAllowedCheckBox = JCheckBox(getString("TableDemo.row_selection"), true)
        isRowSelectionAllowedCheckBox.addActionListener { e ->
            val flag = (e.source as JCheckBox).isSelected
            tableView!!.rowSelectionAllowed = flag
            tableView!!.repaint()
        }
        isColumnSelectionAllowedCheckBox = JCheckBox(getString("TableDemo.column_selection"), false)
        isColumnSelectionAllowedCheckBox.addActionListener { e ->
            val flag = (e.source as JCheckBox).isSelected
            tableView!!.columnSelectionAllowed = flag
            tableView!!.repaint()
        }

        // Show that row/column selections are related
        relatedComponents.removeAllElements()
        relatedComponents.add(isColumnSelectionAllowedCheckBox)
        relatedComponents.add(isRowSelectionAllowedCheckBox)
        buildAccessibleGroup(relatedComponents)
        cbPanel.add(isColumnReorderingAllowedCheckBox)
        cbPanel.add(isRowSelectionAllowedCheckBox)
        cbPanel.add(showHorizontalLinesCheckBox)
        cbPanel.add(isColumnSelectionAllowedCheckBox)
        cbPanel.add(showVerticalLinesCheckBox)


        // label panel
        interCellSpacingLabel = JLabel(getString("TableDemo.intercell_spacing_colon"))
        labelPanel.add(interCellSpacingLabel)
        rowHeightLabel = JLabel(getString("TableDemo.row_height_colon"))
        labelPanel.add(rowHeightLabel)


        // slider panel
        interCellSpacingSlider = JSlider(JSlider.HORIZONTAL, 0, 10, 1)
        interCellSpacingSlider.accessibleContext.accessibleName = getString("TableDemo.intercell_spacing")
        interCellSpacingLabel.labelFor = interCellSpacingSlider
        sliderPanel.add(interCellSpacingSlider)
        interCellSpacingSlider.addChangeListener { e ->
            val spacing = (e.source as JSlider).value
            tableView!!.intercellSpacing = Dimension(spacing, spacing)
            tableView!!.repaint()
        }
        rowHeightSlider = JSlider(JSlider.HORIZONTAL, 5, 100, INITIAL_ROWHEIGHT)
        rowHeightSlider.accessibleContext.accessibleName = getString("TableDemo.row_height")
        rowHeightLabel.labelFor = rowHeightSlider
        sliderPanel.add(rowHeightSlider)
        rowHeightSlider.addChangeListener { e ->
            val height = (e.source as JSlider).value
            tableView!!.rowHeight = height
            tableView!!.repaint()
        }

        // Show that spacing controls are related
        relatedComponents.removeAllElements()
        relatedComponents.add(interCellSpacingSlider)
        relatedComponents.add(rowHeightSlider)
        buildAccessibleGroup(relatedComponents)


        // Create the table.
        tableAggregate = createTable()
        demoPanel?.add(tableAggregate, BorderLayout.CENTER)


        // ComboBox for selection modes.
        val selectMode = JPanel()
        selectMode.layout = BoxLayout(selectMode, BoxLayout.X_AXIS)
        selectMode.border = TitledBorder(getString("TableDemo.selection_mode"))
        selectionModeComboBox?.addItem(getString("TableDemo.single"))
        selectionModeComboBox?.addItem(getString("TableDemo.one_range"))
        selectionModeComboBox?.addItem(getString("TableDemo.multiple_ranges"))
        selectionModeComboBox?.setSelectedIndex(tableView!!.selectionModel.selectionMode)
        selectionModeComboBox?.addItemListener(ItemListener { e ->
            val source = e.source as JComboBox<*>
            tableView!!.setSelectionMode(source.selectedIndex)
        })
        selectMode.add(Box.createHorizontalStrut(2))
        selectMode.add(selectionModeComboBox)
        selectMode.add(Box.createHorizontalGlue())
        comboPanel.add(selectMode)

        // Combo box for table resize mode.
        val resizeMode = JPanel()
        resizeMode.layout = BoxLayout(resizeMode, BoxLayout.X_AXIS)
        resizeMode.border = TitledBorder(getString("TableDemo.autoresize_mode"))
        resizeModeComboBox.addItem(getString("TableDemo.off"))
        resizeModeComboBox.addItem(getString("TableDemo.column_boundaries"))
        resizeModeComboBox.addItem(getString("TableDemo.subsequent_columns"))
        resizeModeComboBox.addItem(getString("TableDemo.last_column"))
        resizeModeComboBox.addItem(getString("TableDemo.all_columns"))
        resizeModeComboBox.setSelectedIndex(tableView!!.autoResizeMode)
        resizeModeComboBox.addItemListener(ItemListener { e ->
            val source = e.source as JComboBox<*>
            tableView!!.autoResizeMode = source.selectedIndex
        })
        resizeMode.add(Box.createHorizontalStrut(2))
        resizeMode.add(resizeModeComboBox)
        resizeMode.add(Box.createHorizontalGlue())
        comboPanel.add(resizeMode)

        // print panel
        printPanel.border = TitledBorder(getString("TableDemo.printing"))
        headerLabel = JLabel(getString("TableDemo.header"))
        footerLabel = JLabel(getString("TableDemo.footer"))
        headerTextField = JTextField(getString("TableDemo.headerText"), 15)
        footerTextField = JTextField(getString("TableDemo.footerText"), 15)
        fitWidth = JCheckBox(getString("TableDemo.fitWidth"), true)
        printButton = JButton(getString("TableDemo.print"))
        printButton.addActionListener { printTable() }
        printPanel.add(headerLabel)
        printPanel.add(headerTextField)
        printPanel.add(footerLabel)
        printPanel.add(footerTextField)
        val buttons = JPanel()
        buttons.add(fitWidth)
        buttons.add(printButton)
        printPanel.add(buttons)

        // Show that printing controls are related
        relatedComponents.removeAllElements()
        relatedComponents.add(headerTextField)
        relatedComponents.add(footerTextField)
        relatedComponents.add(printButton)
        buildAccessibleGroup(relatedComponents)

        // wrap up the panels and add them
        val sliderWrapper = JPanel()
        sliderWrapper.layout = BoxLayout(sliderWrapper, BoxLayout.X_AXIS)
        sliderWrapper.add(labelPanel)
        sliderWrapper.add(sliderPanel)
        sliderWrapper.add(Box.createHorizontalGlue())
        sliderWrapper.border = BorderFactory.createEmptyBorder(0, 4, 0, 0)
        val leftWrapper = JPanel()
        leftWrapper.layout = BoxLayout(leftWrapper, BoxLayout.Y_AXIS)
        leftWrapper.add(cbPanel)
        leftWrapper.add(sliderWrapper)

        // add everything
        controlPanel.border = BorderFactory.createEmptyBorder(0, 0, 2, 0)
        controlPanel.add(leftWrapper)
        controlPanel.add(comboPanel)
        controlPanel.add(printPanel)
        setTableControllers() // Set accessibility information
        demoPanel?.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)!!
                .put(KeyStroke.getKeyStroke("ctrl P"), "print")
        demoPanel?.actionMap!!.put("print", object : AbstractAction() {
            override fun actionPerformed(ae: ActionEvent) {
                printTable()
            }
        })
    } // TableDemo()
}