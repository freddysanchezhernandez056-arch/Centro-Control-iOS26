package com.freydroid.controlios26

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class LiquidGlassView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val glassPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(120, 255, 255, 255) // cristal claro
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f
        color = Color.argb(80, 255, 255, 255)
    }

    private val blurPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        maskFilter = BlurMaskFilter(80f, BlurMaskFilter.Blur.NORMAL)
    }

    private val rect = RectF()
    private val radius = 48f

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        rect.set(0f, 0f, width.toFloat(), height.toFloat())

        // Blur base
        canvas.drawRoundRect(rect, radius, radius, blurPaint)

        // Cristal
        canvas.drawRoundRect(rect, radius, radius, glassPaint)

        // Borde brillante estilo iOS
        canvas.drawRoundRect(rect, radius, radius, strokePaint)
    }
}
