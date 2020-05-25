package com.ogogon.matreshka.demo.swingset2

import java.io.File
import java.util.*
import javax.swing.Icon
import javax.swing.filechooser.FileView

class ExampleFileView : FileView() {
    private val icons = Hashtable<Any?, Any?>(5)
    private val fileDescriptions = Hashtable<Any?, Any?>(5)
    private val typeDescriptions = Hashtable<Any?, Any?>(5)

    /**
     * The name of the file.  Do nothing special here. Let
     * the system file view handle this.
     * @see FileView.getName
     */
    override fun getName(f: File): String {
        return ""
    }

    /**
     * Adds a human readable description of the file.
     */
    fun putDescription(f: File?, fileDescription: String?) {
        fileDescriptions[f] = fileDescription
    }

    /**
     * A human readable description of the file.
     *
     * @see FileView.getDescription
     */
    override fun getDescription(f: File): String {
        return fileDescriptions[f] as String
    }

    /**
     * Adds a human readable type description for files. Based on "dot"
     * extension strings, e.g: ".gif". Case is ignored.
     */
    fun putTypeDescription(extension: String?, typeDescription: String?) {
        typeDescriptions[extension] = typeDescription
    }

    /**
     * Adds a human readable type description for files of the type of
     * the passed in file. Based on "dot" extension strings, e.g: ".gif".
     * Case is ignored.
     */
    fun putTypeDescription(f: File, typeDescription: String?) {
        putTypeDescription(getExtension(f), typeDescription)
    }

    /**
     * A human readable description of the type of the file.
     *
     * @see FileView.getTypeDescription
     */
    override fun getTypeDescription(f: File): String {
        return typeDescriptions[getExtension(f)] as String
    }

    /**
     * Convenience method that returns the "dot" extension for the
     * given file.
     */
    fun getExtension(f: File): String? {
        val name = f.name
        if (name != null) {
            val extensionIndex = name.lastIndexOf('.')
            return if (extensionIndex < 0) {
                null
            } else name.substring(extensionIndex + 1).toLowerCase()
        }
        return null
    }

    /**
     * Adds an icon based on the file type "dot" extension
     * string, e.g: ".gif". Case is ignored.
     */
    fun putIcon(extension: String?, icon: Icon?) {
        icons[extension] = icon
    }

    /**
     * Icon that reperesents this file. Default implementation returns
     * null. You might want to override this to return something more
     * interesting.
     *
     * @see FileView.getIcon
     */
    override fun getIcon(f: File): Icon {
        var icon: Icon? = null
        val extension = getExtension(f)
        if (extension != null) {
            icon = icons[extension] as Icon?
        }
        return icon!!
    }

    /**
     * Whether the directory is traversable or not. Generic implementation
     * returns true for all directories and special folders.
     *
     * You might want to subtype ExampleFileView to do somethimg more interesting,
     * such as recognize compound documents directories; in such a case you might
     * return a special icon for the directory that makes it look like a regular
     * document, and return false for isTraversable to not allow users to
     * descend into the directory.
     *
     * @see FileView.isTraversable
     */
    override fun isTraversable(f: File): Boolean {
        // if (some_reason) {
        //    return Boolean.FALSE;
        // }
        return false // Use default from FileSystemView
    }
}