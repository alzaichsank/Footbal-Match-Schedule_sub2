package alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit

import alzaichsank.com.aplikasifootbalmatchschedule.system.config.APIConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.*

/**
 * Created by alzaichsank on 1/22/18.
 */
interface BaseApiServices {

    @GET(APIConfig.all_leagues)
    fun getall_leagues(): Call<ResponseBody>

    @GET(APIConfig.eventspastleague)
    fun geteventspastleague(@Query("id") id : String): Call<ResponseBody>

    @GET(APIConfig.eventsnextleague)
    fun geteeventsnextleague(@Query("id") id : String): Call<ResponseBody>


    @GET(APIConfig.lookupteam)
    fun getlookupteam(@Query("id") id : String): Call<ResponseBody>
    }