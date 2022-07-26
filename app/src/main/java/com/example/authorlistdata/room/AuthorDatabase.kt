package com.example.mvvmretrofitcoroutines.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.reflect.KParameter


@Database(entities = [Authorentity::class], version = 3, exportSchema = false)
abstract class AuthorDatabase : RoomDatabase() {

    abstract fun appDao(): AuthorDao

}



