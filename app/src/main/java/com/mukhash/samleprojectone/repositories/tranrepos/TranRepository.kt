package com.mukhash.samleprojectone.repositories.tranrepos

import android.annotation.SuppressLint
import com.mukhash.samleprojectone.domain.entities.Word
import com.mukhash.samleprojectone.domain.entities.pastTranslit
import com.mukhash.samleprojectone.repositories.daos.ApiDao
import com.mukhash.samleprojectone.repositories.daos.Dao

class TranRepository(private var dao: Dao, private var apiDao: ApiDao)
{
    @SuppressLint("CheckResult")
    fun insertTranslit(translit: pastTranslit) {
        dao?.insertTranslit(translit)
    }

    @SuppressLint("CheckResult")
    fun updateTranslit(translit: pastTranslit) {
        dao?.updateTranslit(translit)
    }

    @SuppressLint("CheckResult")
    fun deleteTranslit(translit: pastTranslit) {
        dao?.deleteTranslit(translit)
    }

    fun getTranslits(): List<pastTranslit> {
        return dao?.getTranlslits()
    }

    fun insertWords(wordList: List<Word>) {
        apiDao.insertWords(wordList)
    }

    fun searchKzRu(kzSearch: String): String {
        return apiDao.searchKzRu(kzSearch)
    }

    fun searchKzEn(kzSearch: String): String {
        return apiDao.searchKzEn(kzSearch)
    }

    fun insertWord(word: Word) {
        apiDao.insertWord(word)
    }

    fun deleteAll() {
        apiDao.deleteAll()
    }
}