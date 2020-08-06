package com.mukhash.samleprojectone.api

import com.mukhash.samleprojectone.domain.entities.Word
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WordsApiService {
        companion object Factory {
            fun create(): WordsApiService {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://simplechat2345.herokuapp.com")
                    .build()

                return retrofit.create(WordsApiService::class.java);
            }
        }

        @GET("index.json")
        fun get(): Single<List<Word>>
    }
