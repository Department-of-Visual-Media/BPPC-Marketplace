package a.suman.bppcmarketplace.Profile.View

import a.suman.bppcmarketplace.Login.View.LoginView
import a.suman.bppcmarketplace.Login.ViewModel.LoginViewModel
import a.suman.bppcmarketplace.Profile.ViewModel.ProfileViewModel
import a.suman.bppcmarketplace.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(ProfileViewModel::class.java)
        profileViewModel.fetchProfile()

        loginViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        ).get(LoginViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.profileLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("id", it.id.toString())

            nameTextView.text = it.name
            emailTextView.text = it.email
            hostelTextView.text = it.hostel
            contactNoTextView.text = it.contactNo.toString()
        })

        logOutButton.setOnClickListener {
            showConfirmationDialog()
        }


        loginViewModel.loginToken.observe(this, Observer {
            if (it == null) {
                startActivity(Intent(activity, LoginView::class.java))
            }
        })

        wishlistButton.setOnClickListener {
            Toast.makeText(context, "WishList", Toast.LENGTH_LONG).show()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        profileViewModel.dispose()
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(context)
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes")
            { _, _ -> loginViewModel.logOut() }
            .setNegativeButton("No")
            { dialog, _ -> dialog.cancel() }
            .create().show()
    }
}
