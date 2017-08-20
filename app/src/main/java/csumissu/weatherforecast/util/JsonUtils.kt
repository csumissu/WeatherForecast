package csumissu.weatherforecast.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * @author yxsun
 * @since 18/08/2017
 */
object JsonUtils {

    val DEFAULT_GSON: Gson by lazy {
        GsonBuilder()
                .serializeNulls()
                .create()
    }

    inline fun <reified T : Any> convertFromJson(json: String?): T? {
        println("syxlog clazz=${T::class.java}, json=$json")
        return DEFAULT_GSON.fromJson(json, T::class.java)
    }

}