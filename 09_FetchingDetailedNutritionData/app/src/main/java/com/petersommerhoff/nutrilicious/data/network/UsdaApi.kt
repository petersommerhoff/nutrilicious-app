package com.petersommerhoff.nutrilicious.data.network

import com.petersommerhoff.nutrilicious.data.network.dto.*
import retrofit2.Call
import retrofit2.http.*

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

  @GET("V2/reports?format=json")
  fun getDetails(
      @Query("ndbno") id: String,             // Only non-optional parameter is food ID
      @Query("type") detailsType: Char = 'b'  // b = basic, f = full, s = stats
  ): Call<DetailsWrapper<DetailsDto>>

}
