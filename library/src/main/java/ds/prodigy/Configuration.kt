package ds.prodigy

data class Configuration(val component: Class<out IComponent>, val presenter: Class<out Presenter<*>>, val layout: Int, val bindingVariable: Int)