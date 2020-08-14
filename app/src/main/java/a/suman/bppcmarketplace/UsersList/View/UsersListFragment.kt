package a.suman.bppcmarketplace.UsersList.View

import a.suman.bppcmarketplace.R
import a.suman.bppcmarketplace.UsersList.Adapter.UserListAdapter
import a.suman.bppcmarketplace.UsersList.ViewModel.UsersViewModel
import a.suman.bppcmarketplace.Utils.fillCustomGradient
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.datatransport.runtime.logging.Logging
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.fragment_users_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UsersListFragment : Fragment() {

    var animation:Job= Job()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_users_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillCustomGradient(view.findViewById(R.id.appbar_users_list), resources)
        noInternetUser.alpha = 0f
        val usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        val pagedListAdapter = UserListAdapter()
        userrecyclerview.adapter = pagedListAdapter
        userrecyclerview.layoutManager = LinearLayoutManager(context)
        usersViewModel.pagedList.observe(this as LifecycleOwner, Observer {

            pagedListAdapter.submitList(it)
        })
        usersViewModel.errorState.observe(this as LifecycleOwner, Observer {

            if (it != null) {
                Logging.d("ErrorState", it.toString())
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
