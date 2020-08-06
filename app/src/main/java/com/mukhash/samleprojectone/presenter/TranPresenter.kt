package com.mukhash.samleprojectone.presenter


import com.mukhash.samleprojectone.contract.TranContract
import com.mukhash.samleprojectone.domain.entities.Word
import com.mukhash.samleprojectone.domain.entities.pastTranslit
import com.mukhash.samleprojectone.interactors.TranInteractor
import com.mukhash.samleprojectone.repositories.daos.Dao
import io.reactivex.Single
import kz.azatserzhanov.common.base.BasePresenter
import java.util.*

class TranPresenter(private val TranInteractor: TranInteractor, private val dao: Dao) :
    BasePresenter<TranContract.View>(),
    TranContract.Presenter {

    private val kiril: MutableList<String> = mutableListOf(
        "а", "ә", "б", "в", "г", "ғ", "д", "е", "ж",
        "з", "и", "й", "к", "қ", "л", "м", "н", "ң", "о", "ө", "п", "р", "с", "т",
        "у", "ұ", "ү", "ф", "х", "һ", "ч", "ш", "ы", "і", " ", "!", "?", ".", ",", "", "-"
    )
    private val latin: MutableList<String> = mutableListOf(
        "а", "á", "b", "v", "g", "ǵ", "d", "е", "j",
        "z", "i", "i", "к", "q", "l", "m", "n", "ń", "о", "ó", "p", "r", "s", "t",
        "ý", "u", "ú", "f", "h", "h", "ch", "sh", "y", "і", " ", "!", "?", ".", ",", "", "-"
    )

    override fun getTranslits(): List<pastTranslit> {
        return TranInteractor.getTranlslits()
    }

    override fun translit(kirilText: String) {
        val resultList = mutableListOf<String>()
        val ups = mutableListOf<Int>()
        val upperChars = kirilText.split("").drop(1).dropLast(1)

        for (string in upperChars) {
            if (string == string.toUpperCase(Locale.ROOT)) {
                ups.add(upperChars.indexOf(string))
            }
        }

        for (char in upperChars) {
            if (char.toLowerCase(Locale.ROOT) in kiril) {
                if (upperChars.indexOf(char) in ups) {
                    val str: String = latin[kiril.indexOf(char.toLowerCase(Locale.ROOT))]
                    resultList.add(str.toUpperCase(Locale.ROOT))
                } else {
                    resultList.add(latin[kiril.indexOf(char)])
                }
            } else { //TODO work around disallowed values
            }
        }
        view?.showLatinText(resultList.joinToString(""))
    }

    override fun getCharFreqs(text: String) {
        var result = ""
        val charList =
            text.toLowerCase(Locale.ROOT).split("").drop(1).dropLast(1).dropWhile { it == " " }
        val len: Double = charList.size.toDouble()
        var countChar: Int
        for (char1 in charList) {
            countChar = 0
            for (char2 in charList) {
                if (char1 == char2) {
                    countChar++
                }
            }
            val ratio = countChar / len
            if (result.contains(char1)) {
            } else {
                val ratioFormatted: Double = String.format("%.3f", ratio).toDouble()
                result += " $char1 - ${(ratioFormatted) * 100}% "
            }
        }
        view?.showFreq(result)

    }

    override fun getDao(): Dao {
        return dao
    }

    override fun insertWords(wordList: List<Word>) {
        TranInteractor.insertWords(wordList)
    }

    override fun getWords(): Single<List<Word>> {
        return TranInteractor.getWords()
    }

    override fun insertTranslit(position: Int, tranList: List<pastTranslit>) {
        TranInteractor.insertTranslit(tranList[position])
    }

    override fun deleteFromDb(tranList: List<pastTranslit>, position: Int) {
        if (TranInteractor.getTranlslits()?.contains(tranList[position])!!) {
            TranInteractor.deleteTranslit(tranList[position])
        }
    }

    override fun insertWord(word: Word) {
        TranInteractor.insertWord(word)
    }

    override fun searchKzEn(kzSearch: String): String {
        return TranInteractor.searchKzEn(kzSearch)
    }

    override fun searchKzRu(kzSearch: String): String {
        return TranInteractor.searchKzRu(kzSearch)
    }

    override fun deleteAll() {
        TranInteractor.deleteAll()
    }
}