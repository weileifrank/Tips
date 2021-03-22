package com.xysl.foot.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.xysl.foot.beans.GlobleBean
import com.xysl.foot.main.repoitory.GlobleRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SplashViewModel() : ViewModel() {
    val globleRepository by lazy { GlobleRepository() }
    lateinit var liveData: LiveData<GlobleBean?>
    init {
        viewModelScope.launch {
            liveData = flow{
                emit(globleRepository.getGlobleConfig().data)
            }.asLiveData()
        }
    }
}