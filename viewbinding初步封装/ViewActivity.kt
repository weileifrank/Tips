package com.xysl.foot.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

//参考链接:https://blog.csdn.net/jingzz1/article/details/105909040/
abstract class ViewActivity<VB : ViewBinding> : AppCompatActivity() {
    private lateinit var _binding: VB
    protected val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(_binding.root)
    }

    protected abstract fun getViewBinding(): VB
}



