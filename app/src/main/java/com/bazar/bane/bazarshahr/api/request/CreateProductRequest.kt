package com.bazar.bane.bazarshahr.api.request

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.MainApplication.Companion.applicationContext
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*


class CreateProductRequest(
    title: String,
    description: String?,
    jobId: String,
    images: ArrayList<Any?>?
) {
    @SerializedName("data")
    var data: CreateJObData? = null

    init {
        data = CreateJObData(
            title,
            description,
            jobId,
            images
        )
    }

    class CreateJObData(
        title: String,
        description: String?,
        jobId: String,
        images: ArrayList<Any?>?
    ) {
        @SerializedName("params")
        var createJObParam: CreateJObParam? = null

        init {
            this.createJObParam = CreateJObParam(
                title,
                description,
                jobId,
                images
            )
        }

    }

    class CreateJObParam(
        title: String,
        description: String?,
        jobId: String,
        images: ArrayList<Any?>?
    ) {
        @SerializedName("title")
        var title: String? = null

        @SerializedName("description")
        var description: String? = null

        @SerializedName("job_id")
        var jobId: String? = null

        @SerializedName("images")
        var images: ArrayList<ImageData>? = null


        init {
            this.title = title
            this.description = description
            this.jobId = jobId
            if (images != null)
                this.images = convertToImageArrayData(images)
        }

        private fun convertToImageArrayData(images: ArrayList<Any?>): ArrayList<ImageData> {
            val imagesString: ArrayList<ImageData> = ArrayList()
            for (item in images) {
                imagesString.add(ImageData(bitmapToBase64(item!!)))
            }
            return imagesString
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