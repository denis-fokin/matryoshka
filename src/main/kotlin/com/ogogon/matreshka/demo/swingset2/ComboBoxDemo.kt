package com.ogogon.matreshka.demo.swingset2

import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.accessibility.AccessibleRelation
import javax.swing.*
import javax.swing.border.BevelBorder

class ComboBoxDemo(swingset: SwingSet2?) : DemoModule(swingset, "ComboBoxDemo", "toolbar/JComboBox.gif"), ActionListener {
    var face: Face? = null
    var faceLabel: JLabel? = null
    var hairCB: JComboBox<*>? = null
    var eyesCB: JComboBox<*>? = null
    var mouthCB: JComboBox<*>? = null
    var presetCB: JComboBox<*>? = null
    var parts = Hashtable<Any?, Any?>()
    fun createComboBoxDemo() {
        val demo = demoPanel
        val demoPanel = demoPanel
        demoPanel!!.layout = BoxLayout(demoPanel, BoxLayout.Y_AXIS)
        val innerPanel = JPanel()
        innerPanel.layout = BoxLayout(innerPanel, BoxLayout.X_AXIS)
        demoPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP20))
        demoPanel.add(innerPanel)
        demoPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP20))
        innerPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP20))

        // Create a panel to hold buttons
        val comboBoxPanel: JPanel = object : JPanel() {
            override fun getMaximumSize(): Dimension {
                return Dimension(preferredSize.width, super.getMaximumSize().height)
            }
        }
        comboBoxPanel.layout = BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS)
        comboBoxPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        var l = comboBoxPanel.add(JLabel(getString("ComboBoxDemo.presets"))) as JLabel
        l.alignmentX = JLabel.LEFT_ALIGNMENT
        presetCB = comboBoxPanel.add(createPresetComboBox()) as JComboBox<*>
        presetCB!!.alignmentX = JComboBox.LEFT_ALIGNMENT
        l.labelFor = presetCB
        comboBoxPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP30))
        l = comboBoxPanel.add(JLabel(getString("ComboBoxDemo.hair_description"))) as JLabel
        l.alignmentX = JLabel.LEFT_ALIGNMENT
        hairCB = comboBoxPanel.add(createHairComboBox()) as JComboBox<*>
        hairCB!!.alignmentX = JComboBox.LEFT_ALIGNMENT
        l.labelFor = hairCB
        comboBoxPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        l = comboBoxPanel.add(JLabel(getString("ComboBoxDemo.eyes_description"))) as JLabel
        l.alignmentX = JLabel.LEFT_ALIGNMENT
        eyesCB = comboBoxPanel.add(createEyesComboBox()) as JComboBox<*>
        eyesCB!!.alignmentX = JComboBox.LEFT_ALIGNMENT
        l.labelFor = eyesCB
        comboBoxPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP15))
        l = comboBoxPanel.add(JLabel(getString("ComboBoxDemo.mouth_description"))) as JLabel
        l.alignmentX = JLabel.LEFT_ALIGNMENT
        mouthCB = comboBoxPanel.add(createMouthComboBox()) as JComboBox<*>
        mouthCB!!.alignmentX = JComboBox.LEFT_ALIGNMENT
        l.labelFor = mouthCB
        comboBoxPanel.add(Box.createRigidArea(DemoModule.Companion.VGAP15))

        // Fill up the remaining space
        comboBoxPanel.add(JPanel(BorderLayout()))

        // Create and place the Face.
        face = Face()
        val facePanel = JPanel()
        facePanel.layout = BorderLayout()
        facePanel.border = BevelBorder(BevelBorder.LOWERED)
        faceLabel = JLabel(face)
        facePanel.add(faceLabel, BorderLayout.CENTER)
        // Indicate that the face panel is controlled by the hair, eyes and
        // mouth combo boxes.
        val controlledByObjects = arrayOfNulls<Any>(3)
        controlledByObjects[0] = hairCB
        controlledByObjects[1] = eyesCB
        controlledByObjects[2] = mouthCB
        val controlledByRelation = AccessibleRelation(AccessibleRelation.CONTROLLED_BY_PROPERTY,
                controlledByObjects)
        facePanel.accessibleContext.accessibleRelationSet.add(controlledByRelation)

        // Indicate that the hair, eyes and mouth combo boxes are controllers
        // for the face panel.
        val controllerForRelation = AccessibleRelation(AccessibleRelation.CONTROLLER_FOR_PROPERTY,
                facePanel)
        hairCB!!.accessibleContext.accessibleRelationSet.add(controllerForRelation)
        eyesCB!!.accessibleContext.accessibleRelationSet.add(controllerForRelation)
        mouthCB!!.accessibleContext.accessibleRelationSet.add(controllerForRelation)

        // add buttons and image panels to inner panel
        innerPanel.add(comboBoxPanel)
        innerPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP30))
        innerPanel.add(facePanel)
        innerPanel.add(Box.createRigidArea(DemoModule.Companion.HGAP20))

        // load up the face parts
        addFace("brent", getString("ComboBoxDemo.brent"))
        addFace("georges", getString("ComboBoxDemo.georges"))
        addFace("hans", getString("ComboBoxDemo.hans"))
        addFace("howard", getString("ComboBoxDemo.howard"))
        addFace("james", getString("ComboBoxDemo.james"))
        addFace("jeff", getString("ComboBoxDemo.jeff"))
        addFace("jon", getString("ComboBoxDemo.jon"))
        addFace("lara", getString("ComboBoxDemo.lara"))
        addFace("larry", getString("ComboBoxDemo.larry"))
        addFace("lisa", getString("ComboBoxDemo.lisa"))
        addFace("michael", getString("ComboBoxDemo.michael"))
        addFace("philip", getString("ComboBoxDemo.philip"))
        addFace("scott", getString("ComboBoxDemo.scott"))

        // set the default face
        presetCB!!.selectedIndex = 0
    }

    fun addFace(name: String, i18n_name: String?) {
        var i: ImageIcon?
        val i18n_hair = getString("ComboBoxDemo.hair")
        val i18n_eyes = getString("ComboBoxDemo.eyes")
        val i18n_mouth = getString("ComboBoxDemo.mouth")
        parts[i18n_name] = name // i18n name lookup
        parts[name] = i18n_name // reverse name lookup
        i = createImageIcon("combobox/" + name + "hair.jpg", i18n_name + i18n_hair)
        parts[name + "hair"] = i
        i = createImageIcon("combobox/" + name + "eyes.jpg", i18n_name + i18n_eyes)
        parts[name + "eyes"] = i
        i = createImageIcon("combobox/" + name + "mouth.jpg", i18n_name + i18n_mouth)
        parts[name + "mouth"] = i
    }

    fun createHairComboBox(): JComboBox<*> {
        val cb = JComboBox<Any?>()
        fillComboBox(cb)
        cb.addActionListener(this)
        return cb
    }

    fun createEyesComboBox(): JComboBox<*> {
        val cb = JComboBox<Any?>()
        fillComboBox(cb)
        cb.addActionListener(this)
        return cb
    }

    fun createNoseComboBox(): JComboBox<*> {
        val cb = JComboBox<Any?>()
        fillComboBox(cb)
        cb.addActionListener(this)
        return cb
    }

    fun createMouthComboBox(): JComboBox<*> {
        val cb = JComboBox<Any?>()
        fillComboBox(cb)
        cb.addActionListener(this)
        return cb
    }

    fun createPresetComboBox(): JComboBox<*> {
        val cb = JComboBox<Any?>()
        cb.addItem(getString("ComboBoxDemo.preset1"))
        cb.addItem(getString("ComboBoxDemo.preset2"))
        cb.addItem(getString("ComboBoxDemo.preset3"))
        cb.addItem(getString("ComboBoxDemo.preset4"))
        cb.addItem(getString("ComboBoxDemo.preset5"))
        cb.addItem(getString("ComboBoxDemo.preset6"))
        cb.addItem(getString("ComboBoxDemo.preset7"))
        cb.addItem(getString("ComboBoxDemo.preset8"))
        cb.addItem(getString("ComboBoxDemo.preset9"))
        cb.addItem(getString("ComboBoxDemo.preset10"))
        cb.addActionListener(this)
        return cb
    }

    fun fillComboBox(cb: JComboBox<Any?>) {
        cb.addItem(getString("ComboBoxDemo.brent"))
        cb.addItem(getString("ComboBoxDemo.georges"))
        cb.addItem(getString("ComboBoxDemo.hans"))
        cb.addItem(getString("ComboBoxDemo.howard"))
        cb.addItem(getString("ComboBoxDemo.james"))
        cb.addItem(getString("ComboBoxDemo.jeff"))
        cb.addItem(getString("ComboBoxDemo.jon"))
        cb.addItem(getString("ComboBoxDemo.lara"))
        cb.addItem(getString("ComboBoxDemo.larry"))
        cb.addItem(getString("ComboBoxDemo.lisa"))
        cb.addItem(getString("ComboBoxDemo.michael"))
        cb.addItem(getString("ComboBoxDemo.philip"))
        cb.addItem(getString("ComboBoxDemo.scott"))
    }

    override fun actionPerformed(e: ActionEvent) {
        if (e.source === hairCB) {
            val name = parts[hairCB!!.selectedItem as String] as String?
            face!!.hair = parts[name + "hair"] as ImageIcon?
            faceLabel!!.repaint()
        } else if (e.source === eyesCB) {
            val name = parts[eyesCB!!.selectedItem as String] as String?
            face!!.eyes = parts[name + "eyes"] as ImageIcon?
            faceLabel!!.repaint()
        } else if (e.source === mouthCB) {
            val name = parts[mouthCB!!.selectedItem as String] as String?
            face!!.mouth = parts[name + "mouth"] as ImageIcon?
            faceLabel!!.repaint()
        } else if (e.source === presetCB) {
            var hair: String? = null
            var eyes: String? = null
            var mouth: String? = null
            when (presetCB!!.selectedIndex) {
                0 -> {
                    hair = parts["philip"] as String?
                    eyes = parts["howard"] as String?
                    mouth = parts["jeff"] as String?
                }
                1 -> {
                    hair = parts["jeff"] as String?
                    eyes = parts["larry"] as String?
                    mouth = parts["philip"] as String?
                }
                2 -> {
                    hair = parts["howard"] as String?
                    eyes = parts["scott"] as String?
                    mouth = parts["hans"] as String?
                }
                3 -> {
                    hair = parts["philip"] as String?
                    eyes = parts["jeff"] as String?
                    mouth = parts["hans"] as String?
                }
                4 -> {
                    hair = parts["brent"] as String?
                    eyes = parts["jon"] as String?
                    mouth = parts["scott"] as String?
                }
                5 -> {
                    hair = parts["lara"] as String?
                    eyes = parts["larry"] as String?
                    mouth = parts["lisa"] as String?
                }
                6 -> {
                    hair = parts["james"] as String?
                    eyes = parts["philip"] as String?
                    mouth = parts["michael"] as String?
                }
                7 -> {
                    hair = parts["philip"] as String?
                    eyes = parts["lisa"] as String?
                    mouth = parts["brent"] as String?
                }
                8 -> {
                    hair = parts["james"] as String?
                    eyes = parts["philip"] as String?
                    mouth = parts["jon"] as String?
                }
                9 -> {
                    hair = parts["lara"] as String?
                    eyes = parts["jon"] as String?
                    mouth = parts["scott"] as String?
                }
            }
            if (hair != null) {
                hairCB!!.selectedItem = hair
                eyesCB!!.selectedItem = eyes
                mouthCB!!.selectedItem = mouth
                faceLabel!!.repaint()
            }
        }
    }

    inner class Face : Icon {
        var hair: ImageIcon? = null
        var eyes: ImageIcon? = null
        var mouth: ImageIcon? = null


        override fun paintIcon(c: Component, g: Graphics, x: Int, y: Int) {
            var x = x
            var height = y
            x = c.width / 2 - iconWidth / 2
            if (hair != null) {
                hair!!.paintIcon(c, g, x, height)
                height += hair!!.iconHeight
            }
            if (eyes != null) {
                eyes!!.paintIcon(c, g, x, height)
                height += eyes!!.iconHeight
            }
            if (mouth != null) {
                mouth!!.paintIcon(c, g, x, height)
            }
        }

        override fun getIconWidth(): Int {
            return 344
        }

        override fun getIconHeight(): Int {
            return 455
        }
    }

    companion object {
        /**
         * main method allows us to run as a standalone demo.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val demo = ComboBoxDemo(null)
            demo.mainImpl()
        }
    }

    /**
     * ComboBoxDemo Constructor
     */
    init {
        // Set the title for this demo, and an icon used to represent this
        // demo inside the SwingSet2 app.
        createComboBoxDemo()
    }
}