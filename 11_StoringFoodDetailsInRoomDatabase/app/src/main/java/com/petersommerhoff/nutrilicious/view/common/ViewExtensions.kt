package com.petersommerhoff.nutrilicious.view.common

import android.arch.lifecycle.*
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlin.reflect.KClass

/**
 * @author Peter Sommerhoff
 */
fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: KClass<T>): T {
  return ViewModelProviders.of(this).get(modelClass.java)
}

fun <T : ViewModel> Fragment.getViewModel(modelClass: KClass<T>): T {
  return ViewModelProviders.of(this).get(modelClass.java)
}

fun AppCompatActivity.replaceFragment(viewGroupId: Int, fragment: Fragment) {
  supportFragmentManager.beginTransaction()
      .replace(viewGroupId, fragment)
      .commit()
}

fun AppCompatActivity.addFragmentToState(@IdRes containerViewId: Int, fragment: Fragment, tag: String) {
  supportFragmentManager.beginTransaction().add(containerViewId, fragment, tag).commit()
}

fun AppCompatActivity.toast(msg: String) {
  Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
