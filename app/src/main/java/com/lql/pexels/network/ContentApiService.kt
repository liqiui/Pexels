package com.lql.pexels.network

import android.util.Log
import com.lql.pexels.utils.AppModuleProvider
import com.lql.pexels.model.Result
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ContentApiService {
    @GET("search")
    @Headers("Authorization:563492ad6f91700001000001414a159eaed14a5594dff71a233e1bd7")//Key
    fun search(@Query("query") query: String,
               @Query("page") pageIndex: Int): Observable<Result>

    companion object {
        private const val TAG = "ContentAPI"
        // using Jackson to map Json object to class instance,
        // because Jackson is the fastest json parser compared to other parsers like gson
        private fun makeJsonConverterFactory(): Converter.Factory {
            return JacksonConverterFactory.create(AppModuleProvider.objectMapper)
        }

        private fun makeNetworkClient(parameters: Map<String, Any>? = null): OkHttpClient {

            val clientBuilder = OkHttpClient().newBuilder()
            // disabled unused interceptors, the interceptor code is commented for debugging purpose
//            val requestInterceptor = parameters?.let {
//                Interceptor { chain ->
//                    val builder = chain.request().url().newBuilder()
//                    it.forEach {
//                        builder.addEncodedQueryParameter(it.key, it.value.toString())
//                    }
//                    val newUrl = builder.build()
//                    val newRequest = chain.request().newBuilder().url(newUrl).build()
//                    Log.d(TAG, newUrl.toString())
//                    return@Interceptor chain.proceed(newRequest)
//                }
//            }
//
//            clientBuilder.networkInterceptors().add(requestInterceptor)
//
//            // Inject response handler
//            val responseInterceptor = Interceptor { chain ->
//                val request = chain.request()
//                val response = chain.proceed(request)
//                return@Interceptor when (response.code()) {
//                    204 -> throw Exception("No Content")
//                    else -> response
//                }
//            }
//            clientBuilder.networkInterceptors().add(responseInterceptor)
            return clientBuilder.build()
        }

        // using RxJava to build up a pipeline to pass data between components
        fun create(parameters: Map<String, Any>? = null): ContentApiService {
            val retrofitBuilder = Retrofit.Builder().addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            ).addConverterFactory(
                makeJsonConverterFactory()
            ).baseUrl(AppModuleProvider.apiBaseUrl)

            retrofitBuilder.client(makeNetworkClient(parameters))

            return retrofitBuilder.build().create(ContentApiService::class.java)
        }
    }
}
