package com.mukhash.samleprojectone.repositories.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mukhash.samleprojectone.domain.entities.Word
import com.mukhash.samleprojectone.domain.entities.pastTranslit
import com.mukhash.samleprojectone.repositories.daos.ApiDao
import com.mukhash.samleprojectone.repositories.daos.Dao

@Database(entities = arrayOf(pastTranslit::class, Word::class), version = 4, exportSchema = false)
abstract class TranDatabase : RoomDatabase() {
    abstract fun tranDao(): Dao
    abstract fun apiDao(): ApiDao

    companion object {
        var INSTANCE: TranDatabase? = null

        fun getTranDatabase(context: Context): TranDatabase? {
            if (INSTANCE == null) {
                synchronized(TranDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            TranDatabase::class.java,
                            "database"
                        )
                        //.allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

    fun getDatabase(): TranDatabase? {
        return INSTANCE
    }
}
