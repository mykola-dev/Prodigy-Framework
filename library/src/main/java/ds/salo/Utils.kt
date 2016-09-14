package ds.salo

import rx.Observable


fun <T> Observable<T>.respectLifeCycle() : Observable<T> = this // todo