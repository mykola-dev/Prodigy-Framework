package ds.sample.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import ds.prodigy.component.BindingActivity
import ds.sample.BR
import ds.sample.R
import ds.sample.adapter.ViewPagerAdapter
import ds.sample.util.toast
import ds.sample.viewmodel.ViewPagerPresenter

class EmptyActivity : BindingActivity()
class ViewPagerActivity : BindingActivity()

class MainActivity : BindingActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

class TestActivity : BindingActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.test, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

class CustomComponentActivity : BindingActivity(), CustomComponent {
    override fun showToast(text: String) {
        toast(text)
    }
}

class ToolBarActivity : BindingActivity() {

}

class NativeViewPagerActivity : AppCompatActivity() {

    val binding: ViewDataBinding by lazy { DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_viewpager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val p = ViewPagerPresenter()
        binding.setVariable(BR.presenter, p)
        p.adapter.set(ViewPagerAdapter(this))
    }

    override fun onDestroy() {
        //binding.unbind()
        super.onDestroy()
    }
}