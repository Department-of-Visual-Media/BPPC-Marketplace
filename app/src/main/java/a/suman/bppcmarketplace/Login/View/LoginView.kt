package a.suman.bppcmarketplace.Login.View

import a.suman.bppcmarketplace.Login.ViewModel.LoginViewModel
import a.suman.bppcmarketplace.MainActivity
import a.suman.bppcmarketplace.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.SignInButton

class LoginView : AppCompatActivity() {
    private val RC_SIGN_IN = 1
    lateinit var loginViewModel: LoginViewModel
    lateinit var signInButton: SignInButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LoginViewModel::class.java)
        signInButton = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener {
            startActivityForResult(
                loginViewModel.initGoogleSignIn(),
                1
            )
        }

        loginViewModel.TokenLiveData.observe(this, Observer {
                if(it!=null)
                    startActivity(Intent(this, MainActivity::class.java))
                }
            )



        loginViewModel.LoginStatusLiveData.observe(this, Observer {
            if (it.equals("Error")) {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_LONG)
                    .show()
            }
            if (it.equals("Success")) {

            }
            if (it.equals("Server Error")) {
            Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_LONG)
                .show()
            }
        })
    }



    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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