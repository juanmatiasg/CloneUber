package com.example.cloneuber.data.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.cloneuber.R
import javax.inject.Inject


class GalleryPermission @Inject constructor(private val activity: Activity ,private val activityResultRegistry: ActivityResultRegistry) {

    private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private lateinit var galleryPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryResultLauncher: ActivityResultLauncher<Intent>


    init {
        createPermissionLauncher()
        createGalleryLauncher()
    }

    private fun createPermissionLauncher() {
        galleryPermissionLauncher = activityResultRegistry.register(
            "gallery_permission",
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                launchGallery()
            } else {
                showToast(activity.getString(R.string.showPermissionGalery))
            }
        }
    }


    private fun createGalleryLauncher() {
        galleryResultLauncher = activityResultRegistry.register(
            "gallery_result",
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri: Uri? = data?.data
                selectedImageUri?.let { uri ->
                    onImageSelectedListener?.onImageSelected(uri)
                }
            }
        }
    }

    interface OnImageSelectedListener {
        fun onImageSelected(uri: Uri)
    }

    private var onImageSelectedListener: OnImageSelectedListener? = null

    fun setOnImageSelectedListener(listener: OnImageSelectedListener) {
        onImageSelectedListener = listener
    }

    fun launchAddPhotoClicked() {
        if (arePermissionsGranted()) {
            launchGallery()
        } else {
            askPermissions()
        }
    }

    private fun askPermissions() {
        galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun arePermissionsGranted(): Boolean {
        return REQUIRED_PERMISSION.all { permission ->
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun launchGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryResultLauncher.launch(galleryIntent)
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}