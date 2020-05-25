package com.ogogon.matreshka.demo.swingset2

import java.awt.*
import java.awt.event.*
import java.util.*
import javax.swing.*
import javax.swing.UIManager.LookAndFeelInfo
import javax.swing.border.EmptyBorder
import javax.swing.border.EtchedBorder
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import javax.swing.plaf.metal.DefaultMetalTheme
import javax.swing.plaf.metal.MetalLookAndFeel
import javax.swing.plaf.metal.MetalTheme
import javax.swing.plaf.metal.OceanTheme

const val ALL_SCREENS = -1


fun createImageIcon(filename: String?, description: String?): ImageIcon {
    val path = "/resources/images/$filename"
    return ImageIcon(path.javaClass.getResource(path))
}

class SwingSet2 @JvmOverloads constructor(applet: SwingSet2Applet?, gc: GraphicsConfiguration? = null) : JPanel() {
    var demos = arrayOf(
            "ButtonDemo",
            "ColorChooserDemo",
            "ComboBoxDemo",
            "FileChooserDemo",
            "HtmlDemo",
            "ListDemo",
            "OptionPaneDemo",
            "ProgressBarDemo",
            "ScrollPaneDemo",
            "SliderDemo",
            "SplitPaneDemo",
            "TabbedPaneDemo",
            "TableDemo",
            "ToolTipDemo",
            "TreeDemo"
    )

    fun loadDemos() {
        var i = 0
        while (i < demos.size) {
            if (isApplet() && demos[i] == "FileChooserDemo") {
                // don't load the file chooser demo if we are
                // an applet
            } else {
                loadDemo(demos[i])
            }
            i++
        }
    }

    // List of demos
    private val demosList = ArrayList<DemoModule?>()

    // Box spacers
    private val HGAP = Dimension(1, 5)
    private val VGAP = Dimension(5, 1)

    // A place to hold on to the visible demo
    private var currentDemo: DemoModule? = null
    private var demoPanel: JPanel? = null

    // About Box
    private var aboutBox: JDialog? = null

    // Status Bar
    private var statusField: JTextField? = null

    /**
     * Returns the toolbar
     */
    // Tool Bar
    private var toolBar: ToggleButtonToolBar? = null

    /**
     * Returns the toolbar button group
     */
    val toolBarGroup = ButtonGroup()

    /**
     * Returns the menubar
     */
    // Menus
    var menuBar: JMenuBar? = null
        private set
    private var lafMenu: JMenu? = null
    private var themesMenu: JMenu? = null
    private var audioMenu: JMenu? = null
    private var optionsMenu: JMenu? = null
    private val lafMenuGroup = ButtonGroup()
    private val themesMenuGroup = ButtonGroup()
    private val audioMenuGroup = ButtonGroup()

    // Popup menu
    private var popupMenu: JPopupMenu? = null
    private val popupMenuGroup = ButtonGroup()


    var currentLookAndFeel: LookAndFeelData? = null
    var lookAndFeelData: Array<LookAndFeelData>? = null

    /**
     * Returns the frame instance
     */
    // Used only if swingset is an application
    var frame: JFrame? = null

    // Used only if swingset is an applet
    private val applet: SwingSet2Applet? = null

    // To debug or not to debug, that is the question
    private val DEBUG = true
    private var debugCounter = 0

    // The tab pane that holds the demo
    private var tabbedPane: JTabbedPane? = null
    private var demoSrcPane: JEditorPane? = null

    // contentPane cache, saved from the applet or application frame
    var contentPane: Container? = null
    var dragEnabled = false

    // *******************************************************
    // *************** Demo Loading Methods ******************
    // *******************************************************
    fun initializeDemo() {
        val top = JPanel()
        top.layout = BorderLayout()
        add(top, BorderLayout.NORTH)
        menuBar = createMenus()
        if (isApplet()) {
            applet!!.jMenuBar = menuBar
        } else {
            frame!!.jMenuBar = menuBar
        }

        // creates popup menu accessible via keyboard
        popupMenu = createPopupMenu()
        val toolbarPanel = ToolBarPanel()
        toolbarPanel.layout = BorderLayout()
        toolBar = ToggleButtonToolBar()
        toolbarPanel.add(toolBar, BorderLayout.CENTER)
        top.add(toolbarPanel, BorderLayout.SOUTH)
        toolbarPanel.addContainerListener(toolbarPanel)
        tabbedPane = JTabbedPane()
        add(tabbedPane, BorderLayout.CENTER)
        tabbedPane!!.model.addChangeListener(TabListener())
        statusField = JTextField("")
        statusField!!.isEditable = false
        add(statusField, BorderLayout.SOUTH)
        demoPanel = JPanel()
        demoPanel!!.layout = BorderLayout()
        demoPanel!!.border = EtchedBorder()
        tabbedPane!!.addTab("Hi There!", demoPanel)

        // Add html src code viewer
        demoSrcPane = JEditorPane("text/html", getString("SourceCode.loading"))
        demoSrcPane!!.isEditable = false
        val scroller = JScrollPane()
        scroller.viewport.add(demoSrcPane)
        tabbedPane!!.addTab(
                getString("TabbedPane.src_label"),
                null,
                scroller,
                getString("TabbedPane.src_tooltip")
        )
    }

    var currentTabDemo: DemoModule? = null

    internal inner class TabListener : ChangeListener {
        override fun stateChanged(e: ChangeEvent) {
            val model = e.source as SingleSelectionModel
            val srcSelected = model.selectedIndex == 1
            if (currentTabDemo !== currentDemo && demoSrcPane != null && srcSelected) {
                demoSrcPane!!.text = getString("SourceCode.loading")
                repaint()
            }
            if (currentTabDemo !== currentDemo && srcSelected) {
                currentTabDemo = currentDemo
                setSourceCode(currentDemo)
            }
        }
    }

    /**
     * Create menus
     */
    fun createMenus(): JMenuBar {
        var mi: JMenuItem
        // ***** create the menubar ****
        val menuBar = JMenuBar()
        menuBar.accessibleContext.accessibleName = getString("MenuBar.accessible_description")

        // ***** create File menu
        val fileMenu = menuBar.add(JMenu(getString("FileMenu.file_label"))) as JMenu
        fileMenu.setMnemonic(getMnemonic("FileMenu.file_mnemonic"))
        fileMenu.accessibleContext.accessibleDescription = getString("FileMenu.accessible_description")
        createMenuItem(fileMenu, "FileMenu.about_label", "FileMenu.about_mnemonic",
                "FileMenu.about_accessible_description", AboutAction(this))
        fileMenu.addSeparator()
        createMenuItem(fileMenu, "FileMenu.open_label", "FileMenu.open_mnemonic",
                "FileMenu.open_accessible_description", null)
        createMenuItem(fileMenu, "FileMenu.save_label", "FileMenu.save_mnemonic",
                "FileMenu.save_accessible_description", null)
        createMenuItem(fileMenu, "FileMenu.save_as_label", "FileMenu.save_as_mnemonic",
                "FileMenu.save_as_accessible_description", null)
        if (!isApplet()) {
            fileMenu.addSeparator()
            createMenuItem(fileMenu, "FileMenu.exit_label", "FileMenu.exit_mnemonic",
                    "FileMenu.exit_accessible_description", ExitAction(this)
            )
        }

        // Create these menu items for the first SwingSet only.
        if (numSSs == 0) {
            // ***** create laf switcher menu
            lafMenu = menuBar.add(JMenu(getString("LafMenu.laf_label"))) as JMenu
            lafMenu!!.setMnemonic(getMnemonic("LafMenu.laf_mnemonic"))
            lafMenu!!.accessibleContext.accessibleDescription = getString("LafMenu.laf_accessible_description")
            for (lafData in this.lookAndFeelData!!) {
                mi = createLafMenuItem(lafMenu, lafData)
                mi.isSelected = lafData == currentLookAndFeel
            }

            // ***** create themes menu
            themesMenu = menuBar.add(JMenu(getString("ThemesMenu.themes_label"))) as JMenu
            themesMenu!!.setMnemonic(getMnemonic("ThemesMenu.themes_mnemonic"))
            themesMenu!!.accessibleContext.accessibleDescription = getString("ThemesMenu.themes_accessible_description")

            // ***** create the audio submenu under the theme menu
            audioMenu = themesMenu!!.add(JMenu(getString("AudioMenu.audio_label"))) as JMenu
            audioMenu!!.setMnemonic(getMnemonic("AudioMenu.audio_mnemonic"))
            audioMenu!!.accessibleContext.accessibleDescription = getString("AudioMenu.audio_accessible_description")
            createAudioMenuItem(audioMenu, "AudioMenu.on_label",
                    "AudioMenu.on_mnemonic",
                    "AudioMenu.on_accessible_description",
                    OnAudioAction(this))
            mi = createAudioMenuItem(audioMenu, "AudioMenu.default_label",
                    "AudioMenu.default_mnemonic",
                    "AudioMenu.default_accessible_description",
                    DefaultAudioAction(this))
            mi.isSelected = true // This is the default feedback setting
            createAudioMenuItem(audioMenu, "AudioMenu.off_label",
                    "AudioMenu.off_mnemonic",
                    "AudioMenu.off_accessible_description",
                    OffAudioAction(this))


            // ***** create the font submenu under the theme menu
            val fontMenu = themesMenu!!.add(JMenu(getString("FontMenu.fonts_label"))) as JMenu
            fontMenu.setMnemonic(getMnemonic("FontMenu.fonts_mnemonic"))
            fontMenu.accessibleContext.accessibleDescription = getString("FontMenu.fonts_accessible_description")
            val fontButtonGroup = ButtonGroup()
            mi = createButtonGroupMenuItem(fontMenu, "FontMenu.plain_label",
                    "FontMenu.plain_mnemonic",
                    "FontMenu.plain_accessible_description",
                    ChangeFontAction(this, true), fontButtonGroup)
            mi.isSelected = true
            mi = createButtonGroupMenuItem(fontMenu, "FontMenu.bold_label",
                    "FontMenu.bold_mnemonic",
                    "FontMenu.bold_accessible_description",
                    ChangeFontAction(this, false), fontButtonGroup)


            // *** now back to adding color/font themes to the theme menu
            mi = createThemesMenuItem(themesMenu, "ThemesMenu.ocean_label",
                    "ThemesMenu.ocean_mnemonic",
                    "ThemesMenu.ocean_accessible_description",
                    OceanTheme())
            mi.isSelected = true // This is the default theme
            createThemesMenuItem(themesMenu, "ThemesMenu.steel_label",
                    "ThemesMenu.steel_mnemonic",
                    "ThemesMenu.steel_accessible_description",
                    DefaultMetalTheme())
            createThemesMenuItem(themesMenu, "ThemesMenu.aqua_label", "ThemesMenu.aqua_mnemonic",
                    "ThemesMenu.aqua_accessible_description", AquaTheme())
            createThemesMenuItem(themesMenu, "ThemesMenu.charcoal_label", "ThemesMenu.charcoal_mnemonic",
                    "ThemesMenu.charcoal_accessible_description", CharcoalTheme())
            createThemesMenuItem(themesMenu, "ThemesMenu.contrast_label", "ThemesMenu.contrast_mnemonic",
                    "ThemesMenu.contrast_accessible_description", ContrastTheme())
            createThemesMenuItem(themesMenu, "ThemesMenu.emerald_label", "ThemesMenu.emerald_mnemonic",
                    "ThemesMenu.emerald_accessible_description", EmeraldTheme())
            createThemesMenuItem(themesMenu, "ThemesMenu.ruby_label", "ThemesMenu.ruby_mnemonic",
                    "ThemesMenu.ruby_accessible_description", RubyTheme())

            // Enable theme menu based on L&F
            themesMenu!!.isEnabled = "Metal" == currentLookAndFeel?.name

            // ***** create the options menu
            optionsMenu = menuBar.add(
                    JMenu(getString("OptionsMenu.options_label"))) as JMenu
            optionsMenu!!.setMnemonic(getMnemonic("OptionsMenu.options_mnemonic"))
            optionsMenu!!.accessibleContext.accessibleDescription = getString("OptionsMenu.options_accessible_description")

            // ***** create tool tip submenu item.
            mi = createCheckBoxMenuItem(optionsMenu, "OptionsMenu.tooltip_label",
                    "OptionsMenu.tooltip_mnemonic",
                    "OptionsMenu.tooltip_accessible_description",
                    ToolTipAction())
            mi.isSelected = true

            // ***** create drag support submenu item.
            createCheckBoxMenuItem(optionsMenu, "OptionsMenu.dragEnabled_label",
                    "OptionsMenu.dragEnabled_mnemonic",
                    "OptionsMenu.dragEnabled_accessible_description",
                    DragSupportAction())
        }


        // ***** create the multiscreen menu, if we have multiple screens
        if (!isApplet()) {
            val screens = GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices
            if (screens.size > 1) {
                val multiScreenMenu = menuBar.add(JMenu(
                        getString("MultiMenu.multi_label"))) as JMenu
                multiScreenMenu.setMnemonic(getMnemonic("MultiMenu.multi_mnemonic"))
                multiScreenMenu.accessibleContext.accessibleDescription = getString("MultiMenu.multi_accessible_description")
                createMultiscreenMenuItem(multiScreenMenu, ALL_SCREENS)
                for (i in screens.indices) {
                    createMultiscreenMenuItem(multiScreenMenu, i)
                }
            }
        }
        return menuBar
    }

    /**
     * Create a checkbox menu menu item
     */
    private fun createCheckBoxMenuItem(menu: JMenu?, label: String,
                                       mnemonic: String,
                                       accessibleDescription: String,
                                       action: Action): JMenuItem {
        val mi = menu!!.add(
                JCheckBoxMenuItem(getString(label))) as JCheckBoxMenuItem
        mi.setMnemonic(getMnemonic(mnemonic))
        mi.accessibleContext.accessibleDescription = getString(
                accessibleDescription)
        mi.addActionListener(action)
        return mi
    }

    /**
     * Create a radio button menu menu item for items that are part of a
     * button group.
     */
    private fun createButtonGroupMenuItem(menu: JMenu, label: String,
                                          mnemonic: String,
                                          accessibleDescription: String,
                                          action: Action,
                                          buttonGroup: ButtonGroup): JMenuItem {
        val mi = menu.add(
                JRadioButtonMenuItem(getString(label))) as JRadioButtonMenuItem
        buttonGroup.add(mi)
        mi.setMnemonic(getMnemonic(mnemonic))
        mi.accessibleContext.accessibleDescription = getString(
                accessibleDescription)
        mi.addActionListener(action)
        return mi
    }

    /**
     * Create the theme's audio submenu
     */
    fun createAudioMenuItem(menu: JMenu?, label: String,
                            mnemonic: String,
                            accessibleDescription: String,
                            action: Action?): JMenuItem {
        val mi = menu!!.add(JRadioButtonMenuItem(getString(label))) as JRadioButtonMenuItem
        audioMenuGroup.add(mi)
        mi.setMnemonic(getMnemonic(mnemonic))
        mi.accessibleContext.accessibleDescription = getString(accessibleDescription)
        mi.addActionListener(action)
        return mi
    }

    /**
     * Creates a generic menu item
     */
    fun createMenuItem(menu: JMenu, label: String, mnemonic: String,
                       accessibleDescription: String, action: Action?): JMenuItem {
        val mi = menu.add(JMenuItem(getString(label))) as JMenuItem
        mi.setMnemonic(getMnemonic(mnemonic))
        mi.accessibleContext.accessibleDescription = getString(accessibleDescription)
        mi.addActionListener(action)
        if (action == null) {
            mi.isEnabled = false
        }
        return mi
    }

    /**
     * Creates a JRadioButtonMenuItem for the Themes menu
     */
    fun createThemesMenuItem(menu: JMenu?, label: String, mnemonic: String,
                             accessibleDescription: String, theme: MetalTheme?): JMenuItem {
        val mi = menu!!.add(JRadioButtonMenuItem(getString(label))) as JRadioButtonMenuItem
        themesMenuGroup.add(mi)
        mi.setMnemonic(getMnemonic(mnemonic))
        mi.accessibleContext.accessibleDescription = getString(accessibleDescription)
        mi.addActionListener(ChangeThemeAction(this, theme))
        return mi
    }

    /**
     * Creates a JRadioButtonMenuItem for the Look and Feel menu
     */
    fun createLafMenuItem(menu: JMenu?, lafData: LookAndFeelData): JMenuItem {
        val mi = menu!!.add(JRadioButtonMenuItem(lafData.label))
        lafMenuGroup.add(mi)
        mi.setMnemonic(lafData.mnemonic)
        mi.accessibleContext.accessibleDescription = lafData.accDescription
        mi.addActionListener(ChangeLookAndFeelAction(this, lafData))
        return mi
    }

    /**
     * Creates a multi-screen menu item
     */
    fun createMultiscreenMenuItem(menu: JMenu, screen: Int): JMenuItem? {
        var mi: JMenuItem? = null
        if (screen == ALL_SCREENS) {
            mi = menu.add(JMenuItem(getString("MultiMenu.all_label"))) as JMenuItem
            mi!!.setMnemonic(getMnemonic("MultiMenu.all_mnemonic"))
            mi.accessibleContext.accessibleDescription = getString(
                    "MultiMenu.all_accessible_description")
        } else {
            mi = menu.add(JMenuItem(getString("MultiMenu.single_label") + " " +
                    screen)) as JMenuItem
            mi.mnemonic = KeyEvent.VK_0 + screen
            mi.accessibleContext.accessibleDescription = getString(
                    "MultiMenu.single_accessible_description") + " " + screen
        }
        mi!!.addActionListener(MultiScreenAction(this, screen))
        return mi
    }

    fun createPopupMenu(): JPopupMenu {
        val popup = JPopupMenu("JPopupMenu demo")
        for (lafData in lookAndFeelData!!) {
            createPopupMenuItem(popup, lafData)
        }

        // register key binding to activate popup menu
        val map = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
        map.put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, InputEvent.SHIFT_MASK),
                "postMenuAction")
        map.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU, 0), "postMenuAction")
        actionMap.put("postMenuAction", ActivatePopupMenuAction(this, popup))
        return popup
    }

    /**
     * Creates a JMenuItem for the Look and Feel popup menu
     */
    fun createPopupMenuItem(menu: JPopupMenu, lafData: LookAndFeelData): JMenuItem {
        val mi = menu.add(JMenuItem(lafData.label))
        popupMenuGroup.add(mi)
        mi.setMnemonic(lafData.mnemonic)
        mi.accessibleContext.accessibleDescription = lafData.accDescription
        mi.addActionListener(ChangeLookAndFeelAction(this, lafData))
        return mi
    }

    /**
     * Load the first demo. This is done separately from the remaining demos
     * so that we can get SwingSet2 up and available to the user quickly.
     */
    fun preloadFirstDemo() {
        val demo = addDemo(InternalFrameDemo(this))
        setDemo(demo)
    }

    /**
     * Add a demo to the toolbar
     */
    fun addDemo(demo: DemoModule?): DemoModule? {
        demosList.add(demo)
        if (dragEnabled) {
            demo!!.updateDragEnabled(true)
        }
        // do the following on the gui thread
        SwingUtilities.invokeLater(object : SwingSetRunnable(this, demo) {
            override fun run() {
                val action = SwitchToDemoAction(swingset, obj as DemoModule?)
                val tb = swingset.toolBar!!.addToggleButton(action)
                swingset.toolBarGroup.add(tb)
                if (swingset.toolBarGroup.selection == null) {
                    tb.isSelected = true
                }
                tb.text = null
                tb.toolTipText = (obj as DemoModule?)?.toolTip
                if (demos[demos.size - 1] == obj!!.javaClass.name) {
                    setStatus(getString("Status.popupMenuAccessible"))
                }
            }
        })
        return demo
    }

    /**
     * Sets the current demo
     */
    fun setDemo(demo: DemoModule?) {
        currentDemo = demo

        // Ensure panel's UI is current before making visible
        val currentDemoPanel: JComponent? = demo?.demoPanel
        SwingUtilities.updateComponentTreeUI(currentDemoPanel)
        demoPanel!!.removeAll()
        demoPanel!!.add(currentDemoPanel, BorderLayout.CENTER)
        tabbedPane!!.selectedIndex = 0
        tabbedPane!!.setTitleAt(0, demo!!.name)
        tabbedPane!!.setToolTipTextAt(0, demo.toolTip)
    }

    /**
     * Bring up the SwingSet2 demo by showing the frame (only
     * applicable if coming up as an application, not an applet);
     */
    fun showSwingSet2() {
        if (!isApplet() && frame != null) {
            // put swingset in a frame and show it
            val f = frame
            f!!.title = getString("Frame.title")
            f.contentPane.add(this, BorderLayout.CENTER)
            f.pack()
            val screenRect = f.graphicsConfiguration.bounds
            val screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
                    f.graphicsConfiguration)

            // Make sure we don't place the demo off the screen.
            val centerWidth = if (screenRect.width < f.size.width) screenRect.x else screenRect.x + screenRect.width / 2 - f.size.width / 2
            var centerHeight = if (screenRect.height < f.size.height) screenRect.y else screenRect.y + screenRect.height / 2 - f.size.height / 2
            centerHeight = if (centerHeight < screenInsets.top) screenInsets.top else centerHeight
            f.setLocation(centerWidth, centerHeight)
            f.show()
            numSSs++
            swingSets.add(this)
        }
    }
    // *******************************************************
    // ****************** Utility Methods ********************
    // *******************************************************
    /**
     * Loads a demo from a classname
     */
    fun loadDemo(classname: String) {
        setStatus(getString("Status.loading") + getString("$classname.name"))
        var demo: DemoModule? = null
        try {
            val demoClass = Class.forName(classname)
            val demoConstructor = demoClass.getConstructor(*arrayOf<Class<*>>(SwingSet2::class.java))
            demo = demoConstructor.newInstance(*arrayOf<Any>(this)) as DemoModule
            addDemo(demo)
        } catch (e: Exception) {
            println("Error occurred loading demo: $classname")
        }
    }

    /**
     * Determines if this is an applet or application
     */
    fun isApplet(): Boolean {
        return applet != null
    }

    /**
     * Returns the applet instance
     */
    fun getApplet(): SwingSet2Applet? {
        return applet
    }


    /**
     * Set the status
     */
    fun setStatus(s: String?) {
        // do the following on the gui thread
        SwingUtilities.invokeLater(object : SwingSetRunnable(this, s) {
            override fun run() {
                swingset.statusField!!.text = obj as String?
            }
        })
    }



    /**
     * Returns a mnemonic from the resource bundle. Typically used as
     * keyboard shortcuts in menu items.
     */
    fun getMnemonic(key: String): Char {
        return getString(key)[0]
    }



    /**
     * If DEBUG is defined, prints debug information out to std ouput.
     */
    fun debug(s: String) {
        if (DEBUG) {
            println(debugCounter++.toString() + ": " + s)
        }
    }

    /**
     * Stores the current L&F, and calls updateLookAndFeel, below
     */
    fun setLookAndFeel(laf: LookAndFeelData) {
        if (currentLookAndFeel != laf) {
            currentLookAndFeel = laf
            /* The recommended way of synchronizing state between multiple
             * controls that represent the same command is to use Actions.
             * The code below is a workaround and will be replaced in future
             * version of SwingSet2 demo.
             */
            val lafName = laf.label
            themesMenu!!.isEnabled = laf.name == "Metal"
            updateLookAndFeel()
            for (i in 0 until lafMenu!!.itemCount) {
                val item = lafMenu!!.getItem(i)
                item.isSelected = item.text == lafName
            }
        }
    }

    private fun updateThisSwingSet() {
        if (isApplet()) {
            SwingUtilities.updateComponentTreeUI(getApplet())
        } else {
            val frame = frame
            if (frame == null) {
                SwingUtilities.updateComponentTreeUI(this)
            } else {
                SwingUtilities.updateComponentTreeUI(frame)
            }
        }
        SwingUtilities.updateComponentTreeUI(popupMenu)
        if (aboutBox != null) {
            SwingUtilities.updateComponentTreeUI(aboutBox)
        }
    }

    /**
     * Sets the current L&F on each demo module
     */
    fun updateLookAndFeel() {
        try {
            UIManager.setLookAndFeel(currentLookAndFeel?.className)
            if (isApplet()) {
                updateThisSwingSet()
            } else {
                for (ss in swingSets) {
                    ss.updateThisSwingSet()
                }
            }
        } catch (ex: Exception) {
            println("Failed loading L&F: $currentLookAndFeel")
            println(ex)
        }
    }

    /**
     * Loads and puts the source code text into JEditorPane in the "Source Code" tab
     */
    fun setSourceCode(demo: DemoModule?) {
        // do the following on the gui thread
        SwingUtilities.invokeLater(object : SwingSetRunnable(this, demo) {
            override fun run() {
                swingset.demoSrcPane!!.text = (obj as DemoModule?)?.sourceCode
                swingset.demoSrcPane!!.caretPosition = 0
            }
        })
    }

    protected inner class ToggleButtonToolBar : JToolBar() {
        fun addToggleButton(a: Action): JToggleButton {
            val tb = JToggleButton(
                    a.getValue(Action.NAME) as String,
                    a.getValue(Action.SMALL_ICON) as Icon
            )
            tb.margin = zeroInsets
            tb.text = null
            tb.isEnabled = a.isEnabled
            tb.toolTipText = a.getValue(Action.SHORT_DESCRIPTION) as String
            tb.action = a
            add(tb)
            return tb
        }
    }

    // *******************************************************
    // *********  ToolBar Panel / Docking Listener ***********
    // *******************************************************
    internal inner class ToolBarPanel : JPanel(), ContainerListener {
        override fun contains(x: Int, y: Int): Boolean {
            val c: Component? = parent
            return if (c != null) {
                val r = c.bounds
                x >= 0 && x < r.width && y >= 0 && y < r.height
            } else {
                super.contains(x, y)
            }
        }

        override fun componentAdded(e: ContainerEvent) {
            val c = e.container.parent
            if (c != null) {
                c.parent.validate()
                c.parent.repaint()
            }
        }

        override fun componentRemoved(e: ContainerEvent) {
            val c = e.container.parent
            if (c != null) {
                c.parent.validate()
                c.parent.repaint()
            }
        }
    }
    // *******************************************************
    // ******************   Runnables  ***********************
    // *******************************************************
    /**
     * Generic SwingSet2 runnable. This is intended to run on the
     * AWT gui event thread so as not to muck things up by doing
     * gui work off the gui thread. Accepts a SwingSet2 and an Object
     * as arguments, which gives subtypes of this class the two
     * "must haves" needed in most runnables for this demo.
     */
    internal open inner class SwingSetRunnable(protected var swingset: SwingSet2, protected var obj: Any?) : Runnable {
        override fun run() {}

    }

    // *******************************************************
    // ********************   Actions  ***********************
    // *******************************************************
    inner class SwitchToDemoAction(var swingset: SwingSet2, var demo: DemoModule?) : AbstractAction(demo!!.name, demo.icon) {
        override fun actionPerformed(e: ActionEvent) {
            swingset.setDemo(demo)
        }

    }

    internal inner class OkAction(var aboutBox: JDialog) : AbstractAction("OkAction") {
        override fun actionPerformed(e: ActionEvent) {
            aboutBox.isVisible = false
        }

    }

    internal inner class ChangeLookAndFeelAction(var swingset: SwingSet2, var lafData: LookAndFeelData) : AbstractAction("ChangeTheme") {
        override fun actionPerformed(e: ActionEvent) {
            swingset.setLookAndFeel(lafData)
        }

    }

    internal inner class ActivatePopupMenuAction(var swingset: SwingSet2, var popup: JPopupMenu) : AbstractAction("ActivatePopupMenu") {
        override fun actionPerformed(e: ActionEvent) {
            val invokerSize = size
            val popupSize = popup.preferredSize
            popup.show(swingset, (invokerSize.width - popupSize.width) / 2,
                    (invokerSize.height - popupSize.height) / 2)
        }

    }

    // Turns on all possible auditory feedback
    internal inner class OnAudioAction(var swingset: SwingSet2) : AbstractAction("Audio On") {
        override fun actionPerformed(e: ActionEvent) {
            UIManager.put("AuditoryCues.playList",
                    UIManager.get("AuditoryCues.allAuditoryCues"))
            swingset.updateLookAndFeel()
        }

    }

    // Turns on the default amount of auditory feedback
    internal inner class DefaultAudioAction(var swingset: SwingSet2) : AbstractAction("Audio Default") {
        override fun actionPerformed(e: ActionEvent) {
            UIManager.put("AuditoryCues.playList",
                    UIManager.get("AuditoryCues.defaultCueList"))
            swingset.updateLookAndFeel()
        }

    }

    // Turns off all possible auditory feedback
    internal inner class OffAudioAction(var swingset: SwingSet2) : AbstractAction("Audio Off") {
        override fun actionPerformed(e: ActionEvent) {
            UIManager.put("AuditoryCues.playList",
                    UIManager.get("AuditoryCues.noAuditoryCues"))
            swingset.updateLookAndFeel()
        }

    }

    // Turns on or off the tool tips for the demo.
    internal inner class ToolTipAction : AbstractAction("ToolTip Control") {
        override fun actionPerformed(e: ActionEvent) {
            val status = (e.source as JCheckBoxMenuItem).isSelected
            ToolTipManager.sharedInstance().isEnabled = status
        }
    }

    internal inner class DragSupportAction : AbstractAction("DragSupport Control") {
        override fun actionPerformed(e: ActionEvent) {
            val dragEnabled = (e.source as JCheckBoxMenuItem).isSelected
            if (isApplet()) {
                //todo implement
                //setDragEnabled(dragEnabled)
            } else {
                for (ss in swingSets) {
                    //todo implement
                   // ss.setDragEnabled(dragEnabled)
                }
            }
        }
    }

    internal inner class ChangeThemeAction(var swingset: SwingSet2, var theme: MetalTheme?) : AbstractAction("ChangeTheme") {
        override fun actionPerformed(e: ActionEvent) {
            MetalLookAndFeel.setCurrentTheme(theme)
            swingset.updateLookAndFeel()
        }

    }

    internal inner class ExitAction(var swingset: SwingSet2) : AbstractAction("ExitAction") {
        override fun actionPerformed(e: ActionEvent) {
            System.exit(0)
        }

    }

    internal inner class AboutAction(var swingset: SwingSet2) : AbstractAction("AboutAction") {
        override fun actionPerformed(e: ActionEvent) {
            if (aboutBox == null) {
                // JPanel panel = new JPanel(new BorderLayout());
                val panel: JPanel = AboutPanel(swingset)
                panel.layout = BorderLayout()
                aboutBox = JDialog(swingset.frame, getString("AboutBox.title"), false)
                aboutBox!!.isResizable = false
                aboutBox!!.contentPane.add(panel, BorderLayout.CENTER)

                // JButton button = new JButton(getString("AboutBox.ok_button_text"));
                val buttonpanel = JPanel()
                buttonpanel.border = EmptyBorder(0, 0, 3, 0)
                buttonpanel.isOpaque = false
                val button = buttonpanel.add(
                        JButton(getString("AboutBox.ok_button_text"))
                ) as JButton
                panel.add(buttonpanel, BorderLayout.SOUTH)
                button.addActionListener(OkAction(aboutBox!!))
            }
            aboutBox!!.pack()
            if (isApplet()) {
                aboutBox!!.setLocationRelativeTo(getApplet())
            } else {
                aboutBox!!.setLocationRelativeTo(frame)
            }
            aboutBox!!.show()
        }

    }

    internal inner class MultiScreenAction(swingset: SwingSet2?, var screen: Int) : AbstractAction("MultiScreenAction") {
        override fun actionPerformed(e: ActionEvent) {
            val gds = GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices
            if (screen == ALL_SCREENS) {
                for (i in gds.indices) {
                    val swingset = SwingSet2(null,
                            gds[i].defaultConfiguration)
                    // todo implement
                    //swingset.setDragEnabled(dragEnabled)
                }
            } else {
                val swingset = SwingSet2(null,
                        gds[screen].defaultConfiguration)
                // todo implement
                //swingset.setDragEnabled(dragEnabled)
            }
        }



    }

    // *******************************************************
    // **********************  Misc  *************************
    // *******************************************************
    internal inner class DemoLoadThread(var swingset: SwingSet2) : Thread() {
        override fun run() {
            SwingUtilities.invokeLater { swingset.loadDemos() }
        }

    }

    internal inner class AboutPanel(swingset: SwingSet2) : JPanel() {
        var aboutimage: ImageIcon? = null
        var swingset: SwingSet2? = null
        override fun paint(g: Graphics) {
            aboutimage!!.paintIcon(this, g, 0, 0)
            super.paint(g)
        }

        override fun getPreferredSize(): Dimension {
            return Dimension(aboutimage!!.iconWidth,
                    aboutimage!!.iconHeight)
        }

        init {
            this.swingset = swingset
            aboutimage = createImageIcon("About.jpg", "AboutBox.accessible_description")
            isOpaque = false
        }
    }

    private inner class ChangeFontAction internal constructor(private val swingset: SwingSet2, private val plain: Boolean) : AbstractAction("FontMenu") {
        override fun actionPerformed(e: ActionEvent) {
            if (plain) {
                UIManager.put("swing.boldMetal", java.lang.Boolean.FALSE)
            } else {
                UIManager.put("swing.boldMetal", java.lang.Boolean.TRUE)
            }
            // Change the look and feel to force the settings to take effect.
            updateLookAndFeel()
        }

    }

    class LookAndFeelData(var name: String, var className: String, var label: String,
                          mnemonic: String, accDescription: String) {
        var mnemonic: Char
        var accDescription: String

        constructor(info: LookAndFeelInfo) : this(info.name, info.className, info.name,
                info.name, info.name) {
        }

        constructor(info: LookAndFeelInfo, property: String?) : this(info.name, info.className,
                getString(String.format("LafMenu.%s_label", property)),
                getString(String.format("LafMenu.%s_mnemonic", property)),
                getString(String.format("LafMenu.%s_accessible_description",
                        property))) {
        }

        override fun toString(): String {
            return className
        }

        init {
            this.mnemonic = mnemonic[0]
            this.accDescription = accDescription
        }
    }

    companion object {

        /**
         * Creates an icon from an image contained in the "images" directory.
         */


        // The preferred size of the demo
        private const val PREFERRED_WIDTH = 720
        private const val PREFERRED_HEIGHT = 640

        // number of swingsets - for multiscreen
        // keep track of the number of SwingSets created - we only want to exit
        // the program when the last one has been closed.
        private var numSSs = 0
        private var swingSets:Vector<SwingSet2> = Vector<SwingSet2>()

        /**
         * SwingSet2 Main. Called only if we're an application, not an applet.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            // must run in EDT when constructing the GUI components
            SwingUtilities.invokeLater {

                // Create SwingSet on the default monitor
                UIManager.put("swing.boldMetal", java.lang.Boolean.FALSE)
                val swingset = SwingSet2(null, GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.defaultConfiguration)
            }
        }

        /**
         * Create a frame for SwingSet2 to reside in if brought up
         * as an application.
         */
        fun createFrame(gc: GraphicsConfiguration?): JFrame {
            val frame = JFrame(gc)
            if (numSSs == 0) {
                frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            } else {
                val l: WindowListener = object : WindowAdapter() {
                    override fun windowClosing(e: WindowEvent) {
                        numSSs--
                        swingSets.remove(this as SwingSet2)
                    }
                }
                frame.addWindowListener(l)
            }
            return frame
        }

        /**
         * This method returns a string from the demo's resource bundle.
         */
        fun getString(key: String): String {
            var value: String? = null
            try {
                value = TextAndMnemonicUtils.getTextAndMnemonicString(key)
            } catch (e: MissingResourceException) {
                println("java.util.MissingResourceException: Couldn't find value for: $key")
            }
            if (value == null) {
                value = "Could not find resource: $key  "
            }
            return value
        }

        // *******************************************************
        // **************   ToggleButtonToolbar  *****************
        // *******************************************************
        var zeroInsets = Insets(1, 1, 1, 1)
        private val installedLookAndFeelData: Array<LookAndFeelData>
            private get() : Array<LookAndFeelData>  = Arrays.stream(UIManager.getInstalledLookAndFeels())
                    .map { laf: LookAndFeelInfo -> getLookAndFeelData(laf) }
                    .toArray<LookAndFeelData>({ length -> arrayOfNulls(length)})

        private fun getLookAndFeelData(
                info: LookAndFeelInfo): LookAndFeelData {
            return when (info.name) {
                "Metal" -> LookAndFeelData(info, "java")
                "Nimbus" -> LookAndFeelData(info, "nimbus")
                "Windows" -> LookAndFeelData(info, "windows")
                "GTK+" -> LookAndFeelData(info, "gtk")
                "CDE/Motif" -> LookAndFeelData(info, "motif")
                "Mac OS X" -> LookAndFeelData(info, "mac")
                else -> LookAndFeelData(info)
            }
        }
    }

    /**
     * SwingSet2 Constructor
     */
    init {

        // Note that applet may be null if this is started as an application
        val lafClassName = UIManager.getLookAndFeel().javaClass.name
        lookAndFeelData = installedLookAndFeelData
        currentLookAndFeel = Arrays.stream(lookAndFeelData)
                .filter { laf: LookAndFeelData -> lafClassName == laf.className }
                .findFirst().get()
        if (!isApplet()) {
            frame = createFrame(gc)
        }

        // set the layout
        layout = BorderLayout()

        // set the preferred size of the demo
        preferredSize = Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT)
        initializeDemo()
        preloadFirstDemo()
        showSwingSet2()

        // Start loading the rest of the demo in the background
        val demoLoader = DemoLoadThread(this)
        demoLoader.start()
    }
}