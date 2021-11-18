package com.bazar.bane.bazarshahr.api.request

import com.bazar.bane.bazarshahr.util.AppConstants.Companion.USER_ID
import com.bazar.bane.bazarshahr.util.SharedPreferenceUtil
import com.google.gson.annotations.SerializedName

class UserDetailsRequest {
    @SerializedName("data")
    var data: UserDetailsData? = null

    init {
        data = UserDetailsData(SharedPreferenceUtil.getStringValue(USER_ID)!!)
    }

    class UserDetailsData(id: String) {
        @SerializedName("id")
        var id: String? = null

        init {
            this.id = id
        }

    }

}