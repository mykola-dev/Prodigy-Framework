package ds.salo

data class Configuration(val bindingAware: Class<out BindingAware>,val presenter: Class<out Presenter>, val layout: Int, val bindingVariable: Int) {

    val key:String = "${bindingAware.simpleName}_${presenter.simpleName}"
}