package com.example.gwq.clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.g.android.RING") {
            val intent2 = Intent(context, MainActivity::class.java)
            val noteId = intent.getIntExtra("noteId", 1)
            //NoteBody noteBody = NotesLab.get(context).queryNote(noteId);
            //发送通知
            val pi = PendingIntent.getActivity(context, 0, intent2, 0)
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(context)
                    .setContentTitle("Title")//noteBody.getTime())
                    .setContentText("body")//noteBody.getText())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setAutoCancel(true)
                    .setContentIntent(pi)
            val notification = builder.build()
            manager.notify(1, notification)

            //发送一条清空闹铃图标的广播
            //NotesLab.get(context).updateFlag(noteId,0);
            val intent1 = Intent("com.g.android.NoColor")
            intent1.putExtra("noteId", noteId)
            context.sendBroadcast(intent1)
        }
    }
}