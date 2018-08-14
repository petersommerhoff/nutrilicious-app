package com.petersommerhoff.nutrilicious.model

import android.arch.persistence.room.*
import com.petersommerhoff.nutrilicious.data.network.dto.FoodDto

/**
 * @author Peter Sommerhoff
 */

@Entity(tableName = "favorites")
data class Food(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    var isFavorite: Boolean = false
) {
  constructor(dto: FoodDto) : this(dto.ndbno, dto.name, dto.group)
}