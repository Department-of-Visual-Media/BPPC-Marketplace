package a.suman.bppcmarketplace.UsersList.View

import a.suman.bppcmarketplace.R
import a.suman.bppcmarketplace.Utils.fillCustomGradient
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        animation= MainScope().launch {
            while(true){noInternetUser.animate().alpha(0f).duration = 500
                delay(500)
                noInternetUser.animate().alpha(1f).duration = 500
                delay(600)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        animation.cancel()
    }

}
