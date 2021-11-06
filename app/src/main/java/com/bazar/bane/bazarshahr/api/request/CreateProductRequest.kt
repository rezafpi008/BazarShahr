package com.bazar.bane.bazarshahr.api.request

import android.graphics.Bitmap
import com.bazar.bane.bazarshahr.util.MainApplication.Companion.applicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*


class CreateProductRequest(
    title: String,
    details: String,
    adImg: Bitmap
) {
    var title: RequestBody? = null
    var details: RequestBody? = null
    var adImg: MultipartBody.Part? = null

    init {
        this.title = title.trim().toRequestBody("text/plain".toMediaTypeOrNull())
        this.details = details.trim().toRequestBody("text/plain".toMediaTypeOrNull())
        this.adImg = buildImageBodyPart("img", adImg)
    }

    private fun buildImageBodyPart(fileName: String, bitmap: Bitmap): MultipartBody.Part {
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
    }

}

/*
fun getGallery(files:ArrayList<String>){
    for (i in 0 until files.size) {
        val file = File(files[i])
        val leftImageFile = convertBitmapToFile(fileName, bitmap)
        val reqFile = leftImageFile.asRequestBody("image/*".toMediaTypeOrNull())
        builder.addFormDataPart(
            "file[]",
            file.name,
            create(MediaType.parse("multipart/form-data"), file)
        )
    }
}*/