package a.suman.bppcmarketplace.ProductList.View

import a.suman.bppcmarketplace.ProductList.Adapter.ProductListAdapter
import a.suman.bppcmarketplace.ProductList.ViewModel.ProductsViewModel
import a.suman.bppcmarketplace.R
import a.suman.bppcmarketplace.Utils.fillCustomGradient
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.datatransport.runtime.logging.Logging.d
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProductListFragment : Fragment() {

    var animation:Job=Job()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillCustomGradient(view.findViewById(R.id.appbar_product_list), resources)
        Log.d("ProductList", "Blah")
        noInternetProduct.alpha = 0f
        val productsViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        val pagedListAdapter = ProductListAdapter()
        prodrecyclerview.adapter = pagedListAdapter
        prodrecyclerview.layoutManager = GridLayoutManager(context, 2)
        productsViewModel.pagedList.observe(this, Observer {
            pagedListAdapter.submitList(it)
        })
        productsViewModel.errorState.observe(this, Observer {

            if (it != null) {
                d("ErrorState", it.toString())
                if (it.isNotEmpty()) {
                    if (it[0] == "NoInternet") {

                        animation = MainScope().launch {
                            while (true) {
                                noInternetProduct.animate().alpha(0f).duration = 500
                                delay(500)
                                noInternetProduct.animate().alpha(1f).duration = 500
                                delay(600)
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        animation.cancel()
    }

}
