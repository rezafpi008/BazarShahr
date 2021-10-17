package com.bazar.bane.bazarshahr.util.imagePicker

import android.app.Activity
import android.content.Intent
import android.net.Uri

class PickerBuilder(activity: Activity?, type: Int) {

    companion object {
        val SELECT_FROM_GALLERY = 0
        val SELECT_FROM_CAMERA = 1
    }

    private val permissionRefusedListener: onPermissionRefusedListener? = null
    protected var imageReceivedListener: onImageReceivedListener? = null
    private var activity: Activity? = null
    private var pickerManager: PickerManager? = null

    init {
        this.activity = activity
        pickerManager =
            if (type == SELECT_FROM_GALLERY) ImagePickerManager(activity) else CameraPickerManager(
                activity
            )
    }

    interface onPermissionRefusedListener {
        fun onPermissionRefused()
    }

    interface onImageReceivedListener {
        fun onImageReceived(imageUri: Uri?)
    }


    fun start() {
        val intent = Intent(activity, TempActivity::class.java)
        activity?.startActivity(intent)
        GlobalHolder.getInstance()?.setPickerManager(pickerManager)
    }

    fun setOnImageReceivedListener(listener: onImageReceivedListener?): PickerBuilder? {
        pickerManager?.setOnImageReceivedListener(listener)
        return this
    }

    fun setOnPermissionRefusedListener(listener: onPermissionRefusedListener?): PickerBuilder? {
        pickerManager?.setOnPermissionRefusedListener(listener)
        return this
    }

    fun setCropScreenColor(cropScreenColor: Int): PickerBuilder? {
        pickerManager?.setCropActivityColor(cropScreenColor)
        return this
    }

    fun setImageName(imageName: String?): PickerBuilder? {
        pickerManager?.setImageName(imageName)
        return this
    }

    fun withTimeStamp(withTimeStamp: Boolean): PickerBuilder? {
        pickerManager?.withTimeStamp(withTimeStamp)
        return this
    }

    fun setImageFolderName(folderName: String?): PickerBuilder? {
        pickerManager?.setImageFolderName(folderName)
        return this
    }

    fun setCustomizedUcrop(x: Int, y: Int): PickerBuilder? {
        pickerManager?.setCustomizedUcrop(x, y)
        return this
    }

}