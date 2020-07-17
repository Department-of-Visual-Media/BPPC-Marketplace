package a.suman.bppcmarketplace.ProductList.Adapter

import a.suman.bppcmarketplace.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bppcmarketplace.GetProductListQuery
import com.google.android.datatransport.runtime.logging.Logging.d


class ProductListAdapter :
    PagedListAdapter<GetProductListQuery.Object, ProductViewHolder>(DiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_prod_list, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.title.text = getItem(position)!!.name
        holder.price.text = getItem(position)!!.expectedPrice.toString()
        holder.decription.text = getItem(position)!!.description
        if (getItem(position)!!.images != null) {
            if (getItem(position)!!.images!!.isNotEmpty()) {
                d("ViewModel", "Image ${getItem(position)!!.images!![0]}")
                Glide.with(holder.itemView).load(getItem(position)!!.images!![0]).into(holder.image)
            }
        }
    }


}

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val price: TextView = view.findViewById(R.id.textView3)
    val title: TextView = view.findViewById(R.id.textView2)
    val decription: TextView = view.findViewById(R.id.textView4)
    val image: ImageView = view.findViewById(R.id.imageView5)
}
