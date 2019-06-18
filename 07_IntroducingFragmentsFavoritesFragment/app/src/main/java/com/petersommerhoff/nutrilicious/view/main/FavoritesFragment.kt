package com.petersommerhoff.nutrilicious.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.petersommerhoff.nutrilicious.R
import com.petersommerhoff.nutrilicious.model.Food
import kotlinx.android.synthetic.main.fragment_favorites.*

/**
 * @author Peter Sommerhoff
 */
class FavoritesFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_favorites, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpRecyclerView()
  }

  private fun setUpRecyclerView() {
    MainActivity.setUpRecyclerView(rvFavorites, sampleData())
  }


  // Temporary, use string resources instead of hard-coded strings in general!
  private fun sampleData(): List<Food> = listOf(
      Food("00001", "Marshmallow", "Candy and Sweets", true),
      Food("00002", "Nougat", "Candy and Sweets", true),
      Food("00003", "Oreo", "Candy and Sweets", true)
  )
}
