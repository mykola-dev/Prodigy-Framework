package ds.prodigy

import android.support.annotation.LayoutRes

// framework helper. shouldn't use it directly in most cases
object Prodigy {

    var prodigyLogEnabled = true

    val configs = mutableListOf<Configuration>()
    internal val presentersCache = mutableMapOf<Long, Presenter<*>>()
    internal val unbindedQueue = mutableListOf<Presenter<*>>()

    fun init(block: Prodigy.() -> Unit) {
        block(this)
    }

    inline fun <reified P : Presenter<*>, reified C : IComponent> bind(@LayoutRes layout: Int, bindingVariable: Int = BR.presenter) {
        val config = configs.firstOrNull() { it.presenter == P::class.java }
        if (config == null)
            configs.add(Configuration(C::class.java, P::class.java, layout, bindingVariable))
        else
            throw IllegalStateException("Presenter already configured")

    }

    // when BindingAware exists. do not use it directly!
    internal fun provide(cCls: Class<out IComponent>, presenterId: Long = 0): Presenter<*> {
        log("provide presenter for ${cCls.simpleName}")
        var p: Presenter<*>?

        if (presenterId != 0L) {
            p = peekDelayed(presenterId)
            if (p != null) {
                log("found unbinded presenter!")
            } else if (presentersCache.containsKey(presenterId)) {
                p = presentersCache[presenterId]
                log("got from cache id=$presenterId")
            } else {
                throw IllegalStateException("Presenter not found!")
            }
        } else {
            val config = getConfigByComponent(cCls)
            p = config.presenter.newInstance() as Presenter<*>
            log("create new ${p.javaClass.simpleName} with default constructor")
            put(p)
        }

        return p!!
    }

    fun put(presenter: Presenter<*>) {
        log("put presenter to ${presenter.id}")
        presentersCache.put(presenter.id, presenter)
    }

    fun putDelayed(presenter: Presenter<*>) {
        log("put presenter to the queue")
        unbindedQueue.add(presenter)
    }

    internal fun peekDelayed(id: Long): Presenter<*>? {
        log("lookup queued presenter")
        val p = unbindedQueue.firstOrNull { it.id == id }
        if (p != null) {
            unbindedQueue.remove(p)
            put(p)
        }
        return p
    }

    fun isAwaiting(p: Presenter<*>): Boolean {
        return p in unbindedQueue
    }

    fun remove(p: Presenter<*>) {
        presentersCache.remove(p.id)
        log("presenter ${p.id} has been removed from cache")
    }

    internal fun getConfigByComponent(cCls: Class<out IComponent>): Configuration = configs.single { it.component == cCls }

    internal fun getConfig(pCls: Class<out Presenter<*>>): Configuration = configs.single { it.presenter == pCls }

    fun getDiagnostics(): String {
        var result = "cache size=${presentersCache.size}\n"
        result += presentersCache.values.map { it.javaClass.simpleName }.joinToString()
        result += "\n\nunbinded size=${unbindedQueue.size}"
        result += unbindedQueue.map { it.javaClass }.joinToString()
        return result
    }


}