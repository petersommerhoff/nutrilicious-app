package com.petersommerhoff.nutrilicious.view.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.petersommerhoff.nutrilicious.R
import com.petersommerhoff.nutrilicious.data.network.networkScope
import com.petersommerhoff.nutrilicious.data.network.usdaApi
import com.petersommerhoff.nutrilicious.model.Food
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

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

    networkScope.launch {
      val dtos = usdaApi.getFoods("raw").execute()?.body()?.list?.item!!
      val foods: List<Food> = dtos.map(::Food)

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
