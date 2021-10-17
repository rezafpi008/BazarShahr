package com.bazar.bane.bazarshahr.api.main

import com.google.gson.annotations.SerializedName
import java.util.*

class ApiError {
    @SerializedName("error")
    var error: HashMap<String, ArrayList<String>>? = null

    fun getError(): String? {
        return if (error != null && error!!.size != 0) {
            var errorMessage = error!!.values.toTypedArray()[0].toString()
            errorMessage = errorMessage.replace("[", "")
            errorMessage = errorMessage.replace("]", "")
            errorMessage
        } else "null"
    }
}