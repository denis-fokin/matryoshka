package com.ogogon.matryoshka.demo.swingset2

import java.awt.*
import java.awt.geom.GeneralPath
import java.awt.image.BufferedImage
import javax.swing.JPanel

class BezierAnimationPanel : JPanel(), Runnable {
    var backgroundColor:Color = Color(0, 0, 153)
    set (c) {
        field = c
        this.background = c
        this.bgChanged = true
    }
    var outerColor = Color(255, 255, 255)

    var gradientColorA = Color(255, 0, 101)
    var gradientColorB = Color(255, 255, 0)
    var bgChanged = false
    var gradient: GradientPaint? = null
    val NUMPTS = 6
    var animpts = FloatArray(NUMPTS * 2)
    var deltas = FloatArray(NUMPTS * 2)
    var staticpts = floatArrayOf(
            50.0f, 0.0f,
            150.0f, 0.0f,
            200.0f, 75.0f,
            150.0f, 150.0f,
            50.0f, 150.0f,
            0.0f, 75.0f)
    var movepts = FloatArray(staticpts.size)
    var img: BufferedImage? = null
    var anim: Thread? = null
    private val lock = Any()
    override fun isOpaque(): Boolean {
        return true
    }

    fun start() {
        val size = size
        var i = 0
        while (i < animpts.size) {
            animpts[i + 0] = (Math.random() * size.width).toFloat()
            animpts[i + 1] = (Math.random() * size.height).toFloat()
            deltas[i + 0] = (Math.random() * 4.0 + 2.0).toFloat()
            deltas[i + 1] = (Math.random() * 4.0 + 2.0).toFloat()
            if (animpts[i + 0] > size.width / 6.0f) {
                deltas[i + 0] = -deltas[i + 0]
            }
            if (animpts[i + 1] > size.height / 6.0f) {
                deltas[i + 1] = -deltas[i + 1]
            }
            i += 2
        }
        anim = Thread(this)
        anim!!.priority = Thread.MIN_PRIORITY
        anim!!.start()
    }

    @Synchronized
    fun stop() {
        anim = null
        (this as Object).notify()
    }

    fun animate(pts: FloatArray, deltas: FloatArray, index: Int, limit: Int) {
        var newpt = pts[index] + deltas[index]
        if (newpt <= 0) {
            newpt = -newpt
            deltas[index] = (Math.random() * 3.0 + 2.0).toFloat()
        } else if (newpt >= limit.toFloat()) {
            newpt = 2.0f * limit - newpt
            deltas[index] = (-(Math.random() * 3.0 + 2.0)).toFloat()
        }
        pts[index] = newpt
    }

    override fun run() {
        val me = Thread.currentThread()
        while (size.width <= 0) {
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                return
            }
        }
        var g2d: Graphics2D? = null
        var BufferG2D: Graphics2D? = null
        var ScreenG2D: Graphics2D? = null
        val solid = BasicStroke(9.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 9.0f)
        val gp = GeneralPath(GeneralPath.WIND_NON_ZERO)
        val rule = AlphaComposite.SRC_OVER
        val opaque = AlphaComposite.SrcOver
        val blend = AlphaComposite.getInstance(rule, 0.9f)
        val set = AlphaComposite.Src
        var frame = 0
        val frametmp = 0
        var oldSize = size
        var clippath: Shape? = null
        while (anim === me) {
            val size = size
            if (size.width != oldSize.width || size.height != oldSize.height) {
                img = null
                clippath = null
                if (BufferG2D != null) {
                    BufferG2D.dispose()
                    BufferG2D = null
                }
                if (ScreenG2D != null) {
                    ScreenG2D.dispose()
                    ScreenG2D = null
                }
            }
            oldSize = size
            if (img == null) {
                img = createImage(size.width, size.height) as BufferedImage
            }
            if (BufferG2D == null) {
                BufferG2D = img!!.createGraphics()
                BufferG2D.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_DEFAULT)
                BufferG2D.clip = clippath
            }
            g2d = BufferG2D
            var ctrlpts: FloatArray
            run {
                var i = 0
                while (i < animpts.size) {
                    animate(animpts, deltas, i + 0, size.width)
                    animate(animpts, deltas, i + 1, size.height)
                    i += 2
                }
            }
            ctrlpts = animpts
            val len = ctrlpts.size
            gp.reset()
            val dir = 0
            var prevx = ctrlpts[len - 2]
            var prevy = ctrlpts[len - 1]
            var curx = ctrlpts[0]
            var cury = ctrlpts[1]
            var midx = (curx + prevx) / 2.0f
            var midy = (cury + prevy) / 2.0f
            gp.moveTo(midx, midy)
            var i = 2
            while (i <= ctrlpts.size) {
                val x1 = (midx + curx) / 2.0f
                val y1 = (midy + cury) / 2.0f
                prevx = curx
                prevy = cury
                if (i < ctrlpts.size) {
                    curx = ctrlpts[i + 0]
                    cury = ctrlpts[i + 1]
                } else {
                    curx = ctrlpts[0]
                    cury = ctrlpts[1]
                }
                midx = (curx + prevx) / 2.0f
                midy = (cury + prevy) / 2.0f
                val x2 = (prevx + midx) / 2.0f
                val y2 = (prevy + midy) / 2.0f
                gp.curveTo(x1, y1, x2, y2, midx, midy)
                i += 2
            }
            gp.closePath()
            synchronized(lock) {
                g2d!!.composite = set
                g2d.background = backgroundColor
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF)
                if (bgChanged || bounds == null) {
                    bounds = Rectangle(0, 0, width, height)
                    bgChanged = false
                }

                // g2d.clearRect(bounds.x-5, bounds.y-5, bounds.x + bounds.width + 5, bounds.y + bounds.height + 5);
                g2d.clearRect(0, 0, width, height)
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON)
                g2d.color = outerColor
                g2d.composite = opaque
                g2d.stroke = solid
                g2d.draw(gp)
                g2d.paint = gradient
                if (!bgChanged) {
                    bounds = gp.bounds
                } else {
                    bounds = Rectangle(0, 0, width, height)
                    bgChanged = false
                }
                gradient = GradientPaint(bounds!!.x.toFloat(), bounds!!.y.toFloat(), gradientColorA,
                        (bounds!!.x + bounds!!.width).toFloat(), (bounds!!.y + bounds!!.height).toFloat(),
                        gradientColorB, true)
                g2d.composite = blend
                g2d.fill(gp)
            }
            if (g2d === BufferG2D) {
                repaint()
            }
            ++frame
            Thread.yield()
        }
        g2d?.dispose()
    }

    override fun paint(g: Graphics) {
        synchronized(lock) {
            val g2d = g as Graphics2D
            if (img != null) {
                g2d.composite = AlphaComposite.Src
                g2d.drawImage(img, null, 0, 0)
            }
        }
    }

    /**
     * BezierAnimationPanel Constructor
     */
    init {
        addHierarchyListener {
            if (isShowing) {
                start()
            } else {
                stop()
            }
        }
        background = Companion.getBackgroundColor(this)
    }

    companion object {
        fun getBackgroundColor(bezierAnimationPanel: BezierAnimationPanel): Color {
            return bezierAnimationPanel.backgroundColor
        }
    }
}