package com.mukhash.samleprojectone.repositories.daos

import androidx.room.*
import androidx.room.Dao
import com.mukhash.samleprojectone.domain.entities.Word


@Dao
interface ApiDao {
    @Query("SELECT ru FROM word_table where kz like :kzSearch")
    fun searchKzRu(kzSearch: String): String

    @Query("SELECT en FROM word_table where kz like :kzSearch")
    fun searchKzEn(kzSearch: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWords(wordList: List<Word>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWord(word: Word)

    @Query("Delete from word_table")
    fun deleteAll()
}