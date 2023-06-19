package com.example.cloneuber.data.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException

class ImageUtil{
    companion object {
       fun getBitmapFromUri(contentenResolver: ContentResolver, uri: Uri): Bitmap? {
            try {
                // Lee los datos de la Uri proporcionada utilizando el ContentResolver
                val inputStream = contentenResolver.openInputStream(uri)
                inputStream?.use {
                    // Decodifica el flujo de entrada en un objeto Bitmap
                    return BitmapFactory.decodeStream(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun openUri(context: Context, uriPhoto: Uri): String {
            val bitmap: Bitmap? = try {
                BitmapFactory.decodeStream(context.contentResolver.openInputStream(uriPhoto))
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
            return encodedImage(bitmap!!)
        }

        private fun encodedImage(bitmap: Bitmap): String {
            val previewWidth = 150
            val previewHeight = bitmap.height * previewWidth / bitmap.width
            val previewBitmap: Bitmap =
                Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
            val byteArrayOutputStream = ByteArrayOutputStream()
            previewBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        }
    }


}