package com.bazar.bane.bazarshahr.util

import com.google.gson.Gson
import com.bazar.bane.bazarshahr.api.main.ApiError

class RetrofitErrorUtils {
    companion object{
        fun parseError(response: String): String? {
            val gson = Gson()
            var apiError = ApiError()
            try {
                apiError =
                    gson.fromJson(response, ApiError::class.java)
            }catch (e:Exception){
                var hashMap= hashMapOf<String , ArrayList<String>>()
                apiError.error = hashMap
            }

            return apiError.getError()
        }

    }
}