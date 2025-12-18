package com.freydroid.controlios26

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.view.MotionEvent
import android.view.accessibility.AccessibilityEvent

class GestureAccessibilityService : AccessibilityService() {

    private var startY = 0f
    private var startX = 0f

    override fun onServiceConnected() {
        serviceInfo = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_TOUCH_INTERACTION_START
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startY = event.y
                startX = event.x
            }

            MotionEvent.ACTION_UP -> {
                val deltaY = event.y - startY

                if (startY < 120 && deltaY > 180) {
                    startService(Intent(this, OverlayService::class.java))
                    return true
                }
            }
        }
        return super.onTouchEvent(event)
    }
}
