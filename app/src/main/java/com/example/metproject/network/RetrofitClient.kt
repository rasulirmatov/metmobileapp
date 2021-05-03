package com.example.metproject.network

import android.content.Context
import android.provider.SyncStateContract
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.metproject.BuildConfig
import com.example.metproject.utils.Constants
import com.google.gson.Gson
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {
    companion object {
        private var retrofit: Retrofit? = null

        fun getClient(context: Context): Retrofit? {

            val logging = HttpLoggingInterceptor()
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(OkHttpProfilerInterceptor())
                logging.level = HttpLoggingInterceptor.Level.BODY
            }else {
                logging.level = HttpLoggingInterceptor.Level.NONE
            }
            builder.addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            builder.connectTimeout(60000, TimeUnit.SECONDS)
            builder.writeTimeout(120000, TimeUnit.SECONDS)
            builder.readTimeout(120000, TimeUnit.SECONDS)
            builder.retryOnConnectionFailure(true)
            builder.addNetworkInterceptor(logging)
            val client = builder.cache(null).build()
                if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Constants.base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(client)
                    .build()
            }
            return retrofit

        }

    }
}
