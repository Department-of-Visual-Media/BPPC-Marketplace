package a.suman.bppcmarketplace

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApolloConnector {
    companion object {
        private const val BASE_URL = "https://market.bits-dvm.org/api/graphql/"

        @Volatile
        private var INSTANCE: ApolloClient? = null


        fun setUpApollo(): ApolloClient {
            val instance = INSTANCE
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
                        val temp =
                            ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient)
                                .build()
                        INSTANCE = temp
                        return temp
                    }
                }
            }
            return instance!!
        }

        @Synchronized
        fun invalidateApollo(){
            INSTANCE = null
        }
    }
}