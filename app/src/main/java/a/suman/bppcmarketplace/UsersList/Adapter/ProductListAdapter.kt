package a.suman.bppcmarketplace.UsersList.Adapter

import a.suman.bppcmarketplace.ProductList.Adapter.DiffUtilCallbackUser
import a.suman.bppcmarketplace.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bppcmarketplace.GetUserListQuery
import com.google.android.datatransport.runtime.logging.Logging.d


class UserListAdapter :
    PagedListAdapter<GetUserListQuery.Object, ProductViewHolder>(DiffUtilCallbackUser()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_user_list, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.name.text = getItem(position)!!.name
        d("UserDataName", getItem(position)!!.name)
        d("UserDataUsername", getItem(position)!!.username)
        holder.username.text = getItem(position)!!.username
        if (getItem(position)!!.avatar.isNullOrBlank()) {
            Glide.with(holder.itemView).load(getItem(position)!!.avatar).into(holder.imageView)
        }
    }


}

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView = view.findViewById<ImageView>(R.id.imageView7)
    val name = view.findViewById<TextView>(R.id.textView6)
    val username = view.findViewById<TextView>(R.id.textView7)
}
