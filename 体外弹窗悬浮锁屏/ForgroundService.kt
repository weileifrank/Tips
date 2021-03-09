package com.xysl.watermelonclean.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.AppUtils
import com.xysl.common.base.utils.LogUtil
import com.xysl.watermelonclean.R
import com.xysl.watermelonclean.activity.OutDialogActivity
import com.xysl.watermelonclean.activity.SplashActivity
import com.xysl.watermelonclean.utils.RxjavaUtil
import io.reactivex.disposables.Disposable


class ForgroundService : Service() {
    companion object {
        const val TAG = "ForgroundService"
    }

    var disposable: Disposable? = null
    override fun onBind(intent: Intent?): IBinder? {
        LogUtil.d("onBind", TAG)
        return null
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.d("onCreate", TAG)
        disposable = RxjavaUtil.intervalTimeMill(3).subscribe {
            LogUtil.d("isForeground=${AppUtils.isAppForeground()}", TAG)
            if (!AppUtils.isAppForeground()) {
                LogUtil.d("start", TAG)
                val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val runningTasks = activityManager.getRunningTasks(100)
                for (taskInfo in runningTasks) {
                    if (taskInfo.topActivity!!.packageName ==packageName) {
                        activityManager.moveTaskToFront(taskInfo.id, 0)
                        break
                    }
                }
                val intent = Intent(this, OutDialogActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
//                val fullScreenIntent = Intent(this, SplashActivity::class.java)
//                val fullScreenPendingIntent = PendingIntent.getActivity(
//                    this, 0,
//                    fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT
//                )
//                val notificationChannelId = "FOREGROUND SERVICE CHANNEL"
//                val builder =
//                    NotificationCompat.Builder(this, notificationChannelId)
//                        .setSmallIcon(R .mipmap.app_icon)
//                        .setContentTitle("Incoming call")
//                        .setContentText("(919) 555-1234") //以下为关键的3行
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setCategory(NotificationCompat.CATEGORY_CALL)
//                        .setFullScreenIntent(fullScreenPendingIntent, true)
//                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(101, builder.build())

//                NotificationUtils.sendNotificationFullScreen(this,"title","content")
            }
        }
//        val notification = createNotification()
//        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.d("onStartCommand", TAG)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("onDestroy", TAG)
        disposable?.apply {
            if (!isDisposed) {
                dispose()
            }
        }
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "FOREGROUND SERVICE CHANNEL"
        val notificationChannelName = "FOREGROUND SERVICE CHANNEL NAME"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
            val channel = NotificationChannel(
                notificationChannelId,
                notificationChannelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent =
            Intent(this, SplashActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        return NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.mipmap.app_icon)
            .setContentTitle("title")
            .setContentIntent(pendingIntent)
            .setWhen(System.currentTimeMillis())
            .setContentText("content")
            .setAutoCancel(false)
            .setOngoing(true)
            .build()
    }
}