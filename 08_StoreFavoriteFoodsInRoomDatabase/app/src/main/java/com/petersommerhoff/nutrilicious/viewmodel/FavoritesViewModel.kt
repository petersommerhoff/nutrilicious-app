package com.petersommerhoff.nutrilicious.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import com.petersommerhoff.nutrilicious.data.db.*
import com.petersommerhoff.nutrilicious.model.Food
import kotlinx.coroutines.experimental.*

/**
 * @author Peter Sommerhoff
 */
class FavoritesViewModel(app: Application) : AndroidViewModel(app) {

  private val dao by lazy { AppDatabase.getInstance(getApplication()).favoritesDao()}

  suspend fun getFavorites(): LiveData<List<Food>> = withContext(DB) {
    dao.loadAll()
  }

  suspend fun getAllIds(): List<String> = withContext(DB) { dao.loadAllIds() }

  fun add(favorite: Food) = launch { dao.insert(favorite) }

  fun delete(favorite: Food) = launch { dao.delete(favorite) }
}
