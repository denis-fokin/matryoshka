package com.ogogon.matreshka.demo.swingset2

import java.io.IOException
import java.util.*

object TextAndMnemonicUtils {
    // Label suffix for the text & mnemonic resource
    private const val LABEL_SUFFIX = ".labelAndMnemonic"

    // Resource bundle for internationalized and accessible text
    private var bundle: ResourceBundle? = null

    // Resource properties for the mnemonic key defenition
    private var properties: Properties? = null

    /**
     * Returns accessible and internationalized strings or mnemonics from the
     * resource bundle. The key is converted to the text & mnemonic key.
     *
     * The following patterns are checked:
     * Keys that have label suffixes:
     * (xxx_label -> xxx.labelAndMnemonic)
     *
     * Keys that have mnemonic suffixes:
     * (xxx_mnemonic -> xxx.labelAndMnemonic)
     *
     * Keys that do not have definite suffixes:
     * (xxx -> xxx.labelAndMnemonic)
     *
     * Properties class is used to check if a key created for mnemonic exists.
     */
    fun getTextAndMnemonicString(key: String): String? {
        if (key.endsWith("_label")) {
            val compositeKey = composeKey(key, 6, LABEL_SUFFIX)
            val textAndMnemonic = bundle!!.getString(compositeKey)
            return getTextFromTextAndMnemonic(textAndMnemonic)
        }
        if (key.endsWith("_mnemonic")) {
            val compositeKey = composeKey(key, 9, LABEL_SUFFIX)
            val value: Any? = properties!!.getProperty(compositeKey)
            if (value != null) {
                val textAndMnemonic = bundle!!.getString(compositeKey)
                return getMnemonicFromTextAndMnemonic(textAndMnemonic)
            }
        }
        val compositeKey = composeKey(key, 0, LABEL_SUFFIX)
        val value: Any? = properties!!.getProperty(compositeKey)
        if (value != null) {
            val textAndMnemonic = bundle!!.getString(compositeKey)
            return getTextFromTextAndMnemonic(textAndMnemonic)
        }
        val textAndMnemonic = bundle!!.getString(key)
        return getTextFromTextAndMnemonic(textAndMnemonic)
    }

    /**
     * Convert the text & mnemonic string to text string
     *
     * The '&' symbol is treated as the mnemonic pointer
     * The double "&&" symbols are treated as the single '&'
     *
     * For example the string "&Look && Feel" is converted to "Look & Feel"
     */
    fun getTextFromTextAndMnemonic(text: String): String {
        val sb = StringBuilder()
        var prevIndex = 0
        var nextIndex = text.indexOf('&')
        val len = text.length
        while (nextIndex != -1) {
            val s = text.substring(prevIndex, nextIndex)
            sb.append(s)
            nextIndex++
            if (nextIndex != len && text[nextIndex] == '&') {
                sb.append('&')
                nextIndex++
            }
            prevIndex = nextIndex
            nextIndex = text.indexOf('&', nextIndex + 1)
        }
        sb.append(text.substring(prevIndex, text.length))
        return sb.toString()
    }

    /**
     * Convert the text & mnemonic string to mnemonic
     *
     * The '&' symbol is treated the mnemonic pointer
     * The double "&&" symbols are treated as the single '&'
     *
     * For example the string "&Look && Feel" is converted to "L"
     */
    fun getMnemonicFromTextAndMnemonic(text: String): String? {
        val len = text.length
        var index = text.indexOf('&')
        while (0 <= index && index < text.length - 1) {
            index++
            index = if (text[index] == '&') {
                text.indexOf('&', index + 1)
            } else {
                val c = text[index]
                return Character.toUpperCase(c).toString()
            }
        }
        return null
    }

    /**
     * Removes the last n characters and adds the suffix
     */
    private fun composeKey(key: String, reduce: Int, sufix: String): String {
        return key.substring(0, key.length - reduce) + sufix
    }

    init {
        bundle = ResourceBundle.getBundle("resources.swingset")
        properties = Properties()
        try {
            properties?.load(TextAndMnemonicUtils::class.java.getResourceAsStream("resources/swingset.properties"))
        } catch (ex: IOException) {
            println("java.io.IOException: Couldn't load properties from: resources/swingset.properties")
        }
    }
}