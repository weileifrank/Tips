package com.xysl.foot.view.base

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

import androidx.lifecycle.OnLifecycleEvent
import com.xysl.common.utils.logD


open class BaseLifecycleObserver:LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate(owner:LifecycleOwner) {
        logD("Observer---【onCreate】---owner=${owner::class.java.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart(owner:LifecycleOwner) {
        logD("Observer---【onStart】---owner=${owner::class.java.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume(owner: LifecycleOwner) {
        logD("Observer---【onResume】---owner=${owner::class.java.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause(owner:LifecycleOwner) {
        logD("Observer---【onPause】---owner=${owner::class.java.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop(owner:LifecycleOwner) {
        logD("Observer---【onStop】---owner=${owner::class.java.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy(owner:LifecycleOwner) {
        logD("Observer---【onDestroy】---owner=${owner::class.java.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun onAny(source: LifecycleOwner?, event: Lifecycle.Event) {
//        logD("Observer--- onAny：" )
    }

}