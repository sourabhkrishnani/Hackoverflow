package com.example.scheduleassistant.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Reminder"
        val content = intent.getStringExtra("content") ?: "You have a scheduled item."
        val notificationId = intent.getIntExtra("notificationId", 0)
        NotificationHelper.showNotification(context, title, content, notificationId)
    }
}
