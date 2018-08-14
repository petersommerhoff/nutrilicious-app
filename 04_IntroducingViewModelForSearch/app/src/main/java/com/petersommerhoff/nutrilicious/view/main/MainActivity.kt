package com.petersommerhoff.nutrilicious.view.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import com.petersommerhoff.nutrilicious.R
import com.petersommerhoff.nutrilicious.data.network.*
import com.petersommerhoff.nutrilicious.model.Food
import com.petersommerhoff.nutrilicious.view.common.getViewModel
import com.petersommerhoff.nutrilicious.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

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

    launch(NETWORK) {
      val foods = searchViewModel.getFoodsFor("raw")

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
}
