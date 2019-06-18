package com.petersommerhoff.nutrilicious.viewmodel

import android.arch.lifecycle.ViewModel
import com.petersommerhoff.nutrilicious.data.network.NETWORK
import com.petersommerhoff.nutrilicious.data.network.dto.FoodDto
import com.petersommerhoff.nutrilicious.data.network.dto.SearchWrapper
import com.petersommerhoff.nutrilicious.data.network.usdaApi
import com.petersommerhoff.nutrilicious.model.Food
import kotlinx.coroutines.withContext
import retrofit2.Call

/**
 * @author Peter Sommerhoff
 */
class SearchViewModel : ViewModel() {

  suspend fun getFoodsFor(searchTerm: String): List<Food> {
    val request: Call<SearchWrapper<List<FoodDto>>> = usdaApi.getFoods(searchTerm)
    val foodDtos: List<FoodDto> = withContext(NETWORK) { doRequest(request) }
    return foodDtos.map(::Food)
  }

  private fun doRequest(req: Call<SearchWrapper<List<FoodDto>>>): List<FoodDto> =
      req.execute().body()?.list?.item ?: emptyList()
}
