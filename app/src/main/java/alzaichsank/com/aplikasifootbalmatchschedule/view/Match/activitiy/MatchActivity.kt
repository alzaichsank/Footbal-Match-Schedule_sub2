package alzaichsank.com.aplikasifootbalmatchschedule.view.Match.activitiy

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.model.EventsItem
import alzaichsank.com.aplikasifootbalmatchschedule.model.LeaguesItem
import alzaichsank.com.aplikasifootbalmatchschedule.utils.ServerCallback
import alzaichsank.com.aplikasifootbalmatchschedule.utils.gone
import alzaichsank.com.aplikasifootbalmatchschedule.utils.invisible
import alzaichsank.com.aplikasifootbalmatchschedule.utils.visible
import alzaichsank.com.aplikasifootbalmatchschedule.view.DetailMatch.activitiy.DetailMatchActivity
import alzaichsank.com.aplikasifootbalmatchschedule.view.Match.`interface`.MatchView
import alzaichsank.com.aplikasifootbalmatchschedule.view.Match.adapter.matchAdapter
import alzaichsank.com.aplikasifootbalmatchschedule.view.Match.presenter.match_presenter
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_match.*
import org.json.JSONObject
import java.util.*

class MatchActivity : AppCompatActivity(),MatchView {
    private var listLeageu = ArrayList<LeaguesItem>()
    private var menuItem : Int ? = 1
    private var idSpinner : String ? = ""
    private val presenter = match_presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        getlistLeageu()
        set_spinner(menuItem!!)
        initBottomNavigationContainer()
        initContainer()
        refreshButton.setOnClickListener {
            if(presenter.isNetworkAvailable(this)){
                this@MatchActivity.finish()
                this@MatchActivity.startActivity(this@MatchActivity.intent)
            }else{
                Snackbar.make(match_activity, getString(R.string.turnOn_internet)
                        , Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun initLayout(){
        setContentView(R.layout.activity_match)
    }

    private fun initBottomNavigationContainer() {
        navigation.setOnNavigationItemSelectedListener(bottomNavigationListener)
    }

    private val bottomNavigationListener by lazy {
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_menu_match_prev -> {
                    menuItem = 1
                    set_spinner(menuItem!!)
                    setDataToContainer(idSpinner.toString(),menuItem!!)
                    Log.d("TAG", "ini prev")
                    true
                 }
                R.id.main_menu_match_next -> {
                    menuItem = 2
                    set_spinner(menuItem!!)
                    setDataToContainer(idSpinner.toString(),menuItem!!)
                    Log.d("TAG", "ini next")
                    true
                 }else ->{
                true
                }
            }
        }
    }

    override fun showLoading() {
        progressbar.visible()
        recyclerview.invisible()
        emptyDataView.invisible()
        noconectionView.invisible()
    }

    override fun hideLoading() {
        progressbar.gone()
        recyclerview.visible()
        emptyDataView.invisible()
        noconectionView.invisible()

    }

    override fun showEmptyData() {
        progressbar.gone()
        recyclerview.invisible()
        if (presenter.isNetworkAvailable(this)) {
            emptyDataView.visible()
        }else{
            noconectionView.visible()

        }
    }

    private fun getlistLeageu() {
        if (presenter.isNetworkAvailable(this)) {
            presenter.getSpinnerData(this, object : ServerCallback {
                override fun onSuccess(response: String) {
                    if (presenter.isSuccess(response)) {
                        try {
                            val jsonObject = JSONObject(response)
                            val message = jsonObject.getJSONArray("leagues")
                            var numData = message.length()
                            val idLeague = arrayOfNulls<String>(numData)
                            val leagueName = arrayOfNulls<String>(numData)
                            listLeageu.clear()
                            if(numData>0) {
                                for (i in 0 until numData) {
                                    val data = message.getJSONObject(i)
                                    idLeague[i] = data.getString("idLeague")
                                    leagueName[i] = data.getString("strLeague")
                                    listLeageu.add(LeaguesItem(idLeague[i].toString(), leagueName[i].toString()))
                                }
                                spinner.adapter = ArrayAdapter<String>(this@MatchActivity, android.R.layout.simple_spinner_dropdown_item, leagueName)
                            }else{
                            }
                        } catch (e: NullPointerException) {
                            showEmptyData()
                        }
                    }
                }
                override fun onFailed(response: String) {
                }
                override fun onFailure(throwable: Throwable) {
                }
            })
        }else{
            showEmptyData()
            Snackbar.make(match_activity, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }

    }

    fun set_spinner(menu : Int){
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                idSpinner = listLeageu[position].idLeague
                when (menu) {
                    1 -> setDataToContainer(idSpinner.toString(), 1)
                    2 -> setDataToContainer(idSpinner.toString(), 2)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun initContainer() {
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = matchAdapter {posistionData ->
            val dataIntent = getListAdapter()?.getDataAt(posistionData)
            val intent = Intent(this, DetailMatchActivity::class.java)
            intent.putExtra(getString(R.string.menuItem), menuItem.toString())
            intent.putExtra(getString(R.string.idEvent), dataIntent?.idEvent)
            intent.putExtra(getString(R.string.dateEvent), dataIntent?.dateEvent)
//            home
            intent.putExtra(getString(R.string.idHomeTeam), dataIntent?.idHomeTeam)
            intent.putExtra(getString(R.string.strHomeTeam), dataIntent?.strHomeTeam)
            intent.putExtra(getString(R.string.intHomeScore), dataIntent?.intHomeScore)
            intent.putExtra(getString(R.string.strHomeFormation), dataIntent?.strHomeFormation)
            intent.putExtra(getString(R.string.strHomeGoalDetails), dataIntent?.strHomeGoalDetails)
            intent.putExtra(getString(R.string.intHomeShots), dataIntent?.intHomeShots)
            intent.putExtra(getString(R.string.strHomeLineupGoalkeeper), dataIntent?.strHomeLineupGoalkeeper)
            intent.putExtra(getString(R.string.strHomeLineupDefense), dataIntent?.strHomeLineupDefense)
            intent.putExtra(getString(R.string.strHomeLineupMidfield), dataIntent?.strHomeLineupMidfield)
            intent.putExtra(getString(R.string.strHomeLineupForward), dataIntent?.strHomeLineupForward)
            intent.putExtra(getString(R.string.strHomeLineupSubstitutes), dataIntent?.strHomeLineupSubstitutes)
//            away
            intent.putExtra(getString(R.string.idAwayTeam), dataIntent?.idAwayTeam)
            intent.putExtra(getString(R.string.strAwayTeam), dataIntent?.strAwayTeam)
            intent.putExtra(getString(R.string.intAwayScore), dataIntent?.intAwayScore)
            intent.putExtra(getString(R.string.strAwayFormation), dataIntent?.strAwayFormation)
            intent.putExtra(getString(R.string.strAwayGoalDetails), dataIntent?.strAwayGoalDetails)
            intent.putExtra(getString(R.string.intAwayShots), dataIntent?.intAwayShots)
            intent.putExtra(getString(R.string.strAwayLineupGoalkeeper), dataIntent?.strAwayLineupGoalkeeper)
            intent.putExtra(getString(R.string.strAwayLineupDefense), dataIntent?.strAwayLineupDefense)
            intent.putExtra(getString(R.string.strAwayLineupMidfield), dataIntent?.strAwayLineupMidfield)
            intent.putExtra(getString(R.string.strAwayLineupForward), dataIntent?.strAwayLineupForward)
            intent.putExtra(getString(R.string.strAwayLineupSubstitutes), dataIntent?.strAwayLineupSubstitutes)
            startActivity(intent)
        }
    }

    private fun setDataToContainer(id : String, menu : Int ) {
        var data: MutableList<EventsItem>
        if (presenter.isNetworkAvailable(this)) {
            if(menu == 1) {
                showLoading()
                presenter.getPrevMatch(this, id, object : ServerCallback {
                    override fun onSuccess(response: String) {
                        if (presenter.isSuccess(response)) {
                            try {
                                if (presenter.isSuccess(response)) {
                                    data = presenter.parsingData(this@MatchActivity, response)
                                    if (data.size < 1) {
                                        showEmptyData()
                                    } else {
                                        getListAdapter()?.setData(data.toMutableList())
                                        hideLoading()
                                    }
                                }
                            } catch (e: NullPointerException) {
                                showEmptyData()
                            }
                        }
                    }
                    override fun onFailed(response: String) {
                        showEmptyData()
                    }
                    override fun onFailure(throwable: Throwable) {
                        showEmptyData()
                    }
                })
            }else if ( menu == 2) {
                showLoading()
                presenter.getNextMatch(this, id, object : ServerCallback {
                    override fun onSuccess(response: String) {
                        if (presenter.isSuccess(response)) {
                            try {
                                if (presenter.isSuccess(response)) {
                                    data = presenter.parsingData(this@MatchActivity, response)
                                    if (data.size < 1) {
                                        showEmptyData()
                                    } else {
                                        getListAdapter()?.setData(data.toMutableList())
                                        hideLoading()
                                    }
                                }
                            } catch (e: NullPointerException) {
                                showEmptyData()
                            }
                        }
                    }
                    override fun onFailed(response: String) {
                        showEmptyData()
                    }
                    override fun onFailure(throwable: Throwable) {
                        showEmptyData()
                    }
                })
            }else{
                showEmptyData()
            }
        }else{
            showEmptyData()
            Snackbar.make(match_activity, getString(R.string.turnOn_internet)
                    , Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getListAdapter(): matchAdapter? = recyclerview?.adapter as? matchAdapter
}
