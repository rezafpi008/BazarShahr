package com.bazar.bane.bazarshahr.api.request

import android.graphics.Bitmap
import android.util.Base64
import com.bazar.bane.bazarshahr.util.MainApplication.Companion.applicationContext
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*


class CreateJobRequest(
    adTypeId: Int,
    title: String,
    socialLink: String,
    publishDate: String,
    expirationDate: String, totalBudget: String, adImg: Bitmap
) {
    var adTypeId: RequestBody? = null
    var title: RequestBody? = null
    var socialLink: RequestBody? = null
    var publishDate: RequestBody? = null
    var expirationDate: RequestBody? = null
    var totalBudget: RequestBody? = null
    var adImg: MultipartBody.Part? = null

    init {
        this.adTypeId = adTypeId.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        this.title = title.trim().toRequestBody("text/plain".toMediaTypeOrNull())
        this.socialLink = socialLink.trim().toRequestBody("text/plain".toMediaTypeOrNull())
        this.publishDate = publishDate.trim().toRequestBody("text/plain".toMediaTypeOrNull())
        this.expirationDate = expirationDate.trim().toRequestBody("text/plain".toMediaTypeOrNull())
        this.totalBudget = totalBudget.trim().toRequestBody("text/plain".toMediaTypeOrNull())
        //this.adImg = buildImageBodyPart("img", adImg)
    }


    private fun convertToImageArrayData(images:ArrayList<Bitmap>):ArrayList<ImageData> {
        val imagesString:ArrayList<ImageData> = ArrayList()
        for (item in images){
            imagesString.add(ImageData(bitmapToBase64(item)))
        }
        return imagesString
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    class ImageData(image: String) {
        @SerializedName("data")
        var image: String? = null

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