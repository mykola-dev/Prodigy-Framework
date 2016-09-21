package ds.prodigy

class Result<T : Any>(val cls: Class<T>, val callback: (T?) -> Unit) {
    var result: T? = null
    //var owner: Presenter? = null
}