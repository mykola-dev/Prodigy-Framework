package ds.prodigy

import ds.prodigy.component.IComponent

data class Configuration(val component: Class<out IComponent>, val presenter: Class<out Presenter<*>>, val layout: Int, val bindingVariable: Int)
