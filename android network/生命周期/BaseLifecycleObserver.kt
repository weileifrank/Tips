package com.xysl.foot.view.base

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

import androidx.lifecycle.OnLifecycleEvent
import com.xysl.common.utils.logD


open class BaseLifecycleObserver:LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        logD("Observer【onCreate】")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        logD("Observer【onStart】")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume(source: LifecycleOwner) {
        logD("Observer【onResume】")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        logD("Observer【onPause】")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        logD("Observer【onStop】")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        logD("Observer【onDestroy】")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun onAny(source: LifecycleOwner?, event: Lifecycle.Event) {
//        logD("Observer onAny：" )
    }

}