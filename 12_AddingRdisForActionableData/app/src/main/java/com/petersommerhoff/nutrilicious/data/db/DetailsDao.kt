package com.petersommerhoff.nutrilicious.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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
