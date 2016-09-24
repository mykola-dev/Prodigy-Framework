package ds.prodigy

import android.support.annotation.LayoutRes
import ds.prodigy.component.IComponent
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Config(
    val component: KClass<out IComponent> = IComponent::class,
    @LayoutRes val layout: Int = 0
)
