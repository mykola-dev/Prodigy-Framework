package ds.salo

import android.support.annotation.LayoutRes

object Salo {
    val configs = mutableListOf<Configuration>()

    val presentersCache = mutableMapOf<String, Presenter>()

    fun init(block: Salo.() -> Unit) {
        block(this)
    }

    inline fun <reified BA : BindingAware, reified P : Presenter> bind(@LayoutRes layout: Int, bindingVariable: Int = BR.presenter) {
        configs.add(Configuration(BA::class.java, P::class.java, layout, bindingVariable))
    }

    // when BindingAware exists. do not use it directly!
    internal fun provide(ba: BindingAware): Presenter {
        println("get presenter for BindingAware (${ba.javaClass.simpleName})")
        val config = getConfig(ba)
        return provide(config)
    }

    private fun provide(config: Configuration): Presenter {
        val key = config.key
        var p = presentersCache[key]
        if (p == null) {
            p = config.presenter.newInstance()
            println("create new ${p?.javaClass?.simpleName}")
            put(config, p)
        } else {
            println("got from cache")
        }

        return p!!
    }

    fun put(config: Configuration, presenter: Presenter) {
        val pCls = presenter.javaClass
        val key = config.key
        println("put presenter to $key")
        presentersCache.put(key, presenter)
    }

    fun isExist(key: String): Boolean {
        return key in presentersCache
    }

    fun remove(config: Configuration) {
        val key = config.key
        println("remove key $key from cache")
        presentersCache.remove(key)
    }

    fun remove(p: Presenter) {
        val config = getConfig(p)
        remove(config)
    }

    internal fun getConfig(ba: BindingAware): Configuration = configs.first { it.bindingAware == ba.javaClass }

    internal fun getConfig(p: Presenter): Configuration = configs.first { it.presenter == p.javaClass }

}