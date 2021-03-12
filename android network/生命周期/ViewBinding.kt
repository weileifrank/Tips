package com.xysl.foot.view.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.xysl.common.utils.logD
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


fun <VB : ViewBinding> AppCompatActivity.binding(inflate: (LayoutInflater) -> VB) = lazy {
    inflate(layoutInflater).apply { setContentView(root) }
}
fun <VB : ViewBinding> Fragment.binding(bind: (View) -> VB) = FragmentBindingDelegate(bind)

class FragmentBindingDelegate<VB : ViewBinding>(
    private val bind: (View) -> VB
) : ReadOnlyProperty<Fragment, VB> {

    private var isInitialized = false
    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (!isInitialized) {
            logD("init....")
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : BaseLifecycleObserver() {
                override fun onDestroy() {
                    super.onDestroy()
                    _binding = null
                }
            })
            _binding = bind(thisRef.requireView())
            isInitialized = true
        }
        return binding
    }

//inline fun <reified VB : ViewBinding> Fragment.binding() =  FragmentBindingDelegate(VB::class.java)
//
//
//class FragmentBindingDelegate<VB : ViewBinding>(
//    private val clazz: Class<VB>
//) : ReadOnlyProperty<Fragment, VB> {
//
//    private var isInitialized = false
//    private var _binding: VB? = null
//    private val binding: VB get() = _binding!!
//
//    @Suppress("UNCHECKED_CAST")
//    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
//        if (!isInitialized) {
//            logD("init....")
//            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : BaseLifecycleObserver() {
//                override fun onDestroy() {
//                    super.onDestroy()
//                    _binding = null
//                }
//            })
//            _binding = clazz.getMethod("bind", View::class.java)
//                .invoke(null, thisRef.requireView()) as VB
//            isInitialized = true
//        }
//        return binding
//    }
}