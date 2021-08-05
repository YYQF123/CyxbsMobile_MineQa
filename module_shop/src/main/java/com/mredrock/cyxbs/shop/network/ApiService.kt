package com.mredrock.cyxbs.shop.network

import com.mredrock.cyxbs.common.bean.RedrockApiStatus
import com.mredrock.cyxbs.common.bean.RedrockApiWrapper
import com.mredrock.cyxbs.shop.bean.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("")
    fun exGood(@Field("title") title: String): Observable<RedrockApiWrapper<ExResp>>

    @GET("")
    fun getDecorationData(@Query("title") title: String): Observable<RedrockApiWrapper<DecorationResp>>

    @GET("")
    fun getStampGoodData(@Query("title") title: String): Observable<RedrockApiWrapper<StampGoodResp>>

    @GET("")
    fun getAllDecoration(): Observable<RedrockApiWrapper<AllDecorationResp>>

    @GET("")
    fun getAllStampGood(): Observable<RedrockApiWrapper<AllStampGoodResp>>

}