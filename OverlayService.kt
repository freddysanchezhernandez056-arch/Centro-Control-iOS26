package com.freydroid.controlios26

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.FrameLayout

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var rootView: FrameLayout
    private lateinit var panelView: LiquidGlassView

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        rootView = FrameLayout(this)

        panelView = LiquidGlassView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                dpToPx(460)
            )
            translationY = -dpToPx(460).toFloat()
        }

        rootView.addView(panelView)

        rootView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                hidePanel()
                true
            } else false
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
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
            .withEndAction { stopSelf() }
            .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(rootView)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun dpToPx(dp: Int): Int =
        (dp * resources.displayMetrics.density).toInt()
}
