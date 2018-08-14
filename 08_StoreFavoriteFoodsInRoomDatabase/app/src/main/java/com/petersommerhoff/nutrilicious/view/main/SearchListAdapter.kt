package com.petersommerhoff.nutrilicious.view.main

import android.support.v7.widget.RecyclerView
import android.view.*
import com.petersommerhoff.nutrilicious.R
import com.petersommerhoff.nutrilicious.model.Food
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rv_item.*

/**
 * @author Peter Sommerhoff
 */
class SearchListAdapter(
    private var items: List<Food>,
    private val onStarClick: (Food, Int) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.rv_item, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int = items.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindTo(items[position])
  }

  // In this app, we'll usually replace all items so DiffUtil has little use
  fun setItems(newItems: List<Food>) {
    this.items = newItems
    notifyDataSetChanged()
  }

  inner class ViewHolder(
      override val containerView: View
  ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindTo(food: Food) {
      tvFoodName.text = food.name
      tvFoodType.text = food.type

      val image = if (food.isFavorite) {
        android.R.drawable.btn_star_big_on
      } else {
        android.R.drawable.btn_star_big_off
      }
      ivStar.setImageResource(image)
      ivStar.setOnClickListener { onStarClick(food, this.layoutPosition) }
    }
  }
}
