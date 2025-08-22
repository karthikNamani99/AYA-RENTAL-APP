package com.sapotos.ayarental.presentation.appSettingsScreen

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.AppSettingsScreenBinding

class AppSettingsScreen : BaseActivity() {

    private lateinit var binding: AppSettingsScreenBinding
    private lateinit var vm: AppSettingsScreen_View_Model

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.app_settings_screen)
        vm = ViewModelProvider(this)[AppSettingsScreen_View_Model::class.java]

        binding.lifecycleOwner = this
        binding.vm = vm

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Push permission flow when user enables the switch
        binding.swPush.setOnCheckedChangeListener { _, checked ->
            if (checked) ensurePushReady()
        }

        // Check for updates immediately when toggle is turned on
        binding.swUpdate.setOnCheckedChangeListener { _, checked ->
            if (checked) checkForAppUpdate()
        }

        return binding.root
    }

    // ---------- Push notifications ----------
    private fun ensurePushReady() {
        createDefaultChannel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!granted) {
                requestNotifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        // If you use FCM topic/device registration, start it here.
    }

    private val requestNotifPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                // User denied; flip the switch back off
                binding.swPush.isChecked = false
            }
        }

    private fun createDefaultChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanId = "default_aya"
            val channel = NotificationChannel(
                chanId, "General", NotificationManager.IMPORTANCE_DEFAULT
            )
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    // ---------- In-app update (Play Core – basic flexible flow) ----------
    private fun checkForAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    info, AppUpdateType.FLEXIBLE, this, 1001
                )
            }
        }
    }
}
