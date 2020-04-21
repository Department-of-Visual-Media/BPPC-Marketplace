package a.suman.bppcmarketplace.Login.View

import a.suman.bppcmarketplace.Login.ViewModel.LoginViewModel
import a.suman.bppcmarketplace.MainActivity
import a.suman.bppcmarketplace.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.login_layout.*

class LoginView : AppCompatActivity() {
    private val RC_SIGN_IN = 1
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LoginViewModel::class.java)
        sign_in_button.setOnClickListener {
            startActivityForResult(
                loginViewModel.initGoogleSignIn(),
                1
            )
            sign_in_button.cardElevation=0f
            sign_in_button.isEnabled=false
        }

        loginViewModel.TokenLiveData.observe(this, Observer {
                if(it!=null)
                    startActivity(Intent(this, MainActivity::class.java))
                }
            )



        loginViewModel.LoginStatusLiveData.observe(this, Observer {
            if (it=="Error" || it=="Server Error") {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_LONG)
                    .show()
                girl.visibility=View.VISIBLE
                loader.visibility=View.GONE
                progressBar.visibility=View.GONE
                sign_in_button.cardElevation=5f
                sign_in_button.isEnabled=true
            }
        })
    }



    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        girl.visibility= View.GONE
        loader.visibility=View.VISIBLE
        progressBar.visibility=View.VISIBLE
        loginViewModel.onResultFromActivity(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.clearDisposables()
    }

    companion object {
        const val TAG: String = "LoginView"
    }
}