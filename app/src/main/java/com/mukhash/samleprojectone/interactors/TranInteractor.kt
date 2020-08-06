package com.mukhash.samleprojectone.interactors

import com.mukhash.samleprojectone.api.WordsApiService
import com.mukhash.samleprojectone.domain.entities.Word
import com.mukhash.samleprojectone.domain.entities.pastTranslit
import com.mukhash.samleprojectone.repositories.tranrepos.TranRepository
import io.reactivex.Single

class TranInteractor(private val tranRepository: TranRepository, private val apiService: WordsApiService) {
    fun insertTranslit(translit: pastTranslit) {
        tranRepository.insertTranslit(translit)
    }

    fun updateTranslit(translit: pastTranslit) {
        tranRepository.updateTranslit(translit)
    }

    fun deleteTranslit(translit: pastTranslit) {
        tranRepository.deleteTranslit(translit)
    }

    fun getTranlslits(): List<pastTranslit> {
        return tranRepository.getTranslits()
    }

    fun getWords(): Single<List<Word>> {
        return apiService.get()
    }

    fun insertWords(wordList: List<Word>) {
        tranRepository.insertWords(wordList)
    }

    fun searchKzRu(kzSearch: String): String {
        return tranRepository.searchKzRu(kzSearch)
    }

    fun searchKzEn(kzSearch: String): String {
        return tranRepository.searchKzEn(kzSearch)
    }

    fun insertWord(word: Word) {
        tranRepository.insertWord(word)
    }

    fun deleteAll() {
        tranRepository.deleteAll()
    }





}