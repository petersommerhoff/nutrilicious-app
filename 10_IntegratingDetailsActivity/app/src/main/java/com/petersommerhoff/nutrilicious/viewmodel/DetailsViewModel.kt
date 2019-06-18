package com.petersommerhoff.nutrilicious.viewmodel

import android.arch.lifecycle.ViewModel
import com.petersommerhoff.nutrilicious.data.network.NETWORK
import com.petersommerhoff.nutrilicious.data.network.dto.DetailsDto
import com.petersommerhoff.nutrilicious.data.network.dto.DetailsWrapper
import com.petersommerhoff.nutrilicious.data.network.usdaApi
import com.petersommerhoff.nutrilicious.model.FoodDetails
import kotlinx.coroutines.withContext
import retrofit2.Call

/**
 * @author Peter Sommerhoff
 */
class DetailsViewModel : ViewModel() {

  suspend fun getDetails(foodId: String): FoodDetails? {
    val request: Call<DetailsWrapper<DetailsDto>> = usdaApi.getDetails(foodId)

    val detailsDto: DetailsDto = withContext(NETWORK) {
      request.execute().body()?.foods?.get(0)?.food
    } ?: return null

    return FoodDetails(detailsDto)
  }
}
