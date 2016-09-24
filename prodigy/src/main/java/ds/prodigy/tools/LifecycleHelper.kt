package ds.prodigy.tools

import ds.prodigy.tools.L
import ds.prodigy.Presenter
import rx.Observable

// wait until onAttach and unsubscribe on onDestroy
fun <T> Observable<T>.respectLifeCycle(p: Presenter<*>): Observable<T> {
    val lifecycleSignal: Observable<LifecycleEvent> = p.lifecycleSignal
    return this
        .takeUntil(getSignal(lifecycleSignal, LifecycleEvent.DESTROY))
        .delay({ getSignal(lifecycleSignal, LifecycleEvent.ATTACH) })

}

internal fun <T> getSignal(lifecycleSignal: Observable<T>, event: T): Observable<T> {
    return lifecycleSignal
        .takeFirst { e -> e == event }
        .doOnNext { L.w("lifecycle", "signalled $event") }
}

enum class LifecycleEvent {
    CREATE, DESTROY, ATTACH, DETACH
}