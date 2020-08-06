package com.mukhash.samleprojectone.domain.entities

import androidx.room.*

@Entity(tableName = "tran_table")
data class pastTranslit(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var kirilText: String,
    var latinText: String
)

