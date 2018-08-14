package com.petersommerhoff.nutrilicious.data.network.dto

import com.squareup.moshi.JsonClass

/**
 * @author Peter Sommerhoff
 */
@JsonClass(generateAdapter = true)
class ListWrapper<T> {
  var list: T? = null  // Navigates down the ‘list’ object
}

@JsonClass(generateAdapter = true)
class ItemWrapper<T> {
  var item: T? = null  // Navigates down the ‘item’ array
}

typealias SearchWrapper<T> = ListWrapper<ItemWrapper<T>>

@JsonClass(generateAdapter = true)
class FoodDto {
  lateinit var ndbno: String
  lateinit var name: String
  lateinit var group: String
}
