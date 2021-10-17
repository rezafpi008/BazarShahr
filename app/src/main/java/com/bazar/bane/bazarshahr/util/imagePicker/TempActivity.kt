package com.bazar.bane.bazarshahr.util.imagePicker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yalantis.ucrop.UCrop.REQUEST_CROP

class TempActivity : AppCompatActivity() {
    var pickerManager: PickerManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickerManager = GlobalHolder.getInstance()?.getPickerManager()
        pickerManager?.setActivity(this@TempActivity)
        pickerManager?.pickPhotoWithPermission()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            finish()
            return
        }
        when (requestCode) {
            PickerManager.REQUEST_CODE_SELECT_IMAGE -> {
                val uri: Uri?
                uri = if (data != null) data.data else pickerManager?.getImageFile()
                pickerManager?.setUri(uri)
                pickerManager?.startCropActivity()
            }
            REQUEST_CROP -> if (data != null) {
                pickerManager?.handleCropResult(data)
            } else finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PickerManager.REQUEST_CODE_IMAGE_PERMISSION) pickerManager?.handlePermissionResult(
            grantResults
        ) else finish()
    }

}
