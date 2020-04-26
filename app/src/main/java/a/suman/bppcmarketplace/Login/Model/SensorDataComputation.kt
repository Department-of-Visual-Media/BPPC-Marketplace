package a.suman.bppcmarketplace.Login.Model


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlin.math.sin


class SensorDataComputation {
    var timestamp: Float = 0f
    private var cummulativeRotationAroundX: Float = 0f
    private var cummulativeRotationAroundY: Float = 0f
    //val sensorDataX =MutableLiveData<Float>()
    //val sensorDataY = MutableLiveData<Float>()

    fun doInBackground(event:SensorEvent?) {
        if(timestamp!=0f){
        cummulativeRotationAroundX += event!!.values[0]*(event.timestamp-timestamp)/1000000000
        //sensorDataX.postValue(50* sin(cummulativeRotationAroundX))

        cummulativeRotationAroundY+=event.values[1]*(event.timestamp-timestamp)/1000000000
        //sensorDataY.postValue(50* sin(cummulativeRotationAroundY))
        }

        timestamp=event!!.timestamp.toFloat()
    }




}