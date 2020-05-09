package a.suman.bppcmarketplace.Cart

import a.suman.bppcmarketplace.Cart.RecentBids.View.RecentBids
import a.suman.bppcmarketplace.Cart.WishList.View.WishList
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> WishList()
            else -> RecentBids()
        }
    }
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "WishList"
            else -> "Cart"
        }
    }
}