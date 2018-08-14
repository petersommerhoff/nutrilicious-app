package com.petersommerhoff.nutrilicious.view.common

import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

/**
 * @author Peter Sommerhoff
 */
fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: KClass<T>): T {
  return ViewModelProviders.of(this).get(modelClass.java)
}
