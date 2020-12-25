package com.xysl.watermelonclean

import android.content.pm.PackageManager
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.meituan.android.walle.WalleChannelReader
import com.qq.e.comm.managers.GDTADManager
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.xysl.common.base.BaseApplication
import com.xysl.watermelonclean.ad.config.TTAdManagerHolder
import com.xysl.watermelonclean.filesys.AppInfoBean
import com.xysl.watermelonclean.filesys.ProcessManager
import com.xysl.watermelonclean.utils.*
import io.reactivex.plugins.RxJavaPlugins


class MyApp : BaseApplication() {
    var process30 = mutableListOf<AppInfoBean>()

    companion object {
        val DEFAULT_CHANNEL = "official"
        var channelName = DEFAULT_CHANNEL
        var powerLeft = ""
        var cpuTemp: Double = 0.0
        var batterTemp: Double = 0.0
        lateinit var app: MyApp


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
        app = this
        channelName = SPUtils.getInstance().getString(BaseNameConstants.KEY_CHANNEL_NAME)
        if (TextUtils.isEmpty(channelName)) {
//            val appInfo = app.packageManager.getApplicationInfo(app.packageName, PackageManager.GET_META_DATA)
//            channelName = appInfo.metaData["UMENG_CHANNEL"].toString()
            channelName = WalleChannelReader.getChannel(this.applicationContext)?:DEFAULT_CHANNEL
            if (TextUtils.isEmpty(channelName)) {
                channelName = DEFAULT_CHANNEL
            }
            SPUtils.getInstance().put(BaseNameConstants.KEY_CHANNEL_NAME, channelName)
        }
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
        InitManager.initGlobleBean()
        InitManager.initSessionId()

        InitManager.initYouMeng()

        //初始化穿山甲
        TTAdManagerHolder.init(this)
        //初始化广点通
        GDTADManager.getInstance().initWith(this,BaseKeyAppIdConstants.AD_APP_ID_GDT)
        //注册微信
        WxUtil.regToWx(this)
        //初始化语音
        SoundPoolUtil.initSoundPool(applicationContext)

    }


}