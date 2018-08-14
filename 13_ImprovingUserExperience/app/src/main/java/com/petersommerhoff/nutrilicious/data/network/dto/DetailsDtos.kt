package com.petersommerhoff.nutrilicious.data.network.dto

import com.squareup.moshi.JsonClass

/**
 * @author Peter Sommerhoff
 */
@JsonClass(generateAdapter = true)
class FoodsWrapper<T> {
  var foods: List<T> = listOf()
}

@JsonClass(generateAdapter = true)
class FoodWrapper<T> {
  var food: T? = null
}

typealias DetailsWrapper<T> = FoodsWrapper<FoodWrapper<T>>

@JsonClass(generateAdapter = true)
class DetailsDto(val desc: DescriptionDto, val nutrients: List<NutrientDto>) {
  init {
    nutrients.forEach { it.detailsId = desc.ndbno }
  }
}

@JsonClass(generateAdapter = true)
class DescriptionDto {
  lateinit var ndbno: String
  lateinit var name: String
}

@JsonClass(generateAdapter = true)
class NutrientDto {
  var nutrient_id: Int? = null
  var detailsId: String? = null
  lateinit var name: String
  lateinit var unit: String
  var value: Float = 0f
  lateinit var group: String
}
