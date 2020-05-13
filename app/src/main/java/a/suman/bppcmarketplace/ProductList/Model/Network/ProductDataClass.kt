package a.suman.bppcmarketplace.ProductList.Model.Network

data class ProductDataClass(
    val data: Products
)

data class Products(
    val page: Int,
    val pages: Int,
    val hasNext:Boolean,
    val hadPrev:Boolean,
    val objects:ListP
    )
data class ListP(
    val name:String,
    val basePrice:Int,
    val category: CategoryP,
    val seller:Seller
)

data class CategoryP(
    val name:String
)

data class Seller(
    val name:String,
    val username:String
)