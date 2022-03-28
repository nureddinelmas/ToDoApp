package com.nureddinelmas.todoapp

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.nureddinelmas.todoapp.activities.HomeActivity
import com.nureddinelmas.todoapp.model.ToDo
import com.nureddinelmas.todoapp.roomData.ToDoDao
import com.nureddinelmas.todoapp.roomData.ToDoDatabase
import android.os.Build


class NotifierAlarm : BroadcastReceiver(){
    private var appDatabase : ToDoDatabase? = null
    override fun onReceive(context: Context, intent: Intent?) {
        appDatabase = ToDoDatabase.getDatabase(context.applicationContext)
        val roomDao: ToDoDao = appDatabase!!.toDoDao()
        val todo = intent?.getParcelableExtra<ToDo>("item")
        appDatabase!!.destroyInstance()

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)


        val intent1 = Intent(context, HomeActivity::class.java)

        intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP


        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(HomeActivity::class.java)
        taskStackBuilder.addNextIntent(intent1)

        val intent2 = todo?.let { taskStackBuilder.getPendingIntent(it.id,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE) }
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context)

        var channel: NotificationChannel? = null
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH)

        }

        val notification: Notification = builder.setContentTitle(todo?.title)
            .setContentText(todo?.description)
            .setSound(alarmSound).setSmallIcon(R.drawable.ic_access_alarm)
            .setContentIntent(intent2)
            .setChannelId("my_channel_01")
            .build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel!!)
        }
        notificationManager.notify(1, notification)




    }
}