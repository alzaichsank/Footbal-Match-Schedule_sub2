package alzaichsank.com.aplikasifootbalmatchschedule.view.DetailMatch.activitiy

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.utils.DateTime.getLongDate
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.view.DetailMatch.presenter.detail_presenter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.android.synthetic.main.appbar_main.*
import org.json.JSONObject

class DetailMatchActivity : AppCompatActivity() {

    private var badgeHome: String? = ""
    private var badgeAway: String? = ""
    private var idHome: String? = ""
    private var idAway: String? = ""
    private var idTeam = arrayOf<String>()
    private var menuItem: String? = ""
    private val presenter = detail_presenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        implementPutExtra()
        setDataDetail()
    }

    private fun initLayout() {
        setContentView(R.layout.activity_detail_match)
        setSupportActionBar(toolbar_main)
        menuItem = intent.getStringExtra(getString(R.string.menuItem))
        if (menuItem!!.toInt() == 1) {
            detailNoResult.visibility = View.INVISIBLE
            layout_score.visibility = View.VISIBLE
            layout_match.visibility = View.VISIBLE
        } else {
            layout_score.visibility = View.INVISIBLE
            layout_match.visibility = View.INVISIBLE
            detailNoResult.visibility = View.VISIBLE
        }
    }

    private fun implementPutExtra() {
        dateDetail.text = getLongDate(intent.getStringExtra(getString(R.string.dateEvent)))
//        home
        idHome = intent.getStringExtra(getString(R.string.idHomeTeam))
        ID_HOME_SCORE_DETAIL.text = intent.getStringExtra(getString(R.string.intHomeScore))
        name_home.text = intent.getStringExtra(getString(R.string.strHomeTeam))
        GOAL_HOME_SCORE.text = intent.getStringExtra(getString(R.string.strHomeGoalDetails))
        SHOTS_HOME_SCORE.text = intent.getStringExtra(getString(R.string.intHomeShots))
        GK_HOME.text = intent.getStringExtra(getString(R.string.strHomeLineupGoalkeeper))
        DEF_HOME.text = intent.getStringExtra(getString(R.string.strHomeLineupDefense))
        MID_HOME.text = intent.getStringExtra(getString(R.string.strHomeLineupMidfield))
        FW_HOME.text = intent.getStringExtra(getString(R.string.strHomeLineupForward))
        SUB_HOME.text = intent.getStringExtra(getString(R.string.strHomeLineupSubstitutes))
//        away
        idAway = intent.getStringExtra(getString(R.string.idAwayTeam))
        ID_AWAY_SCORE_DETAIL.text = intent.getStringExtra(getString(R.string.intAwayScore))
        name_away.text = intent.getStringExtra(getString(R.string.strAwayTeam))
        GOAL_AWAY_SCORE.text = intent.getStringExtra(getString(R.string.strAwayGoalDetails))
        SHOTS_AWAY_SCORE.text = intent.getStringExtra(getString(R.string.intAwayShots))
        GK_AWAY.text = intent.getStringExtra(getString(R.string.strAwayLineupGoalkeeper))
        DEF_AWAY.text = intent.getStringExtra(getString(R.string.strAwayLineupDefense))
        MID_AWAY.text = intent.getStringExtra(getString(R.string.strAwayLineupMidfield))
        FW_AWAY.text = intent.getStringExtra(getString(R.string.strAwayLineupForward))
        SUB_AWAY.text = intent.getStringExtra(getString(R.string.strAwayLineupSubstitutes))
    }

    private fun setDataDetail() {
        idTeam = arrayOf(idHome.toString(), idAway.toString())
        if (presenter.isNetworkAvailable(this)) {
            if (presenter.isNetworkAvailable(this)) {
                for (i in 0 until idTeam.size) {
                    presenter.geDetailMatch(this, idTeam[i], object : ServerCallback {
                        override fun onSuccess(response: String) {
                            if (presenter.isSuccess(response)) {
                                try {
                                    if (presenter.isSuccess(response)) {
                                        if (i == 0) {
                                            val jsonObject = JSONObject(response)
                                            Log.d("TAG", "Response $jsonObject")
                                            val message = jsonObject.getJSONArray("teams")
                                            for (i in 0 until message.length()) {
                                                val data = message.getJSONObject(i)
                                                badgeHome = data.getString("strTeamBadge")
                                            }
                                            Picasso.with(applicationContext)
                                                    .load(badgeHome)
                                                    .placeholder(R.drawable.ic_no_data)
                                                    .error(R.drawable.ic_no_data)
                                                    .resize(175, 175)
                                                    .into(FlAG_HOME)
                                        } else if (i == 1) {
                                            val jsonObject = JSONObject(response)
                                            Log.d("TAG", "Response $jsonObject")
                                            val message = jsonObject.getJSONArray("teams")
                                            for (i in 0 until message.length()) {
                                                val data = message.getJSONObject(i)
                                                badgeAway = data.getString("strTeamBadge")
                                            }
                                            Picasso.with(applicationContext)
                                                    .load(badgeAway)
                                                    .placeholder(R.drawable.ic_no_data)
                                                    .error(R.drawable.ic_no_data)
                                                    .resize(175, 175)
                                                    .into(FlAG_AWAY)
                                        }
                                    }
                                } catch (e: NullPointerException) {

                                }
                            }
                        }

                        override fun onFailed(response: String) {
                        }

                        override fun onFailure(throwable: Throwable) {
                        }
                    })
                }
            } else {
                Snackbar.make(detail_actvity, getString(R.string.turnOn_internet)
                        , Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }

    override fun onBackPressed() {
        finish()
    }

}
