package a.suman.bppcmarketplace

import a.suman.bppcmarketplace.ChatList.View.ChatListFragment
import a.suman.bppcmarketplace.ProductList.View.ProductListFragment
import a.suman.bppcmarketplace.Profile.View.ProfileFragment
import a.suman.bppcmarketplace.Upload.View.UploadFragment
import a.suman.bppcmarketplace.UsersList.View.UsersListFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.fragment)
        bottomNavBar.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}




