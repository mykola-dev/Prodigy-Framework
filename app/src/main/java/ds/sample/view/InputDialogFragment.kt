package ds.sample.view

import android.content.DialogInterface
import ds.salo.BindingDialogFragment

class InputDialogFragment : BindingDialogFragment() {

    override fun getDialogButtons(): List<ButtonData> = listOf(
        ButtonData("OOOK", DialogInterface.BUTTON_POSITIVE),
        ButtonData("NOPE", DialogInterface.BUTTON_NEGATIVE)
    )

}