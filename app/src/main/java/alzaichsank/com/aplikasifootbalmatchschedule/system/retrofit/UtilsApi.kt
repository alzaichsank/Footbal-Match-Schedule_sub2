package alzaichsank.com.aplikasifootbalmatchschedule.system.retrofit

import alzaichsank.com.aplikasifootbalmatchschedule.system.config.APIConfig

/**
 * Created by alzaichsank on 1/24/18.
 */
object UtilsApi {
    val apiService: BaseApiServices
        get() = RetrofitClient.getClient(APIConfig.END_POINT).create(BaseApiServices::class.java)
}