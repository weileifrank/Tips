package com.xysl.citypackage.model.net

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.xysl.citypackage.AppNavigator
import com.xysl.citypackage.BuildConfig
import com.xysl.citypackage.InitManager
import com.xysl.citypackage.constants.BaseCodeConstants
import com.xysl.citypackage.constants.PageType
import com.xysl.citypackage.model.bean.BaseEntity
import com.xysl.citypackage.model.bean.FailCallBack
import com.xysl.citypackage.utils.GsonUtil
import com.xysl.common.utils.logTest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

/**
 * 网络请求工具
 * 分为对象和数组两种请求方式
 * 返回的结果是对象请调用fetch方法
 * 返回的结果是数组请调用fetchList方法
 */
class NetManager private constructor() {
    var apiService: ApiService
    val gson by lazy { GsonBuilder().serializeNulls().create() }

    companion object {
        const val BASE_URL_DEBUG = "http://apptools-gateway.xysl.com/"
//        const val BASE_URL_DEBUG = "http://192.168.26.71/"
        const val BASE_URL_RELEASE = "https://apptools-gateway.xysl.com/"
        const val NEED_AUTH_HEADER_KEY = "isNeedAuth"
        const val NEED_AUTH_HEADER_VALUE = "1"
        const val NO_NEED_AUTH_HEADER_VALUE = "0"
        val instance = NetManager()
        const val TIME_SET = 5000L
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    }

    init {
        val hostUrl = if (BuildConfig.DEBUG) BASE_URL_DEBUG else BASE_URL_RELEASE
        val httpLoggingInterceptor = HttpLoggingInterceptor(LoggingInterceptorLogger())
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(TIME_SET, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_SET, TimeUnit.MILLISECONDS)
            .connectTimeout(TIME_SET, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(HeadInterceptor())
            .addInterceptor(TokenInterceptor())
            .retryOnConnectionFailure(true)
            .build()
        apiService = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(hostUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

//----------------------------------------------------------------------接口----------------------------------------------------------------


    /**
     * json 组装的请求体json string,通过requestfactory产生
     * isNeedAuth:是否需要认证,默认所有的请求都需要认证
     * failCallBack:请求失败的回调
     */
    suspend inline fun <reified T> fetch(
        json: String,
        isNeedAuth: Boolean = true,
        noinline failCallBack: FailCallBack? = null
    ): BaseEntity<T> {
        try {
            val body = json.toRequestBody(JSON)
            var baseEntity = apiService.fetch<T>(
                body,
                if (isNeedAuth) NEED_AUTH_HEADER_VALUE else NO_NEED_AUTH_HEADER_VALUE
            )
            if (baseEntity.data == null || "".equals(baseEntity.data)) {
                if (!baseEntity.isSuccess()) {
                    proceedError(ResponeThrowable(baseEntity.code, baseEntity.desc), failCallBack)
                }
                baseEntity.data = null
            } else {
                val dataJson = gson.toJson(baseEntity.data)
                baseEntity.data = gson.fromJson(dataJson, T::class.java)
            }
            return baseEntity
        } catch (e: Exception) {
            var responeThrowable = ApiException.getResponeThrowable(e)
            proceedError(responeThrowable, failCallBack)
            return BaseEntity(responeThrowable.code, responeThrowable.desc, null)
        }
    }

    suspend inline fun <reified T> fetchList(
        json: String,
        isNeedAuth: Boolean = true,
        noinline failCallBack: FailCallBack? = null
    ): BaseEntity<List<T>> {
        try {
            val body = json.toRequestBody(JSON)
            var baseEntity = apiService.fetchList<T>(
                body,
                if (isNeedAuth) NEED_AUTH_HEADER_VALUE else NO_NEED_AUTH_HEADER_VALUE
            )
            if (baseEntity.data == null || "[]".equals(baseEntity.data) || "".equals(baseEntity.data)) {
                if (!baseEntity.isSuccess()) {
                    proceedError(ResponeThrowable(baseEntity.code, baseEntity.desc), failCallBack)
                }
                baseEntity.data = null
            } else {
                val dataJson = gson.toJson(baseEntity.data)
//                baseEntity.data = GsonUtil.fromJsonArray(dataJson, T::class.java)
                baseEntity.data = parseJsonToList<T>(dataJson)
            }
            return baseEntity
        } catch (e: Exception) {
            var responeThrowable = ApiException.getResponeThrowable(e)
            proceedError(responeThrowable, failCallBack)
            return BaseEntity(responeThrowable.code, responeThrowable.desc, null)
        }
    }

    fun fetchSync(json: String, isNeedAuth: Boolean = true): Call<ResponseBody> {
        val body = json.toRequestBody(JSON)
        return apiService.fetchSync(
            body,
            if (isNeedAuth) NEED_AUTH_HEADER_VALUE else NO_NEED_AUTH_HEADER_VALUE
        )
    }

    fun proceedError(responeThrowable: ResponeThrowable, failCallBack: FailCallBack?) {
        if (responeThrowable.code == BaseCodeConstants.CODE_TOKEN_EXPIRE) {//token失效处理.需要重新登录
//            InitManager.login()
            logTest("重新登录responeThrowable=$responeThrowable")
            InitManager.session_id = ""
            AppNavigator.navigation(ActivityUtils.getTopActivity(), PageType.Login.redirectUrl)
            return
        }
        if (failCallBack != null) {
            failCallBack.invoke(responeThrowable)
        } else {
            if (responeThrowable.code == ERROR.REQUEST_CANCEL) {

            } else {
                ToastUtils.showShort(responeThrowable.desc)
            }
        }
    }

    suspend fun upload(json: String): ResponseBody {
        val body = json.toRequestBody(JSON)
        return apiService.upload(body)
    }

    inline fun <reified T> parseJsonToList(json:String):MutableList<T> = gson.fromJson(json,ParameterizedTypeImpl(T::class.java))


    class ParameterizedTypeImpl(val clz: Class<*>) : ParameterizedType {
        override fun getRawType(): Type = MutableList::class.java

        override fun getOwnerType(): Type? = null

        override fun getActualTypeArguments(): Array<Type> = arrayOf(clz)
    }
}