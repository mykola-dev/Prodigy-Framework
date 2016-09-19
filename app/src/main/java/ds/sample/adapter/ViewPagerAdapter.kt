package ds.sample.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter
import ds.sample.view.ListFragment
import ds.sample.view.SimpleFragment

class ViewPagerAdapter(val a: FragmentActivity) : FragmentPagerAdapter(a.supportFragmentManager) {

    override fun getItem(position: Int): Fragment {
        return Fragment.instantiate(a, if (position % 2 == 0) ListFragment::class.java.name else SimpleFragment::class.java.name)
    }

    override fun getCount(): Int = 10

    /*override fun saveState(): Parcelable? {
        return null
    }*/
}