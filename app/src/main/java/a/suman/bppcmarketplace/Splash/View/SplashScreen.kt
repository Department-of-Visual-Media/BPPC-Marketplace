package a.suman.bppcmarketplace.Splash.View

import a.suman.bppcmarketplace.Login.View.LoginView
import a.suman.bppcmarketplace.MainActivity
import a.suman.bppcmarketplace.NewUser.View.NewUser
import a.suman.bppcmarketplace.R
import a.suman.bppcmarketplace.Splash.ViewModel.SplashViewModel
import a.suman.bppcmarketplace.TokenClass
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class SplashScreen : AppCompatActivity() {

    //lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val itime = System.currentTimeMillis()
        val splashViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
                SplashViewModel::class.java
            )

        splashViewModel.authenticationLiveData.observe(this, Observer {
            Log.d("Ayush", "Splash")
            val ftime = System.currentTimeMillis()
            if ((ftime - itime) < 2000) {
                if (it != null) {
                    Log.d("Token", it.token)
                    TokenClass.token = it.token
                    Handler().postDelayed({
                        if (it.isNew) {
                            startActivity(Intent(this, NewUser::class.java))
                            finish()
                        } else {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }, 1000)
                } else {
                    Handler().postDelayed(
                        { startActivity(Intent(this, LoginView::class.java))
                            finish()},
                        1000)
                }
            } else {
                if (it != null) {
                    TokenClass.token=it.token
                    //splashViewModel.updateData()
                    if (it.isNew) {
                        startActivity(Intent(this, NewUser::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                } else {
                    startActivity(Intent(this, LoginView::class.java))
                    finish()
                }
            }
        })

    }
}
