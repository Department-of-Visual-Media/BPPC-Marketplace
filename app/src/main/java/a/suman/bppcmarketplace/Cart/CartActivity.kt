package a.suman.bppcmarketplace.Cart

import a.suman.bppcmarketplace.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        val sectionsPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabLayout)
        tabs.setupWithViewPager(viewPager)
    }
}