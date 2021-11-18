package com.bazar.bane.bazarshahr.api.request

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import com.bazar.bane.bazarshahr.util.AppConstants
import com.bazar.bane.bazarshahr.util.MainApplication.Companion.applicationContext
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.google.gson.annotations.SerializedName
import java.io.*


class CreateJobRequest(
    title: String,
    cityId: String,
    address: String,
    phoneNumber: String,
    description: String?,
    categoryId: String,
    mallId: String?,
    images: ArrayList<Any?>?
) {
    @SerializedName("data")
    var data: CreateJObData? = null

    init {
        data = CreateJObData(
            title,
            cityId,
            address,
            phoneNumber,
            description,
            categoryId,
            mallId,
            images
        )
    }

    class CreateJObData(
        title: String,
        cityId: String,
        address: String,
        phoneNumber: String,
        description: String?,
        categoryId: String,
        mallId: String?,
        images: ArrayList<Any?>?
    ) {
        @SerializedName("params")
        var createJObParam: CreateJObParam? = null

        init {
            this.createJObParam = CreateJObParam(
                title,
                cityId,
                address,
                phoneNumber,
                description,
                categoryId,
                mallId,
                images
            )
        }

    }

    class CreateJObParam(
        title: String,
        cityId: String,
        address: String,
        phoneNumber: String,
        description: String?,
        categoryId: String,
        mallId: String?,
        images: ArrayList<Any?>?
    ) {
        @SerializedName("title")
        var title: String? = null

        @SerializedName("city")
        var cityId: String? = null

        @SerializedName("address")
        var address: String? = null

        @SerializedName("phone_number")
        var phoneNumber: String? = null

        @SerializedName("description")
        var description: String? = null

        @SerializedName("user_id")
        var userId: String = SharedPreferenceUtil.getStringValue(AppConstants.USER_ID)!!

        @SerializedName("category")
        var categoryId: String? = null

        @SerializedName("mall")
        var mallId: String? = null

        @SerializedName("images")
        var images: ArrayList<ImageData>? = null


        init {
            this.title = title
            this.cityId = cityId
            this.address = address
            this.phoneNumber = phoneNumber
            this.description = description
            this.categoryId = categoryId
            this.mallId = mallId
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
        var format: String = "png"

        init {
            this.image = image
        }
    }


}

/*private fun buildImageBodyPart(fileName: String, bitmap: Bitmap): MultipartBody.Part {
        val leftImageFile = convertBitmapToFile(fileName, bitmap)
        val reqFile = leftImageFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(fileName, leftImageFile.name, reqFile)
    }
    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
        val file = File(applicationContext().cacheDir, fileName)
        file.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 , bos)
        val bitMapData = bos.toByteArray()
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }*/