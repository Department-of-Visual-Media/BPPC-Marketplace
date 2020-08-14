package a.suman.bppcmarketplace.ProductList.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.bppcmarketplace.GetUserListQuery

class DiffUtilCallbackUser : DiffUtil.ItemCallback<GetUserListQuery.Object>() {
    override fun areContentsTheSame(
        oldItem: GetUserListQuery.Object,
        newItem: GetUserListQuery.Object
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: GetUserListQuery.Object,
        newItem: GetUserListQuery.Object
    ): Boolean {
        return oldItem.username == newItem.username
    }
}