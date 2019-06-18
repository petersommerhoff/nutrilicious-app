package com.petersommerhoff.nutrilicious.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.petersommerhoff.nutrilicious.data.db.NutrientListConverter
import com.petersommerhoff.nutrilicious.data.network.dto.DetailsDto
import com.petersommerhoff.nutrilicious.data.network.dto.NutrientDto

/**
 * @author Peter Sommerhoff
 */
@Entity(tableName = "details")
@TypeConverters(NutrientListConverter::class)
data class FoodDetails(
    @PrimaryKey val id: String,
    val name: String,
    val nutrients: List<Nutrient>
) {
  constructor(dto: DetailsDto) : this(
      dto.desc.ndbno,
      dto.desc.name,
      dto.nutrients.map(::Nutrient)
  )
}

data class Nutrient(
    val id: Int,
    val detailsId: String,
    val name: String,
    val amountPer100g: Amount,
    val type: NutrientType
) {
  constructor(dto: NutrientDto) : this(
      dto.nutrient_id!!,
      dto.detailsId!!,
      dto.name,
      Amount(dto.value.toDouble(), WeightUnit.fromString(dto.unit)),
      NutrientType.valueOf(dto.group.toUpperCase())
  )
}

enum class NutrientType {
  PROXIMATES, MINERALS, VITAMINS, LIPIDS, OTHER
}
