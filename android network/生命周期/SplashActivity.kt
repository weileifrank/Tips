package com.xysl.foot.main.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import com.xysl.common.utils.logD
import com.xysl.foot.R
import com.xysl.foot.beans.GlobleBean
import com.xysl.foot.databinding.ActivityMainBinding
import com.xysl.foot.databinding.ActivitySplashBinding
import com.xysl.foot.net.NetManager
import com.xysl.foot.net.RequestFactory
import com.xysl.foot.view.base.BaseActivity
import com.xysl.foot.view.base.binding

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    val bindings by binding(ActivitySplashBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding
        bindings?.apply {
            ivSplash.setImageResource(R.mipmap.ic_launcher)
        }
//        val intent = Intent()
//        intent.setClass(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()
    }

    override fun initData() {
//        lifecycleScope.launchWhenCreated {
////            val baseEntity = NetManager.instance.fetchList<GoldHistoryBean>(RequestFactory.getGoldCoinHistory(1.toString(),10.toString(),0.toString()))
////            logD("onCreate: baseEntity=$baseEntity")
//            var globleConfigReq = RequestFactory.getGlobleConfig()
//            var baseEntity = NetManager.instance.fetch<GlobleBean>(globleConfigReq) {
//                logD("name=${Thread.currentThread().name} it=$it")
//            }
//            var globleBean = baseEntity.data
//            ToastUtils.showShort(globleBean?.downloadUrl)
//            logD("name=${Thread.currentThread().name} baseEntity=$baseEntity")
//        }

    }


//            GlobalScope.launch {
////            var json = RequestFactory.login(CommonUtil.getAndroidId())
////            var account = NetManager.instance.fetch<Account>(json,false)
////            logD("onCreate: account=$account")
//                var globleConfigReq = RequestFactory.getGlobleConfig()
//                var baseEntity = NetManager.instance.fetch<GlobleBean>(globleConfigReq)
//                logD("i=$i---thread name=${Thread.currentThread().name} baseEntity=$baseEntity")
//            }
//
}