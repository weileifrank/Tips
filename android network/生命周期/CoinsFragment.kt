package com.xysl.foot.main.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xysl.common.utils.logD
import com.xysl.foot.R
import com.xysl.foot.databinding.FragmentCoinsBinding
import com.xysl.foot.main.viewmodel.CoinsViewModel
import com.xysl.foot.view.base.BaseLifecycleObserver
import com.xysl.foot.view.base.binding

class CoinsFragment : Fragment(R.layout.fragment_coins) {

//    val bindings by binding(FragmentCoinsBinding::bind)
    val bindings by binding<FragmentCoinsBinding>()
    private lateinit var coinsViewModel: CoinsViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        logD("CoinsFragment【onAttach】")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logD("CoinsFragment【onCreate】")
//        lifecycle.addObserver(BaseLifecycleObserver())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logD("CoinsFragment【onViewCreated】")
        coinsViewModel = ViewModelProvider(this).get(CoinsViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_coins, container, false)
//        val textView: TextView = root.findViewById(R.id.text_coins)
        coinsViewModel.text.observe(viewLifecycleOwner, Observer {
            bindings.textCoins.text = it
        })
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        logD("CoinsFragment【onCreateView】")
//
//        return bindings.root
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logD("CoinsFragment【onActivityCreated】")
    }

    override fun onStart() {
        super.onStart()
        logD("CoinsFragment【onStart】")
    }

    override fun onResume() {
        super.onResume()
        logD("CoinsFragment【onResume】")
    }

    override fun onPause() {
        super.onPause()
        logD("CoinsFragment【onPause】")
    }

    override fun onStop() {
        super.onStop()
        logD("CoinsFragment【onStop】")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logD("CoinsFragment【onDestroyView】")
    }

    override fun onDestroy() {
        super.onDestroy()
        logD("CoinsFragment【onDestroy】")
    }

    override fun onDetach() {
        super.onDetach()
        logD("CoinsFragment【onDetach】")
    }
}