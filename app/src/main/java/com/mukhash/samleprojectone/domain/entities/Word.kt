package com.mukhash.samleprojectone.domain.entities

import androidx.room.Entity

@Entity(primaryKeys = ["kz", "ru", "en"] , tableName = "word_table")
data class Word(
   // @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
   // @PrimaryKey
    var kz: String,
    var ru: String,
    var en: String
)
