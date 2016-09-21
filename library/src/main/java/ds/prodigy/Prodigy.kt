package ds.prodigy

import android.support.annotation.LayoutRes

// framework helper. shouldn't use it directly in most cases
object Prodigy {

    val configs = mutableListOf<Configuration>()
    internal val presentersCache = mutableMapOf<String, Presenter<*>>()
    internal val unbindedQueue = mutableMapOf<Configuration, Presenter<*>>()

    fun init(block: Prodigy.() -> Unit) {
        block(this)
    }

    inline fun <reified C : IComponent, reified P : Presenter<*>> bind(@LayoutRes layout: Int, bindingVariable: Int = BR.presenter) {
        val config = configs.firstOrNull() { it.presenter == P::class.java }
        if (config == null)
            configs.add(Configuration(C::class.java, P::class.java, layout, bindingVariable))
        else
            throw IllegalStateException("Presenter already configured")

    }

    // when BindingAware exists. do not use it directly!
    internal fun provide(component: IComponent, presenterId: Long = 0): Presenter<*> {
        val config = getConfig(component)
        println("provide presenter ${config.presenter.simpleName}")
        var p: Presenter<*>?

        val key = key(config, presenterId)
        if (presenterId != 0L && presentersCache.containsKey(key)) {
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

    fun key(config: Configuration, id: Long) = "${config.component.simpleName}_${config.presenter.simpleName}_$id"

    fun put(config: Configuration, presenter: Presenter<*>) {
        val key = key(config, presenter.id)
        println("put presenter to $key")
        presentersCache.put(key, presenter)
    }

    fun putDelayed(config: Configuration, presenter: Presenter<*>) {
        println("put presenter to the queue")
        unbindedQueue.put(config, presenter)
    }

    fun isAwaiting(p: Presenter<*>): Boolean {
        val config = getConfig(p.javaClass)
        return config in unbindedQueue
    }

    fun remove(p: Presenter<*>) {
        val config = getConfig(p.javaClass)
        val key = key(config, p.id)
        presentersCache.remove(key)
        println("presenter $key has been removed from cache")
    }

    internal fun getConfig(component: IComponent): Configuration = configs.first { it.component == component.javaClass }

    internal fun getConfig(pCls: Class<out Presenter<*>>): Configuration = configs.first { it.presenter == pCls }

    fun getDiagnostics(): String {
        var result = "cache size=${presentersCache.size}\n"
        result += presentersCache.keys.joinToString()
        result += "\nunbinded size=${unbindedQueue.size}"
        result += unbindedQueue.keys.joinToString()
        return result
    }


}