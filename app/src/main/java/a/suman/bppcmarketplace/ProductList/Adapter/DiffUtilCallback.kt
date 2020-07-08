package a.suman.bppcmarketplace.ProductList.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.bppcmarketplace.GetProductListQuery

class DiffUtilCallback : DiffUtil.ItemCallback<GetProductListQuery.Object>() {
    override fun areContentsTheSame(
        oldItem: GetProductListQuery.Object,
        newItem: GetProductListQuery.Object
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: GetProductListQuery.Object,
        newItem: GetProductListQuery.Object
    ): Boolean {
        return oldItem.id == newItem.id
    }
}