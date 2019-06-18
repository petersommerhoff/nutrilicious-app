package com.petersommerhoff.nutrilicious.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.petersommerhoff.nutrilicious.model.Food
import kotlinx.coroutines.CoroutineScope

/**
 * @author Peter Sommerhoff
 */

val dbScope = CoroutineScope(DB)

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
