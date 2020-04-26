package a.suman.bppcmarketplace.Login.View

import a.suman.bppcmarketplace.Login.ViewModel.LoginViewModel
import a.suman.bppcmarketplace.MainActivity
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
    var cummulativeRotationAroundX=0f
    var cummulativeRotationAroundY=0f
    var imageView2TranslationX=0
    var imageView2TranslationY=0
    var viewTranslationX=0
    var viewTranslationY=0
    var girlTranslationX=0
    var girlTranslationY=0
    var loaderTranslationX=0
    var loaderTranslationY=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        var imageView2TranslationX=imageView2.translationX
        var imageView2TranslationY=imageView2.translationY
        var viewTranslationX=view.translationX
        var viewTranslationY=view.translationY
        var girlTranslationX=girl.translationX
        var girlTranslationY=girl.translationY
        var loaderTranslationX=loader.translationX
        var loaderTranslationY=loader.translationY

        sensormanager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyro=sensormanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(LoginViewModel::class.java)
        sign_in_button.setOnClickListener {
            startActivityForResult(
                loginViewModel.initGoogleSignIn(),
                1
            )
            sign_in_button.cardElevation=0f
            sign_in_button.isEnabled=false
        }
        if(gyro==null){
            Toast.makeText(this, "Gyroscope Not Supported", Toast.LENGTH_LONG).show()
        }else{
            //Toast.makeText(this, "Ready to Bang Bang $gyro", Toast.LENGTH_LONG).show()
        }
        loginViewModel.TokenLiveData.observe(this, Observer {
                if(it!=null){}
                    //startActivity(Intent(this, MainActivity::class.java))
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


    override fun onResume() {
        super.onResume()
        sensormanager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL)

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        girl.visibility= View.GONE
        loader.visibility=View.VISIBLE
        progressBar.visibility=View.VISIBLE
        loginViewModel.onResultFromActivity(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int){
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(timestamp!=0f){
            var timeelapsed=(event!!.timestamp.toFloat() - timestamp)/1000000000
            var rotationAroundX: Float = event.values[0]
            var rotationAroundY:Float =event.values[1]
            cummulativeRotationAroundX+=rotationAroundX*timeelapsed
            cummulativeRotationAroundY+=rotationAroundY*timeelapsed

            imageView2.translationY=(imageView2TranslationY+ 50*sin(cummulativeRotationAroundX))
            imageView2.translationX=(imageView2TranslationX+ 50*sin(cummulativeRotationAroundY))
            view.translationY=(viewTranslationY+50* sin(cummulativeRotationAroundX))
            view.translationX=(viewTranslationX+50*sin(cummulativeRotationAroundY))
            girl.translationY=(girlTranslationY+50*sin(cummulativeRotationAroundX))
            girl.translationX=(girlTranslationX+50*sin(cummulativeRotationAroundY))
            loader.translationY=(loaderTranslationY+50*sin(cummulativeRotationAroundX))
            loader.translationX=(loaderTranslationX+50*sin(cummulativeRotationAroundY))
        }
        timestamp=event!!.timestamp.toFloat()
    }


    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.clearDisposables()
    }

    companion object {
        const val TAG: String = "LoginView"
    }
}