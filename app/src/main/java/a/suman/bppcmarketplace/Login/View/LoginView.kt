package a.suman.bppcmarketplace.Login.View

import a.suman.bppcmarketplace.Login.ViewModel.LoginViewModel
import a.suman.bppcmarketplace.MainActivity
import a.suman.bppcmarketplace.NewUser.View.NewUser
import a.suman.bppcmarketplace.R
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.util.Log
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.login_layout.*
import kotlin.math.sin

class LoginView : AppCompatActivity(), SensorEventListener {
    private val RC_SIGN_IN = 1

    lateinit var loginViewModel: LoginViewModel
    lateinit var sensormanager:SensorManager
    lateinit var gyro:Sensor
    var timestamp:Float=0f
    var cummulativeRotationAroundX:Float=0f
    var cummulativeRotationAroundY:Float=0f

    var imageview5TranslationX:Float=0f
    var imageview5TranslationY:Float=0f
    var viewTranslationX:Float=0f
     var viewTranslationY:Float=0f
    var imageViewTranslationX:Float=0f
    var imageViewTranslationY:Float=0f

    var timeelapsed:Long=0

    companion object {
        const val TAG: String = "LoginView"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        imageview5TranslationX=imageview5.translationX
        imageview5TranslationY=imageview5.translationY
        imageViewTranslationX=imageView.translationX
        imageViewTranslationY=imageView.translationX
        viewTranslationX=view.translationX
        viewTranslationY=view.translationY


        sensormanager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyro=sensormanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LoginViewModel::class.java)
        sign_in_button.setOnClickListener {
            startActivityForResult(
                loginViewModel.initGoogleSignIn(),
                RC_SIGN_IN
            )
            sign_in_button.cardElevation=0f
            sign_in_button.isEnabled=false
        }

        loginViewModel.loginStatus.observe(this, Observer{
            if(it=="Error"){
                girl.visibility= View.VISIBLE
                loader.visibility=View.GONE
                progressBar.visibility=View.GONE
                sign_in_button.cardElevation=5f
                sign_in_button.isEnabled=true
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
            }
            if(it=="Internet"){
                girl.visibility= View.VISIBLE
                loader.visibility=View.GONE
                progressBar.visibility=View.GONE
                sign_in_button.cardElevation=5f
                sign_in_button.isEnabled=true
                Toast.makeText(this, "Check your Internet Connection", Toast.LENGTH_LONG).show()
            }
            if(it=="GoogleSignInError"){
                girl.visibility= View.VISIBLE
                loader.visibility=View.GONE
                progressBar.visibility=View.GONE
                sign_in_button.cardElevation=5f
                sign_in_button.isEnabled=true
                Toast.makeText(this, "Google Sign In Failed", Toast.LENGTH_LONG).show()
            }
            if(it=="Server Error"){
                girl.visibility= View.VISIBLE
                loader.visibility=View.GONE
                progressBar.visibility=View.GONE
                sign_in_button.cardElevation=5f
                sign_in_button.isEnabled=true
                Toast.makeText(this, "Server is under maintenance", Toast.LENGTH_LONG).show()
            }
        })

        loginViewModel.loginToken.observe(this, Observer {
            if(it.isNew){
                startActivity(Intent(this, NewUser::class.java))
            }else{
                startActivity((Intent(this, MainActivity::class.java)))
            }
        })



    }


    public override fun onActivityResult(requestCode: Int,resultCode:Int, data: Intent?) {
        girl.visibility= View.GONE
        loader.visibility=View.VISIBLE
        progressBar.visibility=View.VISIBLE
        loginViewModel.onResultFromActivity(requestCode, data)
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onResume() {
        super.onResume()
        sensormanager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensormanager.unregisterListener(this)
    }



    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

        //This is implemented in View to reduce the tiny latency due to transfer of data through LiveData
        if(timestamp!=0f) {
            timeelapsed = (event!!.timestamp - timestamp).toLong()/1000000
            cummulativeRotationAroundX += event!!.values[0] * timeelapsed/1000
            cummulativeRotationAroundY += event.values[1] * timeelapsed/1000


            imageview5.animate()
                .translationY(imageview5TranslationY + 20 * sin(cummulativeRotationAroundX)).duration =
                timeelapsed
            imageView.animate()
                .translationY(imageViewTranslationY + 5 * sin(cummulativeRotationAroundX)).duration =
                timeelapsed
            view.animate()
                .translationY(viewTranslationY + 20 * sin(cummulativeRotationAroundX)).duration =
                timeelapsed

            imageview5.animate()
                .translationX(imageview5TranslationX + 20 * sin(cummulativeRotationAroundY)).duration =
                timeelapsed
            imageView.animate()
                .translationX((imageViewTranslationX + 5 * sin(cummulativeRotationAroundY))).duration =
                timeelapsed
            view.animate()
                .translationX(viewTranslationX + 20 * sin(cummulativeRotationAroundY)).duration =
                timeelapsed
        }
        timestamp = event!!.timestamp.toFloat()
    }
}