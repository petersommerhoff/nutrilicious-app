package com.petersommerhoff.nutrilicious.view.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.petersommerhoff.nutrilicious.R
import com.petersommerhoff.nutrilicious.data.network.networkScope
import com.petersommerhoff.nutrilicious.model.Food
import com.petersommerhoff.nutrilicious.view.common.UI
import com.petersommerhoff.nutrilicious.view.common.getViewModel
import com.petersommerhoff.nutrilicious.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Peter Sommerhoff
 */
class SearchFragment : Fragment() {

  private lateinit var searchViewModel: SearchViewModel

  private var lastSearch = ""

  private var lastResults = emptyList<Food>()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_search, container, false)
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    searchViewModel = getViewModel(SearchViewModel::class)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpSearchRecyclerView()
    setUpSwipeRefresh()
    (rvFoods?.adapter as? SearchListAdapter)?.setItems(lastResults)
  }

  private fun setUpSearchRecyclerView() {
    MainActivity.setUpRecyclerView(rvFoods)
  }

  private fun setUpSwipeRefresh() {
    swipeRefresh.setOnRefreshListener {
      updateListFor(lastSearch)
    }
  }

  fun updateListFor(searchTerm: String) {
    lastSearch = searchTerm
    swipeRefresh?.isRefreshing = true

    networkScope.launch {
      val foods = searchViewModel.getFoodsFor(searchTerm)
      lastResults = foods

      withContext(UI) {
        (rvFoods?.adapter as? SearchListAdapter)?.setItems(foods)
        swipeRefresh?.isRefreshing = false
      }
    }
  }
}
