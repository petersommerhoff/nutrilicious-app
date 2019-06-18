package com.petersommerhoff.nutrilicious.view.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.widget.SearchView
import com.petersommerhoff.nutrilicious.R
import com.petersommerhoff.nutrilicious.data.network.networkScope
import com.petersommerhoff.nutrilicious.view.common.UI
import com.petersommerhoff.nutrilicious.view.common.getViewModel
import com.petersommerhoff.nutrilicious.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

  private lateinit var searchViewModel: SearchViewModel

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
    setUpSearchRecyclerView()

    navigation.setOnNavigationItemSelectedListener(navListener)
    searchViewModel = getViewModel(SearchViewModel::class)
  }

  private fun updateListFor(searchTerm: String) {
    networkScope.launch {
      val foods = searchViewModel.getFoodsFor(searchTerm)

      withContext(UI) {
        (rvFoods.adapter as SearchListAdapter).setItems(foods)
      }
    }
  }

  private fun setUpSearchRecyclerView() = with(rvFoods) {
    adapter = SearchListAdapter(emptyList())
    layoutManager = LinearLayoutManager(this@MainActivity)
    addItemDecoration(DividerItemDecoration(
        this@MainActivity, LinearLayoutManager.VERTICAL
    ))
    setHasFixedSize(true)
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
      updateListFor(query)
    }
  }
}
