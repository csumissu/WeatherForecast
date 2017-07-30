package csumissu.weatherforecast.model.remote

import csumissu.weatherforecast.model.ForecastList
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * @author yxsun
 * @since 06/06/2017
 */
interface ForecastApi {

    @GET("data/2.5/forecast/daily")
    fun getDailyForecasts(@Query("lat") latitude: Double,
                          @Query("lon") longitude: Double): Flowable<ForecastList>

    companion object {
        val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        val API_HOST = "http://api.openweathermap.org/"

        fun getApiService(): ForecastApi {
            val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            clientBuilder.addInterceptor { it ->
                val httpUrlBuilder = it.request().url().newBuilder()
                httpUrlBuilder.addQueryParameter("units", "metric")
                httpUrlBuilder.addQueryParameter("lang", Locale.getDefault().language)
                httpUrlBuilder.addQueryParameter("APPID", APP_ID)

                it.proceed(it.request().newBuilder().url(httpUrlBuilder.build()).build())
            }

            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            clientBuilder.addInterceptor(logInterceptor)

            return Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .client(clientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ForecastApi::class.java)
        }
    }

}