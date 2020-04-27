package a.suman.bppcmarketplace.Login.Model.Network


import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    private val retrofit: Retrofit
    val api: Api
        get() = retrofit.create(Api::class.java)

    companion object {
        private const val BASE_URL = "http://market.bits-dvm.org"

        @get:Synchronized
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field =
                        RetrofitClient()
                }
                return field
            }
            private set
    }

    init {

        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }
}