package ds.prodigy;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Target;

import ds.prodigy.component.IComponent;

import static java.lang.annotation.ElementType.TYPE;

@Target(value = TYPE)
public @interface Config {

    Class<? extends IComponent> component() default IComponent.class;

    @LayoutRes int layout() default 0;
}
