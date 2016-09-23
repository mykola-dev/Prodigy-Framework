package ds.sample.viewmodel

import android.databinding.ObservableField
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter
import ds.prodigy.Config
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent
import ds.sample.R
import ds.sample.adapter.ViewPagerAdapter
import ds.sample.view.ViewPagerActivity

@Config(component = ViewPagerActivity::class, layout = R.layout.activity_viewpager)
class ViewPagerPresenter() : Presenter<IComponent>() {

    val adapter = ObservableField<FragmentPagerAdapter>()

    override fun onAttach() {
        val a = component?.getActivity()
        a as FragmentActivity
        adapter.set(ViewPagerAdapter(a))    // adapter should be recreated on each reAttach since it holds the fragment instances
    }

}

