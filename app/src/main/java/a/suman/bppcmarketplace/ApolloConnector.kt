package a.suman.bppcmarketplace

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object ApolloConnector {
    private const val BASE_URL = "https://market.hedonhermdev.tech/api/graphql/"

    @Volatile
    private var INSTANCE: ApolloClient? = null

    fun setUpApollo(): ApolloClient {
        var instance = INSTANCE
        if (instance == null) {
            synchronized(this) {
                if (instance == null) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    val okHttpClient = OkHttpClient.Builder().addInterceptor {
                        val original = it.request()
                        val builder =
                            original.newBuilder().method(original.method, original.body)
                        builder.header("Authorization", "JWT ${TokenClass.token}")
                        it.proceed(builder.build())
                    }.build()
                    instance =
                        ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient)
                            .build()
                    INSTANCE = instance
                }
            }
        }
        return instance!!
    }

    @Synchronized
    fun invalidateApollo() {
        INSTANCE = null
    }
}
