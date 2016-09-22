package ds.sample.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter
import ds.sample.util.L
import ds.sample.view.ListFragment
import ds.sample.view.SimpleFragment

class ViewPagerAdapter(val a: FragmentActivity) : FragmentPagerAdapter(a.supportFragmentManager) {

    init {
        L.v("adapter initialized")
    }

    override fun getItem(position: Int): Fragment {
        L.v("adapter get item $position")
        return Fragment.instantiate(a, if (position % 2 == 0) ListFragment::class.java.name else SimpleFragment::class.java.name)
    }

    override fun getCount(): Int = 10

}