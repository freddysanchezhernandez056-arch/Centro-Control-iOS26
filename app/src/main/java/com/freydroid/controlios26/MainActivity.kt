package com.freydroid.controlios26

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pedir permiso para mostrar sobre otras apps (Overlay)
        if (!Settings.canDrawOverlays(this)) {!
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
            finish()
            return
        }

        // Iniciar el servicio del Centro de Control
        startService(Intent(this, OverlayService::class.java))
        finish()
    }
}
