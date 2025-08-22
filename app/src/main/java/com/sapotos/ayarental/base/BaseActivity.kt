package com.sapotos.ayarental.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

abstract class BaseActivity : AppCompatActivity() {

    var parentContainer: View? = null
    abstract fun getLayoutResourceId(): View

    private var resultLauncher: ActivityResultLauncher<android.content.Intent>? = null

    // Optional: use this from child screens when you toggle Push switch
    private val requestNotifPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { /* handle result if needed */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1) Apply DARK/LIGHT mode **before** inflating views
        val prefs = PreferenceHelper.defaultPrefs(this)
        val dark = prefs.getBoolean(KEY_DARK, false)
        AppCompatDelegate.setDefaultNightMode(
            if (dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)

        // 2) Inflate child layout
        parentContainer = getLayoutResourceId()

        // 3) Keep your existing launcher (if you’re using it elsewhere)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ -> }
    }

    /** Call from child when enabling push so Android 13+ asks only once. */
    protected fun ensureNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) {
                requestNotifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    companion object {
        const val KEY_DARK = "settings_dark_mode"
    }
}
