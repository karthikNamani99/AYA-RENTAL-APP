package com.sapotos.ayarental.presentation.appSettingsScreen

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AppSettingsScreen_View_Model(app: Application) : AndroidViewModel(app) {

    private val prefs = PreferenceHelper.defaultPrefs(app)

    val pushEnabled = MutableLiveData(prefs.getBoolean(KEY_PUSH, false))
    val autoUpdate  = MutableLiveData(prefs.getBoolean(KEY_AUTO_UPDATE, false))
    val darkMode    = MutableLiveData(prefs.getBoolean(KEY_DARK, false))

    fun onPushToggled(checked: Boolean) {
        pushEnabled.value = checked
        prefs.edit().putBoolean(KEY_PUSH, checked).apply()
        // No-op here. Activity will actually request permission / register channel.
    }

    fun onAutoUpdateToggled(checked: Boolean) {
        autoUpdate.value = checked
        prefs.edit().putBoolean(KEY_AUTO_UPDATE, checked).apply()
        // Activity will trigger a one-time update check when turned on.
    }

    fun onDarkModeToggled(checked: Boolean) {
        darkMode.value = checked
        prefs.edit().putBoolean(KEY_DARK, checked).apply()
        AppCompatDelegate.setDefaultNightMode(
            if (checked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    companion object {
        private const val KEY_PUSH = "settings_push_enabled"
        private const val KEY_AUTO_UPDATE = "settings_auto_update"
        private const val KEY_DARK = "settings_dark_mode"
    }
}
