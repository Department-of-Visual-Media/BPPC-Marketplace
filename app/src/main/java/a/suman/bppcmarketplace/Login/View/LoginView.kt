package a.suman.bppcmarketplace.Login.View

import a.suman.bppcmarketplace.Login.Model.ActivityNavigation
import a.suman.bppcmarketplace.Login.ViewModel.LoginViewModel
import a.suman.bppcmarketplace.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.SignInButton

class LoginView : AppCompatActivity(), ActivityNavigation {
    lateinit var loginViewModel: LoginViewModel
    lateinit var signInButton: SignInButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        signInButton = findViewById(R.id.sign_in_button)
        subscribeView()
        signInButton.setOnClickListener { loginViewModel.initGoogleSignIn() }

    }

    private fun subscribeView() {
        loginViewModel.startActivityForResultEvent.setEventReceiver(this, this)
        //this sets the LifeCycler owner and receiver
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "Getting account and sending to LoginViewModel")
        loginViewModel.onResultFromActivity(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.clearDisposables()
    }

    companion object {
        final val TAG: String = "LoginView"
    }
}