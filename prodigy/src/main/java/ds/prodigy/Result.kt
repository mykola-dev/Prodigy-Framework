package ds.prodigy

class Result<T : Any>(var owner: Presenter<*>, val resultType: Class<T>, val callback: (T?) -> Unit) {
    var result: T? = null

}