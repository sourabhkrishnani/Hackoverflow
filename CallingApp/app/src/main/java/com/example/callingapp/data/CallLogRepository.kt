package com.example.moderncaller.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.CallLog
import com.example.moderncaller.viewmodel.CallLogEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CallLogRepository(private val contentResolver: ContentResolver) {

    @SuppressLint("Range")
    suspend fun getCallLogs(): List<CallLogEntry> = withContext(Dispatchers.IO) {
        val logs = mutableListOf<CallLogEntry>()
        val projection = arrayOf(
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION,
            CallLog.Calls.TYPE
        )

        val cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            projection,
            null,
            null,
            "${CallLog.Calls.DATE} DESC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex(CallLog.Calls.CACHED_NAME))
                val number = it.getString(it.getColumnIndex(CallLog.Calls.NUMBER))
                val date = it.getLong(it.getColumnIndex(CallLog.Calls.DATE))
                val duration = it.getLong(it.getColumnIndex(CallLog.Calls.DURATION))
                val type = it.getInt(it.getColumnIndex(CallLog.Calls.TYPE))

                logs.add(CallLogEntry(name, number, date, duration, type))
            }
        }
        return@withContext logs
    }
}
