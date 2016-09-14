/*
 * Copyright (c) 2016. Deviant Studio
 */

package ds.sample.data

import android.content.Context
import ds.bindingtools.PrefDelegate
import ds.bindingtools.pref

class Prefs(ctx: Context) {
	init {
		PrefDelegate.init(ctx, "main_prefs")
	}

	var name by pref("Vasya")
	var age by pref(18)
	var sex by pref<String>(null)
}