package a.suman.bppcmarketplace.ProductList.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bppcmarketplace.GetProductListQuery

class ProductListAdapter :
    PagedListAdapter<GetProductListQuery.Object, ProductViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        TODO()
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

    }


}

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view)
