package com.petersommerhoff.nutrilicious.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.petersommerhoff.nutrilicious.data.DetailsRepository
import com.petersommerhoff.nutrilicious.model.FoodDetails

/**
 * @author Peter Sommerhoff
 */
class DetailsViewModel(app: Application) : AndroidViewModel(app) {
  private val repo = DetailsRepository(app)
  suspend fun getDetails(foodId: String): FoodDetails? = repo.getDetails(foodId)
}