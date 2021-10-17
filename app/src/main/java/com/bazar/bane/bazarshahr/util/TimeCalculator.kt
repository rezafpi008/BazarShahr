package com.bazar.bane.bazarshahr.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeCalculator {

    companion object {

        @SuppressLint("SimpleDateFormat")
        fun convertUtcToGmt(time: String?): String? {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            format.timeZone = TimeZone.getTimeZone("UTC")
            var date: Date? = null
            try {
                date = format.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format.timeZone = TimeZone.getDefault()
            return format.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        fun getCurrentTime(): String? {
            val simpleDateFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
            )
            return simpleDateFormat.format(Date())
        }

        @SuppressLint("SimpleDateFormat")
        fun strToDateLong(strDate: String?): Date? {
            if(strDate.isNullOrEmpty()) return null
            val formatter =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val pos = ParsePosition(0)
            return formatter.parse(strDate, pos)
        }

        private fun dateToLong(date: Date): Long {
            return date.time
        }

        fun stringToLong(strTime: String?): Long {
            val date: Date = strToDateLong(strTime)!!
            return if (date == null) {
                0
            } else {
                dateToLong(date)
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun subtractTime(time: String?): String {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val time = convertUtcToGmt(time)
            val calendar = Calendar.getInstance()
            try {
                calendar.time = format.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val today = Calendar.getInstance()
            val difference = today.timeInMillis - calendar.timeInMillis
            val sec = TimeUnit.SECONDS.convert(difference, TimeUnit.MILLISECONDS)
                .toInt()
            val minutes = TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS)
                .toInt()
            val hour = TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS)
                .toInt()
            val days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
                .toInt()
            return if (days > 0) {
                if (days > 365) {
                    return (days / 365).toString() + "years ago"
                } else if (days > 30) {
                    return (days / 30).toString() + "month ago"
                }
                days.toString() + "d ago"
            } else if (hour > 0) {
                hour.toString() + "h ago"
            } else if (minutes > 0) {
                minutes.toString() + "m ago"
            } else sec.toString() + "s ago"
        }

    }
}