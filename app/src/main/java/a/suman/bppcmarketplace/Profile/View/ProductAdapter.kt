package a.suman.bppcmarketplace.Profile.View

import a.suman.bppcmarketplace.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bppcmarketplace.ProfileWithProductsQuery
import kotlinx.android.synthetic.main.row_profile_recyler_view.view.*

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var list: List<ProfileWithProductsQuery.Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_profile_recyler_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setDataList(list: List<ProfileWithProductsQuery.Product>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(product: ProfileWithProductsQuery.Product) {
            itemView.productNameTextView.text = product.name
            itemView.productDescriptionTextView.text=product.description
            itemView.productPriceTextView.text="Rs${product.expectedPrice}"

        }
    }
}