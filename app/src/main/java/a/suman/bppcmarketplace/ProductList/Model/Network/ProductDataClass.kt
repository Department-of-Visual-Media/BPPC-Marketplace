package a.suman.bppcmarketplace.ProductList.Model.Network

data class ProductDataClass(
    val name: String,
    val basePrice: Int?,
    val description: String?,
    val category: CategoryDataClass?,
    val sold: Boolean,
    val visible: Boolean?

) {

    constructor(name: String, sold: Boolean) : this(name, null, null, null, sold, null)
}

data class CategoryDataClass(
    var name: String,
    var products: ProductDataClass?
)