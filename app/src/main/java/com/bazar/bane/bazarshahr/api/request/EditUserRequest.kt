package com.bazar.bane.bazarshahr.api.request

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.bazar.bane.bazarshahr.util.MainApplication.Companion.applicationContext
import com.google.gson.annotations.SerializedName
import java.io.*


class EditUserRequest(
    name: String,
    phoneNumber: String,
    image: String?
) {
    @SerializedName("data")
    var data: CreateJObData? = null

    init {
        data = CreateJObData(
            name,
            phoneNumber,
            image
        )
    }

    class CreateJObData(
        name: String,
        phoneNumber: String,
        image: String?
    ) {
        @SerializedName("name")
        var name: String? = null

        @SerializedName("phone_number")
        var phoneNumber: String? = null

        @SerializedName("image")
        var image: ImageData? = null


        init {
            this.name = name
            this.phoneNumber = phoneNumber
            if (image != null)
                this.image = ImageData(bitmapToBase64(image))
        }

        private fun bitmapToBase64(item: Any): String {
            val imageUri = item as String
            val bitmap = BitmapFactory.decodeStream(
                applicationContext().contentResolver.openInputStream(
                    Uri.parse(imageUri)
                )
            )
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)
        }

    }

    class ImageData(image: String) {
        @SerializedName("data")
        var image: String? = null

        @SerializedName("format")
        var format:String="png"

        init {
            this.image = image
        }
    }


}