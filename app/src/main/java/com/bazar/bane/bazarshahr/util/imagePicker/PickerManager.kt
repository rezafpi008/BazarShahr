package com.bazar.bane.bazarshahr.util.imagePicker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bazar.bane.bazarshahr.R
import com.bazar.bane.bazarshahr.util.MainApplication.Companion.applicationContext
import com.yalantis.ucrop.UCrop
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

abstract class PickerManager(activity: Activity?) {
    companion object {
        const val REQUEST_CODE_SELECT_IMAGE = 200
        const val REQUEST_CODE_IMAGE_PERMISSION = 201
    }

    protected var mProcessingPhotoUri: Uri? = null
    private var withTimeStamp = true
    private var folder: String? = null
    private var imageName: String? = null
    protected var activity: Activity? = null
    private var uCrop: UCrop? = null
    private var xScale = 0
    private var yScale = 0
    var imageReceivedListener: PickerBuilder.onImageReceivedListener? = null
    var permissionRefusedListener: PickerBuilder.onPermissionRefusedListener? = null
    private var cropActivityColor: Int = R.color.colorPrimary

    fun setOnImageReceivedListener(listener: PickerBuilder.onImageReceivedListener?): PickerManager? {
        imageReceivedListener = listener
        return this
    }

    fun setOnPermissionRefusedListener(listener: PickerBuilder.onPermissionRefusedListener?): PickerManager? {
        permissionRefusedListener = listener
        return this
    }

    fun PickerManager(activity: Activity) {
        this.activity = activity
        imageName = activity.getString(R.string.app_name)
    }


    fun pickPhotoWithPermission() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_IMAGE_PERMISSION
            )
        } else sendToExternalApp()
    }

    fun handlePermissionResult(grantResults: IntArray) {
        if (grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {

            // permission was granted
            sendToExternalApp()
        } else {

            // permission denied
            if (permissionRefusedListener != null) permissionRefusedListener?.onPermissionRefused()
            activity?.finish()
        }
    }


    protected abstract fun sendToExternalApp()

    fun getImageFile(): Uri? {
        val finalPhotoName = (imageName +
                (if (withTimeStamp) "_" + SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(
                    Date(
                        System.currentTimeMillis()
                    )
                ) else "")
                + ".jpg")
        val photo = File(applicationContext().cacheDir, finalPhotoName)
        return Uri.fromFile(photo)
    }

    abstract fun setUri(uri: Uri?)

    fun startCropActivity() {
        if (uCrop == null) {
            uCrop = UCrop.of(mProcessingPhotoUri!!, getImageFile()!!)
            //uCrop = uCrop.useSourceImageAspectRatio();
            uCrop?.withAspectRatio(xScale.toFloat(), yScale.toFloat())
            val options: UCrop.Options = UCrop.Options()
            options.setHideBottomControls(true)
            options.setFreeStyleCropEnabled(false)
            uCrop = uCrop?.withOptions(options)
        }
        uCrop?.start(activity!!)
    }

    fun handleCropResult(data: Intent?) {
        val resultUri: Uri = UCrop.getOutput(data!!)!!
        if (imageReceivedListener != null) imageReceivedListener!!.onImageReceived(resultUri)
        activity!!.finish()
    }


    fun setActivity(activity: Activity?): PickerManager? {
        this.activity = activity
        return this
    }

    fun setImageName(imageName: String?): PickerManager? {
        this.imageName = imageName
        return this
    }

    fun setCropActivityColor(cropActivityColor: Int): PickerManager? {
        this.cropActivityColor = cropActivityColor
        return this
    }

    fun withTimeStamp(withTimeStamp: Boolean): PickerManager? {
        this.withTimeStamp = withTimeStamp
        return this
    }

    fun setImageFolderName(folder: String?): PickerManager? {
        this.folder = folder
        return this
    }

    fun setCustomizedUcrop(x: Int, y: Int): PickerManager? {
        xScale = x
        yScale = y
        return this
    }
}