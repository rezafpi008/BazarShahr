package com.bazar.bane.bazarshahr.util

import android.content.Context
import android.os.Environment
import com.bazar.bane.bazarshahr.util.MainApplication.Companion.applicationContext

class SharedPreferenceUtil {
    companion object {
        fun saveBooleanValue(context: Context, key: String?, value: Boolean) {
            val settings = context.getSharedPreferences(
                AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
            )
            val editor = settings.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun getBooleanValue(context: Context, key: String?): Boolean {
            val settings = context.getSharedPreferences(
                AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
            )
            return settings.getBoolean(key, false)
        }

        fun saveIntValue(context: Context, key: String?, value: Int) {
            val settings = context.getSharedPreferences(
                AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
            )
            val editor = settings.edit()
            editor.putInt(key, value)
            editor.apply()
        }


        fun getIntValue(context: Context, key: String?): Int {
            val settings = context.getSharedPreferences(
                AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
            )
            return settings.getInt(key, -1)
        }

        fun saveStringValue(key: String?, value: String?) {
            val settings = applicationContext().getSharedPreferences(
                AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
            )
            val editor = settings.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun getStringValue(key: String?): String? {
            val settings = applicationContext().getSharedPreferences(
                AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
            )
            return settings.getString(key, "")
        }

        fun saveLongValue(context: Context, key: String?, value: Long) {
            val settings = context.getSharedPreferences(
                AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
            )
            val editor = settings.edit()
            editor.putLong(key, value)
            editor.apply()
        }

        fun getLongValue(context: Context, key: String?): Long {
            val settings = context.getSharedPreferences(
                AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
            )
            return settings.getLong(key, 0L)
        }

        fun cleanStringValue(context: Context, vararg keys: String?) {
            for (key in keys) {
                val settings = context.getSharedPreferences(
                    AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
                )
                val editor = settings.edit()
                if (settings.contains(key)) {
                    editor.remove(key).apply()
                }
            }
        }

        fun cleanAll(context: Context) {
            val settings = context.getSharedPreferences(
                AppConstants.SHARE_FILE_NAME, Context.MODE_PRIVATE
            )
            val editor = settings.edit()
            editor.clear().apply()
        }

        fun isCanUseSdCard(): Boolean {
            try {
                return Environment.getExternalStorageState() ==
                        Environment.MEDIA_MOUNTED
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

    }
}