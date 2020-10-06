package a.suman.bppcmarketplace.Profile.View

import a.suman.bppcmarketplace.Login.View.LoginView
import a.suman.bppcmarketplace.MainActivityViewModel
import a.suman.bppcmarketplace.Profile.ViewModel.ProfileViewModel
import a.suman.bppcmarketplace.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bppcmarketplace.ProfileWithProductsQuery
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(ProfileViewModel::class.java)
        profileViewModel.getCachedProfile()

        mainActivityViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(MainActivityViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productAdapter = ProductAdapter()
        postsRecylerView.layoutManager = LinearLayoutManager(context)
        postsRecylerView.adapter = productAdapter

        profileViewModel.profileLiveData.observe(viewLifecycleOwner, Observer {
            nameTextView.text = it.name
//            emailIcon.visibility = View.VISIBLE
//            emailTextView.text = it.email
            hostelTextView.text = it.hostel
//            phoneIcon.visibility = View.VISIBLE
//            contactNoTextView.text = it.contactNo.toString()
        })

        profileViewModel.productLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                productAdapter.setDataList(it as List<ProfileWithProductsQuery.Product>)
                if (it.isEmpty())
                    productTextView.visibility = View.VISIBLE
            }
        })

        profileViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                profileProgressBar.visibility = View.VISIBLE
                productTextView.visibility = View.GONE
            } else
                profileProgressBar.visibility = View.GONE
        })

        logOutButton.setOnClickListener {
            showConfirmationDialog()
        }



        mainActivityViewModel.loginToken.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                startActivity(Intent(activity, LoginView::class.java))
            }
        })
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(context)
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes")
            { _, _ -> mainActivityViewModel.logOut() }
            .setNegativeButton("No")
            { dialog, _ -> dialog.cancel() }
            .create().show()
    }
}
