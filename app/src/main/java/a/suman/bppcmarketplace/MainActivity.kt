package a.suman.bppcmarketplace

import a.suman.bppcmarketplace.ProductList.View.ProductsListFragment
import a.suman.bppcmarketplace.Profile.View.ProfileFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottom_nav: ChipNavigationBar = findViewById(R.id.chipNavigationBar)

        openFragment(ProductsListFragment())
        bottom_nav.setItemSelected(R.id.products)

        bottom_nav.setOnItemSelectedListener { id :Int->
            when (id) {
                R.id.products -> {
                    val fragment =
                        ProductsListFragment()
                    openFragment(fragment)
                }
                R.id.users -> {
                    val fragment = UsersListFragment()
                    openFragment(fragment)
                }
                R.id.upload -> {
                    val fragment = UploadFragment()
                    openFragment(fragment)
                }
                R.id.profile -> {
                    val fragment =
                        ProfileFragment()
                    openFragment(fragment)
                }
                R.id.chat -> {
                    val fragment = ChatListFragment()
                    openFragment(fragment)
                }
            }
        }
    }

    fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}




