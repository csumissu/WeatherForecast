package csumissu.weatherforecast.model.remote

import android.Manifest
import csumissu.weatherforecast.App
import csumissu.weatherforecast.BuildConfig
import csumissu.weatherforecast.extensions.isPermissionGranted
import csumissu.weatherforecast.model.ForecastList
import csumissu.weatherforecast.util.Utils
import io.reactivex.Flowable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author yxsun
 * @since 06/06/2017
 */
interface ForecastApi {

    @GET("data/2.5/forecast/daily")
    fun getDailyForecasts(@Query("lat") latitude: Double,
                          @Query("lon") longitude: Double): Flowable<ForecastList>

    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val API_HOST = "http://api.openweathermap.org/"

        fun getApiService(): ForecastApi {
            val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(getQueryParamsInterceptor())
                    .addInterceptor(getHttpLoggingInterceptor())
                    .addNetworkInterceptor(getNetworkInterceptor())
                    .cache(getCache())
                    .retryOnConnectionFailure(true)

            return Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .client(clientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ForecastApi::class.java)
        }

        private fun getQueryParamsInterceptor() = Interceptor {
            val httpUrlBuilder = it.request().url().newBuilder()
            httpUrlBuilder.addQueryParameter("units", "metric")
            httpUrlBuilder.addQueryParameter("lang", Locale.getDefault().language)
            httpUrlBuilder.addQueryParameter("APPID", APP_ID)
            httpUrlBuilder.addQueryParameter("cnt", "16")

            it.proceed(it.request().newBuilder().url(httpUrlBuilder.build()).build())
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BASIC
            }
            return logInterceptor
        }

        private fun getNetworkInterceptor() = Interceptor {
            val request = it.request()
            val response = it.proceed(request)

            if (Utils.isNetworkAvailable(App.INSTANCE)) {
                response.newBuilder()
                        .header("Cache-Control", "public, max-ag=0")
                        .removeHeader("Pragma")
                        .build()
            } else {
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=${24 * 60 * 60}")
                        .removeHeader("Pragma")
                        .build()
            }

            response
        }

        private fun getCache(): Cache {
            val hasPermission = App.INSTANCE.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val cacheDir: File = if (hasPermission) {
                App.INSTANCE.externalCacheDir
            } else {
                App.INSTANCE.cacheDir
            }
            return Cache(cacheDir, 10 * 1024 * 1024)
        }

    }

}