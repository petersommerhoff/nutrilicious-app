package com.petersommerhoff.nutrilicious.view.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.widget.SearchView
import com.petersommerhoff.nutrilicious.R
import com.petersommerhoff.nutrilicious.model.Food
import com.petersommerhoff.nutrilicious.view.common.addFragmentToState
import com.petersommerhoff.nutrilicious.view.common.getViewModel
import com.petersommerhoff.nutrilicious.view.common.replaceFragment
import com.petersommerhoff.nutrilicious.view.common.toast
import com.petersommerhoff.nutrilicious.view.details.DetailsActivity
import com.petersommerhoff.nutrilicious.view.details.FOOD_ID_EXTRA
import com.petersommerhoff.nutrilicious.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.activity_main.*

private const val SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT"

class MainActivity : AppCompatActivity() {

  private lateinit var searchFragment: SearchFragment

  private lateinit var favoritesViewModel: FavoritesViewModel

  private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
    when (it.itemId) {
      R.id.navigation_home -> {
        replaceFragment(R.id.mainView, searchFragment) // Uses existing search fragment
        return@OnNavigationItemSelectedListener true
      }
      R.id.navigation_my_foods -> {
        replaceFragment(R.id.mainView, FavoritesFragment())  // Creates new fragment
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  fun setUpRecyclerView(rv: RecyclerView, list: List<Food> = emptyList()) {
    with(rv) {
      adapter = setUpSearchListAdapter(rv, list)
      layoutManager = LinearLayoutManager(context)
      addItemDecoration(DividerItemDecoration(
          context, LinearLayoutManager.VERTICAL
      ))
      setHasFixedSize(true)
    }
  }

  private fun setUpSearchListAdapter(rv: RecyclerView, items: List<Food>) = SearchListAdapter(
      items,
      onItemClick = { startDetailsActivity(it) },
      onStarClick = { food, layoutPosition ->
        toggleFavorite(food)
        rv.adapter?.notifyItemChanged(layoutPosition)
      })

  private fun startDetailsActivity(food: Food) {
    val intent = Intent(this, DetailsActivity::class.java).apply {
      putExtra(FOOD_ID_EXTRA, food.id)
    }
    startActivity(intent)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    recoverOrBuildSearchFragment()
    replaceFragment(R.id.mainView, searchFragment)

    favoritesViewModel = getViewModel(FavoritesViewModel::class)

    navigation.setOnNavigationItemSelectedListener(navListener)
  }

  private fun toggleFavorite(food: Food) {
    val wasFavoriteBefore = food.isFavorite
    food.isFavorite = food.isFavorite.not()

    if (wasFavoriteBefore) {
      favoritesViewModel.delete(food)
      toast("Removed ${food.name} from your favorites.")
    } else {
      favoritesViewModel.add(food)
      toast("Added ${food.name} as a new favorite of yours!")
    }
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
