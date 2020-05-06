package a.suman.bppcmarketplace.Profile.Model

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApolloConnector {
    companion object {
        private const val BASE_URL = "https://market.bits-dvm.org/api/graphql/"

        fun setUpApollo(): ApolloClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            return ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build()
        }
    }
}