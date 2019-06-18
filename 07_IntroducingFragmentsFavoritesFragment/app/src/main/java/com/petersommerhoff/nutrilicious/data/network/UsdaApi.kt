package com.petersommerhoff.nutrilicious.data.network

import com.petersommerhoff.nutrilicious.data.network.dto.FoodDto
import com.petersommerhoff.nutrilicious.data.network.dto.SearchWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Peter Sommerhoff
 */
interface UsdaApi {

  @GET("search?format=json")
  fun getFoods(
      @Query("q") searchTerm: String,     // Only non-optional parameter
      @Query("sort") sortBy: Char = 'r',  // Sorts by relevance by default
      @Query("ds") dataSource: String = "Standard Reference",
      @Query("offset") offset: Int = 0
  ): Call<SearchWrapper<List<FoodDto>>>
}
