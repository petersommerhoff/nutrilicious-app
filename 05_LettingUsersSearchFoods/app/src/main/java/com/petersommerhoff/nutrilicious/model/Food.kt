package com.petersommerhoff.nutrilicious.model

import com.petersommerhoff.nutrilicious.data.network.dto.FoodDto

/**
 * @author Peter Sommerhoff
 */
data class Food(val id: String, val name: String, val type: String, var isFavorite: Boolean = false) {
  constructor(dto: FoodDto) : this(dto.ndbno, dto.name, dto.group)
}