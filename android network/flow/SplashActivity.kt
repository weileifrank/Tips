package com.xysl.foot.main.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import com.xysl.common.utils.logD
import com.xysl.foot.beans.GlobleBean
import com.xysl.foot.databinding.ActivitySplashBinding
import com.xysl.foot.main.viewmodel.SplashViewModel
import com.xysl.foot.net.NetManager
import com.xysl.foot.net.RequestFactory
import com.xysl.foot.view.base.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    val splashViewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val intent = Intent()
//        intent.setClass(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()

        initData()
    }

    private fun initData() {
        splashViewModel.liveData.observe(this, Observer{
                it?.apply {
                    binding.tvSplash.text = downloadUrl
                }
        })
//        lifecycleScope.launchWhenResumed {
//            logD("start thread name = ${Thread.currentThread().name}")
//            val intFlow = flow<Int> {
//                (1..10).forEach {
//                    logD("emit it=$it thread name = ${Thread.currentThread().name}")
//                    emit(it)
//                    delay(1000)
////                    if (it == 2) {
////                        val i = it / 0;
////                    }
//                }
//            }.catch { t: Throwable ->
//                logD("catch errorMsg=${t.message}   thread name = ${Thread.currentThread().name}")
//            }.onCompletion {
//                logD("onCompletion thread name = ${Thread.currentThread().name}")
//            }
//            intFlow.flowOn(Dispatchers.IO).collect {
//                logD("collect it=$it thread name = ${Thread.currentThread().name}")
//                delay(300)
//            }
//            logD("end thread name = ${Thread.currentThread().name}")
//        }
//        lifecycleScope.launchWhenCreated {
//            var globleConfigReq = RequestFactory.getGlobleConfig()
//            var baseEntity = NetManager.instance.fetch<GlobleBean>(globleConfigReq)
//            baseEntity.data?.apply {
//                flow<GlobleBean> {
//                    emit(this@apply)
//                }.map { downloadUrl }.collect {
//                    ToastUtils.showShort(it)
//                }
//            }
//            logD("name=${Thread.currentThread().name} baseEntity=$baseEntity")
//        }
    }

//        lifecycleScope.launchWhenCreated {
////            val baseEntity = NetManager.instance.fetchList<GoldHistoryBean>(RequestFactory.getGoldCoinHistory(1.toString(),10.toString(),0.toString()))
////            logD("onCreate: baseEntity=$baseEntity")
//            var globleConfigReq = RequestFactory.getGlobleConfig()
//            var baseEntity = NetManager.instance.fetch<GlobleBean>(globleConfigReq) {
//                logD("name=${Thread.currentThread().name} it=$it")
//            }
//
//            logD("name=${Thread.currentThread().name} baseEntity=$baseEntity")
//        }

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