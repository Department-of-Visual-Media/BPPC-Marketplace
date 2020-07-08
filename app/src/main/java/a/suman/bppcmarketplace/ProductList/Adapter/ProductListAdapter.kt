package a.suman.bppcmarketplace.ProductList.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bppcmarketplace.GetProductListQuery

class ProductListAdapter :
    PagedListAdapter<GetProductListQuery.Object, ProductViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view)
