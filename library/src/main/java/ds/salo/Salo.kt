package ds.salo

import android.support.annotation.LayoutRes

// framework helper. shouldn't use it directly in most cases
object Salo {

    val configs = mutableListOf<Configuration>()
    val presentersCache = mutableMapOf<String, Presenter>()
    val unbindedQueue = mutableMapOf<Configuration, Presenter>()

    fun init(block: Salo.() -> Unit) {
        block(this)
    }

    inline fun <reified BA : BindingAware, reified P : Presenter> bind(@LayoutRes layout: Int, bindingVariable: Int = BR.presenter) {
        configs.add(Configuration(BA::class.java, P::class.java, layout, bindingVariable))
    }

    // when BindingAware exists. do not use it directly!
    internal fun provide(bindingAware: BindingAware, presenterId: Long = 0): Presenter {
        val config = getConfig(bindingAware)
        println("provide presenter ${config.presenter.simpleName}")
        var p: Presenter?

        if (presenterId != 0L) {
            val key = key(config, presenterId)
            p = presentersCache[key]
            println("got from cache $key")
        } else {
            p = unbindedQueue[config]
            if (p != null) {
                println("found unbinded presenter!")
                unbindedQueue.remove(config)
            } else {
                p = config.presenter.newInstance()
                println("create new ${p?.javaClass?.simpleName} with default constructor")
            }
            put(config, p!!)
        }

        return p!!
    }

    fun key(config: Configuration, id: Long) = "${config.bindingAware.simpleName}_${config.presenter.simpleName}_$id"

    fun put(config: Configuration, presenter: Presenter) {
        val key = key(config, presenter.id)
        println("put presenter to $key")
        presentersCache.put(key, presenter)
    }

    fun putDelayed(config: Configuration, presenter: Presenter) {
        println("put presenter to the queue")
        unbindedQueue.put(config, presenter)
    }

    fun isAwaiting(p: Presenter): Boolean {
        val config = getConfig(p.javaClass)
        return config in unbindedQueue
    }

    fun remove(p: Presenter) {
        val config = getConfig(p.javaClass)
        val key = key(config, p.id)
        presentersCache.remove(key)
        println("presenter $key has been removed from cache")
    }

    internal fun getConfig(ba: BindingAware): Configuration = configs.first { it.bindingAware == ba.javaClass }

    internal fun getConfig(pCls: Class<out Presenter>): Configuration = configs.first { it.presenter == pCls }


}