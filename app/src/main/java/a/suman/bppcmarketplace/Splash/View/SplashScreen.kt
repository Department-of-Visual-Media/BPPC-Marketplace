package a.suman.bppcmarketplace.Splash.View

import a.suman.bppcmarketplace.Login.View.LoginView
import a.suman.bppcmarketplace.MainActivity
import a.suman.bppcmarketplace.NewUser.View.NewUser
import a.suman.bppcmarketplace.R
import a.suman.bppcmarketplace.Splash.ViewModel.SplashViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Handler
import android.util.Log

class SplashScreen : AppCompatActivity() {

    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val itime = System.currentTimeMillis()
        splashViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                SplashViewModel::class.java
            )
        splashViewModel.authenticationLiveData.observe(this, Observer {
            val ftime = System.currentTimeMillis()
            if ((ftime - itime) < 2000) {
                if (it != null) {
                    //splashViewModel.updateData()
                    Handler().postDelayed({
                        if (it.isNew) {
                            startActivity(Intent(this, NewUser::class.java))
                        } else {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                    }, 1000)
                } else {
                    Handler().postDelayed(
                        { startActivity(Intent(this, LoginView::class.java)) },
                        1000)
                }
            } else {
                if (it != null) {
                    //splashViewModel.updateData()
                    if (it.isNew) {
                        startActivity(Intent(this, NewUser::class.java))
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                } else {
                    startActivity(Intent(this, LoginView::class.java))
                }
            }
        })

    }
}
