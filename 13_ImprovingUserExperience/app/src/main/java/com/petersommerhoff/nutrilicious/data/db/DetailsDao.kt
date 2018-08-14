package com.petersommerhoff.nutrilicious.data.db

import android.arch.persistence.room.*
import com.petersommerhoff.nutrilicious.model.FoodDetails

/**
 * @author Peter Sommerhoff
 */
@Dao
interface DetailsDao {

  @Query("SELECT * FROM details WHERE id = :ndbno")
  fun loadById(ndbno: String): FoodDetails?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(food: FoodDetails)
}
