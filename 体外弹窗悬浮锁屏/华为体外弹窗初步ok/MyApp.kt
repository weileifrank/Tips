package com.xysl.watermelonclean

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.SPUtils
import com.meituan.android.walle.WalleChannelReader
import com.qq.e.comm.managers.GDTADManager
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.xysl.common.base.BaseApplication
import com.xysl.common.base.utils.LogUtil
import com.xysl.watermelonclean.activity.BackSplashActivity
import com.xysl.watermelonclean.activity.MainActivity
import com.xysl.watermelonclean.activity.OutDialogActivity
import com.xysl.watermelonclean.activity.SplashActivity
import com.xysl.watermelonclean.ad.config.TTAdManagerHolder
import com.xysl.watermelonclean.filesys.AppInfoBean
import com.xysl.watermelonclean.filesys.ProcessManager
import com.xysl.watermelonclean.utils.*
import io.reactivex.plugins.RxJavaPlugins
import java.util.concurrent.LinkedBlockingQueue


class MyApp : BaseApplication() {
    val queue = LinkedBlockingQueue<Boolean>(5)
    var process30 = mutableListOf<AppInfoBean>()
    var handler = Handler(Looper.getMainLooper())

    companion object {
        val DEFAULT_CHANNEL = "cleantest"
        var channelName = DEFAULT_CHANNEL
        var powerLeft = ""
        var cpuTemp: Double = 0.0
        var batterTemp: Double = 0.0
        lateinit var app: MyApp
        var appStartTimeMill = 0L
        var lastOnStopTimeMill = 0L
        var hasFloatPermission = false
    }

    init {
        cpuTemp = CommonUtil.getCPUPTemp()
        batterTemp = CommonUtil.getBatteryTemp()
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setEnableHeaderTranslationContent(false)
            MaterialHeader(context).setColorSchemeResources(R.color.colorPrimary)
        }
    }

    override fun onCreate() {
        super.onCreate()
        appStartTimeMill = System.currentTimeMillis()
        Thread({
            //初始化穿山甲
            TTAdManagerHolder.init(this)
            queue.put(true)
        }).start()
        Thread({
            //初始化广点通
            GDTADManager.getInstance().initWith(this, BaseKeyAppIdConstants.AD_APP_ID_GDT)
            queue.put(true)
        }).start()
        app = this
        channelName = SPUtils.getInstance().getString(BaseNameConstants.KEY_CHANNEL_NAME)
        if (TextUtils.isEmpty(channelName)) {
//            val appInfo = app.packageManager.getApplicationInfo(app.packageName, PackageManager.GET_META_DATA)
//            channelName = appInfo.metaData["UMENG_CHANNEL"].toString()
            channelName = WalleChannelReader.getChannel(this.applicationContext) ?: ""
            if (TextUtils.isEmpty(channelName)) {
                channelName = DEFAULT_CHANNEL
            }
            SPUtils.getInstance().put(BaseNameConstants.KEY_CHANNEL_NAME, channelName)
        }
        registerActivityLifecycleCallback()
        ThreadUtil.runOnSubThread {
            val temp = ProcessManager.instance.getAllAppInfosLimit30(app)
            if (temp != null) {
                process30.clear()
                process30.addAll(temp)
            }
        }
        RxJavaPlugins.setErrorHandler {}
        if (!BuildConfig.DEBUG) {
            InitManager.initExceptionHandler()
        }
        InitManager.oaid = SPUtils.getInstance().getString(BaseNameConstants.KEY_OAID, "")
        InitManager.initGlobleBean()
        InitManager.initSessionId()

        InitManager.initYouMeng()
        InitManager.initJpush()

        //注册微信
        WxUtil.regToWx(this)
        //初始化语音
        SoundPoolUtil.initSoundPool(applicationContext)

        queue.take()
        queue.take()
        lastHotRebootTimeMill = System.currentTimeMillis()
        lastOnStopTimeMill = System.currentTimeMillis()
        LogUtil.d("duration=${System.currentTimeMillis() - appStartTimeMill}", "aaaaaa")
    }

    private var isRunInForeground = true
    private var lastHotRebootTimeMill = 0L

    private fun registerActivityLifecycleCallback() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    LogUtil.d(
                        "onActivityDestroyed   isForeground=${AppUtils.isAppForeground()}  isGrantedDrawOverlays=${PermissionUtils.isGrantedDrawOverlays()}",
                        "LifecycleCallback"
                    )
                }
                handler.postDelayed({
                    activity?.apply {
                        if (this is MainActivity) {
                            val intent = Intent(this, OutDialogActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }, 10000)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
                isRunInForeground = AppUtils.isAppForeground()
                lastOnStopTimeMill = System.currentTimeMillis()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    handler.postDelayed({
                        LogUtil.d(
                            "isForeground=${AppUtils.isAppForeground()}  isGrantedDrawOverlays=${PermissionUtils.isGrantedDrawOverlays()}",
                            "LifecycleCallback"
                        )
                    }, 500)
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                LogUtil.d("onActivityCreated activity=${activity}", "aaaaaa")
                if (activity is SplashActivity) {
                    lastHotRebootTimeMill = System.currentTimeMillis()
                }
            }

            override fun onActivityResumed(activity: Activity) {
                val cur = System.currentTimeMillis()
                val duration = cur - lastHotRebootTimeMill
                if (!isRunInForeground && duration > InitManager.hotIntervalTime) {
                    //后台进入到前台
                    activity?.apply {
                        lastHotRebootTimeMill = cur
                        isRunInForeground = AppUtils.isAppForeground()
                        val intent = Intent(this, BackSplashActivity::class.java)
                        startActivity(intent)
                    }
                }

            }

        })
    }

}