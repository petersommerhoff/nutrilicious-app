package com.petersommerhoff.nutrilicious.view.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView
import com.petersommerhoff.nutrilicious.R
import com.petersommerhoff.nutrilicious.view.common.addFragmentToState
import com.petersommerhoff.nutrilicious.view.common.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

private const val SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT"

class MainActivity : AppCompatActivity() {

  private lateinit var searchFragment: SearchFragment

  private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
    when (it.itemId) {
      R.id.navigation_home -> {
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_my_foods -> {
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    recoverOrBuildSearchFragment()
    replaceFragment(R.id.mainView, searchFragment)

    navigation.setOnNavigationItemSelectedListener(navListener)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.search_menu, menu)

    // Associate searchable configuration with the SearchView
    val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
    (menu.findItem(R.id.search).actionView as SearchView).apply {
      setSearchableInfo(searchManager.getSearchableInfo(componentName))
    }

    return true
  }

  override fun onNewIntent(intent: Intent) {
    if (intent.action == Intent.ACTION_SEARCH) {
      val query = intent.getStringExtra(SearchManager.QUERY)
      searchFragment.updateListFor(query)
    }
  }

  private fun recoverOrBuildSearchFragment() {
    val fragment = supportFragmentManager
        .findFragmentByTag(SEARCH_FRAGMENT_TAG) as? SearchFragment
    if (fragment == null) {
      setUpSearchFragment()
    } else {
      searchFragment = fragment
    }
  }

  private fun setUpSearchFragment() {
    searchFragment = SearchFragment()
    addFragmentToState(R.id.mainView, searchFragment, SEARCH_FRAGMENT_TAG)
  }
}
