package com.mukhash.samleprojectone.contract


import com.mukhash.samleprojectone.domain.entities.Word
import com.mukhash.samleprojectone.domain.entities.pastTranslit
import com.mukhash.samleprojectone.repositories.daos.Dao
import io.reactivex.Single
import kz.azatserzhanov.common.base.MvpPresenter
import kz.azatserzhanov.common.base.MvpView

interface TranContract {

    interface View : MvpView {
        fun showLatinText(latinText: String)
        fun showFreq(result: String)
        fun showTranslits()
    }

    interface Presenter : MvpPresenter<View> {
        fun translit(kirilText: String)
        fun getCharFreqs(text: String)
        fun getDao(): Dao
        fun insertWords(wordList: List<Word>)
        fun insertTranslit(position: Int, tranList: List<pastTranslit>)
        fun deleteFromDb(tranList: List<pastTranslit>, position: Int)
        fun getTranslits(): List<pastTranslit>
        fun getWords(): Single<List<Word>>
        fun insertWord(word: Word)
        fun searchKzRu(kzSearch: String): String
        fun searchKzEn(kzSearch: String): String
        fun deleteAll()
    }
}