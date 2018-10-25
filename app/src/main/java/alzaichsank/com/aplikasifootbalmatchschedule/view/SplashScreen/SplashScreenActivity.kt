package alzaichsank.com.aplikasifootbalmatchschedule.view.SplashScreen

import alzaichsank.com.aplikasifootbalmatchschedule.R
import alzaichsank.com.aplikasifootbalmatchschedule.view.Match.activitiy.MatchActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        goToMain()
    }

    private fun initLayout(){
        setContentView(R.layout.activity_splash_screen)
    }

    private fun goToMain() {
        Handler().postDelayed(object : Thread() {
            override fun run() {
                    val intent = Intent(this@SplashScreenActivity, MatchActivity::class.java)
                    this@SplashScreenActivity.startActivity(intent)
                    this@SplashScreenActivity.finish()
            }
        }, 2500)
    }

}
