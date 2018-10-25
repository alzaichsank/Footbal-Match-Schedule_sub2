package alzaichsank.com.aplikasifootbalmatchschedule.view.DetailMatch.`interface`

import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import android.app.Activity

interface detail_interface {
    fun isNetworkAvailable(context: Activity): Boolean
    fun geDetailMatch(context: Activity, id : String,  callback: ServerCallback)
    fun isSuccess(response: String): Boolean
    fun parsingData(context: Activity, response: String): ArrayList<EventsItem>
}