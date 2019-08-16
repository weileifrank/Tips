package com.frank.wallet.net

import com.frank.wallet.model.*
import com.frank.wallet.util.Constant
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*


interface WalletAPIService {
    //http://yapi.rechengit.com/mock/46/
    //2.0 刷新用户信息
    @POST("app/v2/client/refreshLogin/{username}")
    fun refreshLogin( @Path("username") username: String,@Header(Constant.TOKEN) token: String): Observable<BaseResponse<LoginBean>>

    //登录
    @FormUrlEncoded
    @POST("app/v2/client/login")
    fun getLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<BaseResponse<LoginBean>>

    // 退出登录
    @DELETE("app/v2/client/signOut")
    fun logout(
        @Header(Constant.TOKEN) token: String
    ): Observable<BaseResponse<Any>>

    //    2.0 获取用户邮箱地址
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("forget/password/queryUserMail")
    fun queryUserMail(@Body requestBody: RequestBody): Observable<BaseResponse<String>>

    // 2.0 发送邮件验证码
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("app/v2/client/forget/password")
    fun getDataForSendEmailCode(@Body requestBody: RequestBody): Observable<BaseResponse<String>>

    // 2.0 重置登录密码
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("app/v2/client/reset/password")
    fun resetPwd(@Body requestBody: RequestBody): Observable<BaseResponse<Any>>

    // 2.0 重置支付密码
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("app/v2/client/resetPayPwd")
    fun resetPayPwd(@Body requestBody: RequestBody): Observable<BaseResponse<Any>>

    //修改登录密码
    @FormUrlEncoded
    @POST("app/v2/client/edit/password")
    fun modifyLoginPwd(
        @Field("old") old: String,
        @Field("newPwd") newPwd: String,
        @Header(Constant.TOKEN) token: String
    ): Observable<BaseResponse<Any>>

    //修改登录密码
    @FormUrlEncoded
    @POST("app/v2/client/modifyPayPwd")
    fun modifyPayPwd(
        @Field("old") username: String,
        @Field("newPwd") password: String
    ): Observable<BaseResponse<Any>>


    //钱包页面
    @POST("app/v2/wallet/queryBalance")
    fun getWalletPageData(
        @Header(Constant.TOKEN) token: String
    ): Observable<BaseResponse<WalletBean>>


    //2.0商户提现
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("app/v2/wallet/fundOut")
    fun withdraw(
        @Body requestBody: RequestBody,
        @Header(Constant.TOKEN) token: String
    ): Observable<BaseResponse<WithdrawBean>>

    //转账商户查询
    @POST("app/v2/transfer/merchantList")
    fun queryMerchantList(@Header(Constant.TOKEN) token: String): Observable<BaseResponse<List<TransferAccountBean>>>

    //2.0 余额转账
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("app/v2/transfer/executeTransfer")
    fun transfer(@Body requestBody: RequestBody,@Header(Constant.TOKEN) token: String): Observable<BaseResponse<Any>>


    //2.0待结算账户流水查询
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST("app/v2/wallet/toBeSettleList")
    fun getUnSettleDataList(
        @Body requestBody: RequestBody,
        @Header(Constant.TOKEN) token: String
    ): Observable<BaseResponse<List<UnSettleBean>>>

    //2.0待结算账户流水详情
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST("app/v2/wallet/{sysTraceNo}/{payCode}")
    fun getUnSettleDataDetail(
        @Path("sysTraceNo") sysTraceNo: String,
        @Path("payCode") payCode: String,
        @Header(Constant.TOKEN) token: String
    ): Observable<BaseResponse<UnSettleDetailBean>>

    //2.0 余额户流水
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST("app/v2/wallet/balanceLog")
    fun getBalanceDataList(
        @Body requestBody: RequestBody,
        @Header(Constant.TOKEN) token: String
    ): Observable<BaseResponse<List<BalanceBean>>>

    //2.0 余额流水详情
    @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
    @POST("app/v2/wallet/accountInfo/{sysTraceNo}/{productCode}")
    fun getBalanceDataDetail(
        @Path("sysTraceNo") sysTraceNo: String,
        @Path("productCode") productCode: String,
        @Header(Constant.TOKEN) token: String
    ): Observable<BaseResponse<BalanceDetailBean>>

}