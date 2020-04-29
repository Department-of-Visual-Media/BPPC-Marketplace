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
import android.view.animation.AccelerateDecelerateInterpolator
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


    var imageViewTranslationX:Float=0f
    var imageViewTranslationY:Float=0f
    var imageView2TranslationX:Float=0f
    var imageView2TranslationY:Float=0f
    var imageView3TranslationX:Float=0f
    var imageView3TranslationY:Float=0f
    var imageView4TranslationX:Float=0f
    var imageView4TranslationY:Float=0f

    var timeelapsed:Long=0
    var timeelapsedf:Float=0f

    companion object {
        const val TAG: String = "LoginView"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        imageViewTranslationX=imageView.translationX
        imageViewTranslationY=imageView.translationY
        imageView2TranslationX=imageView2.translationX
        imageView2TranslationY=imageView2.translationY
        imageView3TranslationX=imageView3.translationX
        imageView3TranslationY=imageView3.translationY
        imageView4TranslationX=imageView4.translationX
        imageView4TranslationY=imageView4.translationY



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
        if(gyro!=null){
            sensormanager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(gyro!=null) {
            sensormanager.unregisterListener(this)
        }
    }



    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

        //This is implemented in View to reduce the tiny latency due to transfer of data through LiveData
        if(timestamp!=0f) {
            timeelapsed = (event!!.timestamp - timestamp).toLong()/1000000
            timeelapsedf = (event!!.timestamp.toFloat() - timestamp)/1000000000
            cummulativeRotationAroundX += event!!.values[0] * timeelapsedf
            cummulativeRotationAroundY += event.values[1] * timeelapsedf

            if(cummulativeRotationAroundX<Math.PI/2 && cummulativeRotationAroundX>(-Math.PI/2)) {
                imageView.animate()
                    .translationY(imageViewTranslationY + 80 * sin(cummulativeRotationAroundX))
                    .setInterpolator(AccelerateDecelerateInterpolator()).duration =
                    timeelapsed * 2
                imageView2.animate()
                    .translationY(imageView2TranslationY + 60 * sin(cummulativeRotationAroundX))
                    .setInterpolator(AccelerateDecelerateInterpolator()).duration =
                    timeelapsed * 2
                imageView3.animate()
                    .translationY(imageView3TranslationY + 80 * sin(cummulativeRotationAroundX))
                    .setInterpolator(AccelerateDecelerateInterpolator()).duration =
                    timeelapsed * 2
                imageView4.animate()
                    .translationY(imageView4TranslationY + 80 * sin(cummulativeRotationAroundX))
                    .setInterpolator(AccelerateDecelerateInterpolator()).duration =
                    timeelapsed * 2
            }
            if(cummulativeRotationAroundY<Math.PI/2 && cummulativeRotationAroundY>(-Math.PI/2)){
            imageView.animate()
                .translationX((imageViewTranslationX + 80 * sin(cummulativeRotationAroundY))).setInterpolator(AccelerateDecelerateInterpolator()).duration =
                timeelapsed*2
            imageView2.animate()
                .translationX((imageView2TranslationX + 60 * sin(cummulativeRotationAroundY))).setInterpolator(AccelerateDecelerateInterpolator()).duration =
                timeelapsed*2
            imageView3.animate()
                .translationX((imageView3TranslationX + 80 * sin(cummulativeRotationAroundY))).setInterpolator(AccelerateDecelerateInterpolator()).duration =
                timeelapsed*2
            imageView4.animate()
                .translationX((imageView4TranslationX + 80 * sin(cummulativeRotationAroundY))).setInterpolator(AccelerateDecelerateInterpolator()).duration =
                timeelapsed*2
            }
        }
        timestamp = event!!.timestamp.toFloat()
    }
}