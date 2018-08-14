package com.petersommerhoff.nutrilicious.model

import com.petersommerhoff.nutrilicious.model.WeightUnit.*

/**
 * @author Peter Sommerhoff
 */
data class Amount(val value: Double, val unit: WeightUnit) {

  fun normalized() = when(unit) {
    GRAMS, KCAL, IU -> value
    MILLIGRAMS -> value / 1000.0
    MICROGRAMS -> value / 1_000_000.0
  }
}

enum class WeightUnit {
  GRAMS, MILLIGRAMS, MICROGRAMS, KCAL, IU;

  companion object {
    fun fromString(unit: String) = when(unit) {
      "g" -> WeightUnit.GRAMS
      "mg" -> WeightUnit.MILLIGRAMS
      "\u00b5g" -> WeightUnit.MICROGRAMS
      "kcal" -> WeightUnit.KCAL
      "IU" -> WeightUnit.IU
      else -> throw IllegalArgumentException("Unknown weight unit: $unit")
    }
  }

  override fun toString(): String = when(this) {
    WeightUnit.GRAMS -> "g"
    WeightUnit.MILLIGRAMS -> "mg"
    WeightUnit.MICROGRAMS -> "\u00b5g"
    WeightUnit.KCAL -> "kcal"
    WeightUnit.IU -> "IU"
  }
}

internal val RDI = mapOf(
    255 to Amount(3000.0, GRAMS),       // water
    208 to Amount(2000.0, KCAL),        // energy
    203 to Amount(50.0, GRAMS),         // protein
    204 to Amount(78.0, GRAMS),         // total fat (lipids)
    205 to Amount(275.0, GRAMS),        // carbohydrates
    291 to Amount(28.0, GRAMS),         // fiber
    269 to Amount(50.0, GRAMS),         // sugars
    301 to Amount(1300.0, MILLIGRAMS),  // calcium
    303 to Amount(13.0, MILLIGRAMS),    // iron
    304 to Amount(350.0, MILLIGRAMS),   // magnesium
    305 to Amount(700.0, MILLIGRAMS),   // phosphorus
    306 to Amount(4700.0, MILLIGRAMS),  // potassium
    307 to Amount(1500.0, MILLIGRAMS),  // sodium
    309 to Amount(10.0, MILLIGRAMS),    // zinc
    401 to Amount(85.0, MILLIGRAMS),    // vitamin c
    404 to Amount(1200.0, MICROGRAMS),  // vitamin b1 (thiamin)
    405 to Amount(1200.0, MICROGRAMS),  // vitamin b2 (riboflavin)
    406 to Amount(15.0, MILLIGRAMS),    // vitamin b3 (niacin)
    415 to Amount(1300.0, MICROGRAMS),  // vitamin b6 (pyridoxine)
    435 to Amount(400.0, MICROGRAMS),   // folate
    418 to Amount(3.0, MICROGRAMS),     // vitamin b12 (cobalamine)
    320 to Amount(800.0, MICROGRAMS),   // vitamin a
    323 to Amount(15.0, MILLIGRAMS),    // vitamin e (tocopherol)
    328 to Amount(15.0, MICROGRAMS),    // vitamin d (d2 + d3)
    430 to Amount(105.0, MICROGRAMS),   // vitamin k
    606 to Amount(20.0, GRAMS),         // saturated fats
    605 to Amount(0.0, GRAMS),          // transfats
    601 to Amount(300.0, MILLIGRAMS)    // cholesterol
)
