package a.suman.bppcmarketplace.Upload.View

import a.suman.bppcmarketplace.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UploadAdapter() : RecyclerView.Adapter<UploadAdapter.ViewHolder>() {
    private var list: List<Int> = listOf(1, 2, 3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_image_upload, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
        holder.itemView.setOnClickListener { }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(viewType: Int) {

        }


    }
}