package com.freydroid.controlios26

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.FrameLayout

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var rootView: FrameLayout
    private lateinit var panelView: LiquidGlassView

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Vista raíz (fondo transparente)
        rootView = FrameLayout(this)

        // Panel estilo iOS (Liquid Glass)
        panelView = LiquidGlassView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                dpToPx(460)
            )
            translationY = -dpToPx(460).toFloat()
        }

        // Agregar panel al root
        rootView.addView(panelView)

        // Cerrar SOLO cuando se toca fuera del panel
        rootView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                hidePanel()
                true
            } else {
                false
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT   // ✅ CORRECTO
        )

        params.gravity = Gravity.TOP

        windowManager.addView(rootView, params)
        showPanel()
    }

    private fun showPanel() {
        panelView.animate()
            .translationY(0f)
            .setDuration(320)
            .setInterpolator(android.view.animation.OvershootInterpolator(0.9f))
            .start()
    }

    private fun hidePanel() {
        panelView.animate()
            .translationY(-dpToPx(460).toFloat())
            .setDuration(260)
            .setInterpolator(android.view.animation.AccelerateInterpolator())
            .withEndAction {
                stopSelf()
            }
            .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::rootView.isInitialized) {
            windowManager.removeView(rootView)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
