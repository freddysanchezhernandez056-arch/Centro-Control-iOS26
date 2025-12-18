package com.freydroid.controlios26

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class LiquidGlassView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val blurPaint = Paint()

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        blurPaint.maskFilter = BlurMaskFilter(40f, BlurMaskFilter.Blur.NORMAL
