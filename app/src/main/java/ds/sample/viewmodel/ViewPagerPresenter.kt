package ds.sample.viewmodel

import android.databinding.ObservableField
import android.support.v4.app.FragmentActivity
import ds.salo.IComponent
import ds.salo.Presenter
import ds.sample.adapter.ViewPagerAdapter
import ds.sample.util.L

class ViewPagerPresenter() : Presenter<IComponent>() {

    val adapter = ObservableField<ViewPagerAdapter>()

    override fun onCreate() {
        super.onCreate()
        val a = component?.getActivity()
        a as FragmentActivity
        adapter.set(ViewPagerAdapter(a))
    }

}

