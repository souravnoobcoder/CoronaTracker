package com.example.coronatracker.api

import com.example.coronatracker.funtions.MyApplication.Companion.getInstance
import com.example.coronatracker.funtions.MyApplication.Companion.hasNetwork
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object NewsApi {
    private const val cacheSize = (1024 * 1024) // 1 MB
        .toLong()
    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val HEADER_PRAGMA = "Pragma"
    private val CACHE_DIR = getInstance()!!.cacheDir
    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache())
            .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
            .addNetworkInterceptor(networkInterceptor()) // only used when network is on
            .addInterceptor(offlineInterceptor())
            .build()
    }

    private fun okHttpClientCountries(): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cacheCountry())
            .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
            .addNetworkInterceptor(networkInterceptor()) // only used when network is on
            .addInterceptor(offlineInterceptor())
            .build()
    }

    private fun okHttpClientStates(): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cacheState())
            .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
            .addNetworkInterceptor(networkInterceptor()) // only used when network is on
            .addInterceptor(offlineInterceptor())
            .build()
    }

    private fun cache(): Cache {
        return Cache(File(CACHE_DIR, "someIdentifier"), cacheSize)
    }

    private fun cacheCountry(): Cache {
        return Cache(File(CACHE_DIR, "country"), cacheSize)
    }

    private fun cacheState(): Cache {
        return Cache(File(CACHE_DIR, "state"), cacheSize)
    }

    /**
     * This interceptor will be called both if the network is available and if the network is not available
     * @return
     */
    private fun offlineInterceptor(): Interceptor {
        return Interceptor { chain ->
            println("offline interceptor: called.")
            var request = chain.request()

            // prevent caching when network is on. For that we use the "networkInterceptor"
            if (!hasNetwork()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }

    private const val BASE_COUNTRY_URL = "https://corona.lmao.ninja/v3/"
    private const val WORLD_URL = "https://corona.lmao.ninja/v2/"
    private var COVID_API_INSTANCE: Retrofit? = null
    private var WORLD: Retrofit? = null
    private var INDIA_STATE: Retrofit? = null

    /**
     * This interceptor will be called ONLY if the network is available
     * @return
     */
    private fun networkInterceptor(): Interceptor {
        return Interceptor { chain ->
            println("network interceptor: called.")
            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(10, TimeUnit.MINUTES)
                .build()
            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private const val STATE_URL = "https://api.rootnet.in/"
    val apiInstance: Retrofit?
        get() {
            if (COVID_API_INSTANCE == null) {
                COVID_API_INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_COUNTRY_URL)
                    .client(okHttpClientCountries())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return COVID_API_INSTANCE
        }
    val world: Retrofit?
        get() {
            if (WORLD == null) {
                WORLD = Retrofit.Builder()
                    .baseUrl(WORLD_URL)
                    .client(okHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return WORLD
        }
    val indiaState: Retrofit?
        get() {
            if (INDIA_STATE == null) {
                INDIA_STATE = Retrofit.Builder()
                    .baseUrl(STATE_URL)
                    .client(okHttpClientStates())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INDIA_STATE
        }
}