package a.suman.bppcmarketplace.Upload.Model

import a.suman.bppcmarketplace.ApolloConnector
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.example.bppcmarketplace.CreateProductMutation
import io.reactivex.Observable

class UploadRepository {


    fun addProduct(
        name: String,
        description: String,
        expectedPrice: Int,
        isNegotiable: Boolean
    ): Observable<Response<CreateProductMutation.Data>> {
        return Rx2Apollo.from(
            ApolloConnector.setUpApollo().mutate(
                CreateProductMutation(
                    name = Input.fromNullable(name),
                    description = Input.fromNullable(description),
                    expectedPrice = Input.fromNullable(expectedPrice),
                    isNegotiable = Input.fromNullable(isNegotiable)
                )
            )
        )
    }
}