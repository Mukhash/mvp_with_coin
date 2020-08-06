package com.mukhash.samleprojectone


import com.mukhash.samleprojectone.api.WordsApiService
import com.mukhash.samleprojectone.interactors.TranInteractor
import com.mukhash.samleprojectone.presenter.TranPresenter
import com.mukhash.samleprojectone.repositories.databases.TranDatabase
import com.mukhash.samleprojectone.repositories.tranrepos.TranRepository
import kz.azatserzhanov.common.base.InjectionModule
import org.koin.dsl.module

object TranModule : InjectionModule {

    override fun create() = module {
        single { TranPresenter(get(), get()) }
        single { TranInteractor(get(), get()) }
        single { WordsApiService.create() }
        single { TranRepository(get(), get()) }
        single { TranDatabase.getTranDatabase(get()) }
        single { get<TranDatabase>().tranDao() }
        single { get<TranDatabase>().apiDao() }
    }
}