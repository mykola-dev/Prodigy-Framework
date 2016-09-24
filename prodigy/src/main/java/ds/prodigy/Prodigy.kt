package ds.prodigy

import android.support.annotation.LayoutRes
import ds.prodigy.component.IComponent
import ds.prodigy.tools.L

// framework helper. shouldn't use it directly in most cases
object Prodigy {
    val TAG = "Prodigy"

    val configs = mutableListOf<Configuration>()
    internal val presentersCache = mutableMapOf<Long, Presenter<*>>()
    internal val unbindedQueue = mutableListOf<Presenter<*>>()

    // manual initialization
    fun init(block: Prodigy.() -> Unit) {
        block(this)
    }

    // auto initialization
    fun init(configs:List<Configuration>) {
        this.configs.addAll(configs)
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
        L.i(TAG, "provide presenter for ${cCls.simpleName}")
        var p: Presenter<*>?

        if (presenterId != 0L) {
            p = peekDelayed(presenterId)
            if (p != null) {
                L.i(TAG, "found unbinded presenter!")
            } else if (presentersCache.containsKey(presenterId)) {
                p = presentersCache[presenterId]
                L.i(TAG, "got from cache id=$presenterId")
            } else {
                throw IllegalStateException("Presenter not found!")
            }
        } else {
            val config = getConfigByComponent(cCls)
            p = config.presenter.newInstance() as Presenter<*>
            L.i(TAG, "create new ${p.javaClass.simpleName} with default constructor")
            put(p)
        }

        return p!!
    }

    fun put(presenter: Presenter<*>) {
        L.i(TAG, "put(${presenter.id}, ${presenter.javaClass.simpleName})")
        presentersCache.put(presenter.id, presenter)
    }

    fun putDelayed(presenter: Presenter<*>) {
        L.i(TAG, "putDelayed(${presenter.id}, ${presenter.javaClass.simpleName})")
        unbindedQueue.add(presenter)
    }

    internal fun peekDelayed(id: Long): Presenter<*>? {
        L.i(TAG, "lookup queued id=$id")
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
        L.w(TAG, "remove(${p.id}, ${p.javaClass.simpleName})")
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