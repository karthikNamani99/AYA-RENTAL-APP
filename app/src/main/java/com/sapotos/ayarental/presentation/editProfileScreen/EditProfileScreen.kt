package com.sapotos.ayarental.presentation.editProfileScreen

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sapotos.ayarental.R
import com.sapotos.ayarental.base.BaseActivity
import com.sapotos.ayarental.databinding.EditProfileScreenBinding

class EditProfileScreen : BaseActivity() {

    private lateinit var binding: EditProfileScreenBinding
    private lateinit var vm: EditProfileScreen_View_Model
    private var tempCameraUri: Uri? = null

    // ---- Gallery (no runtime permission needed with system picker) ----
    private val pickImage = registerForActivityResult(GetContent()) { uri ->
        uri?.let { vm.setPhotoUri(it) }
    }

    // ---- Camera ----
    private val takePicture = registerForActivityResult(TakePicture()) { success ->
        if (success && tempCameraUri != null) vm.setPhotoUri(tempCameraUri!!)
    }

    private val requestCameraPermission = registerForActivityResult(RequestPermission()) { granted ->
        if (granted) {
            tempCameraUri = createImageUri()
            tempCameraUri?.let { takePicture.launch(it) }
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getLayoutResourceId(): View {
        binding = DataBindingUtil.setContentView(this, R.layout.edit_profile_screen)
        vm = ViewModelProvider(this)[EditProfileScreen_View_Model::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = vm

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.btnChangePhoto.setOnClickListener { showChangePhotoSheet() }
        binding.btnUpdate.setOnClickListener {
            // TODO: call your API with vm fields
        }
        return binding.root
    }

    private fun showChangePhotoSheet() {
        val dialog = BottomSheetDialog(this, com.google.android.material.R.style.ThemeOverlay_Material3_BottomSheetDialog)
        val sheet = layoutInflater.inflate(R.layout.bottom_sheet_change_photo, null)
        dialog.setContentView(sheet)

        val rowCamera  = sheet.findViewById<View>(R.id.rowCamera)
        val rowGallery = sheet.findViewById<View>(R.id.rowGallery)
        val rowRemove  = sheet.findViewById<View>(R.id.rowRemove)

        // Hide camera option on devices without a camera
        val hasCamera = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
        rowCamera.visibility = if (hasCamera) View.VISIBLE else View.GONE

        rowCamera.setOnClickListener {
            dialog.dismiss()
            // Ask for CAMERA and only open after callback grants it
            when (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                PackageManager.PERMISSION_GRANTED -> {
                    tempCameraUri = createImageUri()
                    tempCameraUri?.let { takePicture.launch(it) }
                }
                else -> requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
        }

        rowGallery.setOnClickListener {
            dialog.dismiss()
            // No READ permission needed with GetContent()
            pickImage.launch("image/*")
        }

        rowRemove.setOnClickListener {
            dialog.dismiss()
            vm.clearPhoto()
        }

        dialog.show()
    }

    /** Pre-create a MediaStore entry to receive the camera photo */
    private fun createImageUri(): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DISPLAY_NAME, "profile_${System.currentTimeMillis()}.jpg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }
}
