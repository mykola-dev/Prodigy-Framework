package ds.sample.view

import android.view.Menu
import ds.prodigy.component.IComponent

interface MenuComponent : IComponent {

    fun toggleNotificationsIcon(menu: Menu, enable:Boolean)

}