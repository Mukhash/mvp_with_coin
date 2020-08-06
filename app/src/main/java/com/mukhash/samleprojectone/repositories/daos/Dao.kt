package com.mukhash.samleprojectone.repositories.daos

import androidx.room.*
import androidx.room.Dao
import com.mukhash.samleprojectone.domain.entities.pastTranslit

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTranslit(translit: pastTranslit)

    @Update
    fun updateTranslit(translit: pastTranslit)

    @Delete
    fun deleteTranslit(translit: pastTranslit)

    @Query("SELECT * FROM tran_table")
    fun getTranlslits(): List<pastTranslit>
}