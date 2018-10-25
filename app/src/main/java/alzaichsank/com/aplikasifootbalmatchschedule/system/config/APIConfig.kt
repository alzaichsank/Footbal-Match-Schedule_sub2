package alzaichsank.com.aplikasifootbalmatchschedule.system.config

/**
 * Created by Alza Ichsan Kurniawa on 18/12/2017.
 */
class APIConfig {
    companion object {
        // base end point
        const val END_POINT = "https://www.thesportsdb.com/api/v1/json/1/"
        // all leagues ( spinner )
        const val all_leagues = "all_leagues.php"
        // prev match
        const val eventspastleague = "eventspastleague.php"
        // next match
        const val eventsnextleague = "eventsnextleague.php"
        // detail team
        const val lookupteam = "lookupteam.php"
    }
}
