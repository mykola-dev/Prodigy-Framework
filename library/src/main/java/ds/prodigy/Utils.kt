package ds.prodigy

internal fun log(s: String) = if (Prodigy.prodigyLogEnabled) println(s) else Unit