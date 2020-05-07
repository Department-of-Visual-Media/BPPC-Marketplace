package a.suman.bppcmarketplace.Profile.View

import a.suman.bppcmarketplace.Profile.ViewModel.ProfileViewModel
import a.suman.bppcmarketplace.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.profileLiveData.observe(viewLifecycleOwner, Observer {
            Log.i("id", it.id.toString())

            nameTextView.setText(it.name)
            emailTextView.setText(it.email)
            hostelTextView.setText(it.hostel)
            contactNoTextView.setText(it.contactNo.toString())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        profileViewModel.dispose()
    }
}
