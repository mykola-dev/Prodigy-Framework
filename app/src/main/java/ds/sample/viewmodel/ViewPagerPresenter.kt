package ds.sample.viewmodel

import android.databinding.ObservableField
import android.support.v4.app.FragmentActivity
import ds.salo.Presenter
import ds.sample.adapter.ViewPagerAdapter
import ds.sample.util.L

class ViewPagerPresenter() : Presenter() {

    val adapter = ObservableField<ViewPagerAdapter>()

    override fun onCreate() {
        L.v("ViewPagerPresenter created")
        val a = component?.getActivity()
        a as FragmentActivity
        adapter.set(ViewPagerAdapter(a))
    }

    override fun onAttach() {
        L.v("ViewPagerPresenter attached")
    }

    override fun onDetach() {
        L.v("ViewPagerPresenter detached")
    }

    override fun onDestroy() {
        L.v("ViewPagerPresenter destroyed")
    }


}

