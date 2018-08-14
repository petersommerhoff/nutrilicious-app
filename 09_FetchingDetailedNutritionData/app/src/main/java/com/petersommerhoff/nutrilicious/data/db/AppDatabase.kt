package com.petersommerhoff.nutrilicious.data.db

import android.arch.persistence.room.*
import android.content.Context
import com.petersommerhoff.nutrilicious.model.Food

/**
 * @author Peter Sommerhoff
 */
@Database(entities = [Food::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

  companion object {
    private var INSTANCE: AppDatabase? = null

    fun getInstance(ctx: Context): AppDatabase {
      if (INSTANCE == null) { INSTANCE = buildDatabase(ctx) }
      return INSTANCE!!
    }

    private fun buildDatabase(ctx: Context) = Room
        .databaseBuilder(ctx, AppDatabase::class.java, "AppDatabase")
        .build()
  }

  abstract fun favoritesDao(): FavoritesDao
}
