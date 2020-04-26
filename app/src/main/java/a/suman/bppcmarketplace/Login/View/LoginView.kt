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
    var cummulativeRotationAroundX:Float=0f
    var cummulativeRotationAroundY:Float=0f

    var imageView2TranslationX:Float=0f
    var imageView2TranslationY:Float=0f
    var viewTranslationX:Float=0f
     var viewTranslationY:Float=0f
     var girlTranslationX:Float=0f
     var girlTranslationY:Float=0f
     var loaderTranslationX:Float=0f
     var loaderTranslationY:Float=0f
    var imageViewTranslationX:Float=0f
    var imageViewTranslationY:Float=0f

    companion object {
        const val TAG: String = "LoginView"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        imageView2TranslationX=imageView2.translationX
        imageView2TranslationY=imageView2.translationY
        imageViewTranslationX=imageView.translationX
        imageViewTranslationY=imageView.translationX
        viewTranslationX=view.translationX
        viewTranslationY=view.translationY
        girlTranslationX=girl.translationX
        girlTranslationY=girl.translationY
        loaderTranslationX=loader.translationX
        loaderTranslationY=loader.translationY

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

        /*loginViewModel.transformXAddition.observe(this, Observer {
            imageView2.animate().translationY(imageView2TranslationY+it).duration=100
            view.animate().translationY(viewTranslationY+it).duration=100
            girl.animate().translationY(girlTranslationY+it).duration=100
            loader.animate().translationY(loaderTranslationY+it).duration=100
        })
        loginViewModel.transformYAddition.observe(this, Observer {
            imageView2.animate().translationX(imageView2TranslationX+it).duration=100
            view.animate().translationX(viewTranslationX+it).duration=100
            girl.animate().translationX(girlTranslationX+it).duration=100
            loader.animate().translationX(loaderTranslationX+it).duration=100
        })*/
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        girl.visibility= View.GONE
        loader.visibility=View.VISIBLE
        progressBar.visibility=View.VISIBLE
        loginViewModel.onResultFromActivity(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        sensormanager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.clearDisposables()
        sensormanager.unregisterListener(this)
    }



    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        //loginViewModel.SensorDataComputation(event)
            if(timestamp!=0f){
            cummulativeRotationAroundX += event!!.values[0] * (event.timestamp - timestamp) / 1000000000
            cummulativeRotationAroundY += event.values[1] * (event.timestamp - timestamp) / 1000000000
            }
            timestamp=event!!.timestamp.toFloat()
        imageView2.animate().translationY(imageView2TranslationY+50*sin(cummulativeRotationAroundX)).duration=500
        imageView.animate().translationY(imageViewTranslationY+25*sin(cummulativeRotationAroundX)).duration=500
        view.animate().translationY(viewTranslationY+50*sin(cummulativeRotationAroundX)).duration=500
        girl.animate().translationY(girlTranslationY+50*sin(cummulativeRotationAroundX)).duration=500
        loader.animate().translationY(loaderTranslationY+50*sin(cummulativeRotationAroundX)).duration=500

        imageView2.animate().translationX(imageView2TranslationX+ 50*sin(cummulativeRotationAroundY)).duration=500
        imageView.animate().translationX((imageViewTranslationX+25*sin(cummulativeRotationAroundY))).duration=500
        view.animate().translationX(viewTranslationX+50*sin(cummulativeRotationAroundY)).duration=500
        girl.animate().translationX(girlTranslationX+50*sin(cummulativeRotationAroundY)).duration=500
        loader.animate().translationX(loaderTranslationX+50*sin(cummulativeRotationAroundY)).duration=500



    }
}