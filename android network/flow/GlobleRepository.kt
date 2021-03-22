package com.xysl.foot.main.repoitory

import com.xysl.foot.beans.BaseEntity
import com.xysl.foot.beans.FailCallBack
import com.xysl.foot.beans.GlobleBean
import com.xysl.foot.net.NetManager
import com.xysl.foot.net.RequestFactory

class GlobleRepository {
    suspend fun getGlobleConfig(isNeedAuth: Boolean = true, failCallBack: FailCallBack?=null): BaseEntity<GlobleBean> {
        var globleConfigReq = RequestFactory.getGlobleConfig()
        return NetManager.instance.fetch(globleConfigReq,isNeedAuth,failCallBack)
    }
}