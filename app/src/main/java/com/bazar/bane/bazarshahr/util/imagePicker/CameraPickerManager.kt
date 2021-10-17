package com.bazar.bane.bazarshahr.util.imagePicker

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

class CameraPickerManager(activity: Activity?) : PickerManager(activity) {
    override fun sendToExternalApp() {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        mProcessingPhotoUri = activity!!.contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        captureIntent.addFlags(
            Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mProcessingPhotoUri)
        activity!!.startActivityForResult(captureIntent, REQUEST_CODE_SELECT_IMAGE)
    }

    override fun setUri(uri: Uri?) {
    }

}