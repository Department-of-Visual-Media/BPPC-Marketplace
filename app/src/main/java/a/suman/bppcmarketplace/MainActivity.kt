package a.suman.bppcmarketplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //to hide action Bar
        actionBar?.hide()
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }
        setContentView(R.layout.activity_main)
        //Setting up Navigation Bar
        var bottom_nav: ChipNavigationBar = findViewById(R.id.chipNavigationBar)


        fun openFragment(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        bottom_nav.setOnItemSelectedListener { id :Int->
            when (id) {
                R.id.products -> {
                    val fragment = ProductsFragment()
                    openFragment(fragment)
                }
                R.id.users -> {
                    val fragment = UsersFragment()
                    openFragment(fragment)
                }
                R.id.upload -> {
                    val fragment = UploadPicturesFragment()
                    openFragment(fragment)
                }
                R.id.profile -> {
                    val fragment = ProfileFragment()
                    openFragment(fragment)
                }
                R.id.chat -> {
                    val fragment = ChatFragment()
                    openFragment(fragment)
                }
            }
        }
    }
}




