package com.petersommerhoff.nutrilicious.data

import android.content.Context
import com.petersommerhoff.nutrilicious.data.db.*
import com.petersommerhoff.nutrilicious.data.network.*
import com.petersommerhoff.nutrilicious.data.network.dto.*
import com.petersommerhoff.nutrilicious.model.FoodDetails
import kotlinx.coroutines.experimental.*
import retrofit2.Call

/**
 * @author Peter Sommerhoff
 */
class DetailsRepository(ctx: Context) {

  private val detailsDao by lazy { AppDatabase.getInstance(ctx).detailsDao() }

  fun add(details: FoodDetails) = launch(DB) { detailsDao.insert(details) }

  suspend fun getDetails(id: String): FoodDetails? {
    return withContext(DB) { detailsDao.loadById(id) }       // Prefers database
        ?: withContext(NETWORK) { fetchDetailsFromApi(id) }  // Falls back to network
            .also { if (it != null) this.add(it) } // Adds newly fetched foods to DB
  }

  private suspend fun fetchDetailsFromApi(id: String): FoodDetails? {
    val request: Call<DetailsWrapper<DetailsDto>> = usdaApi.getDetails(id)

    val detailsDto: DetailsDto = withContext(NETWORK) {
      request.execute().body()?.foods?.get(0)?.food
    } ?: return null

    return FoodDetails(detailsDto)
  }
}
