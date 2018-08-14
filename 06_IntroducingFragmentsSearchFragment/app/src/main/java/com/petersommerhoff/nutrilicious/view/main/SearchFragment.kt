package com.petersommerhoff.nutrilicious.view.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.petersommerhoff.nutrilicious.R
import com.petersommerhoff.nutrilicious.data.network.NETWORK
import com.petersommerhoff.nutrilicious.model.Food
import com.petersommerhoff.nutrilicious.view.common.getViewModel
import com.petersommerhoff.nutrilicious.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

/**
 * @author Peter Sommerhoff
 */
class SearchFragment : Fragment() {

  private lateinit var searchViewModel: SearchViewModel

  private var lastSearch = ""

  private var lastResults = emptyList<Food>()  // Import Food!

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
  }

  private fun setUpSearchRecyclerView() = with(rvFoods) {
    adapter = SearchListAdapter(emptyList())
    layoutManager = LinearLayoutManager(context)
    addItemDecoration(DividerItemDecoration(
        context, LinearLayoutManager.VERTICAL
    ))
    setHasFixedSize(true)
  }

  private fun setUpSwipeRefresh() {
    swipeRefresh.setOnRefreshListener {
      updateListFor(lastSearch)
    }
  }

  fun updateListFor(searchTerm: String) {
    lastSearch = searchTerm
    swipeRefresh?.isRefreshing = true

    launch(NETWORK) {
      val foods = searchViewModel.getFoodsFor(searchTerm)
      lastResults = foods

      withContext(UI) {
        (rvFoods?.adapter as? SearchListAdapter)?.setItems(foods)
        swipeRefresh?.isRefreshing = false
      }
    }
  }
}
