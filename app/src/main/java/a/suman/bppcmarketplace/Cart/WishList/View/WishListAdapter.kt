package a.suman.bppcmarketplace.Cart.WishList.View

import a.suman.bppcmarketplace.Cart.WishList.Model.WishListClass
import a.suman.bppcmarketplace.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishListAdapter:RecyclerView.Adapter<WishListAdapter.ViewHolder>() {

    var list: MutableList<WishListClass> = ArrayList()
    override fun onCreateViewHolder(p: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(p.context).inflate(R.layout.wish_list_row,p,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


    fun setData(list:MutableList<WishListClass>){
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }
}
