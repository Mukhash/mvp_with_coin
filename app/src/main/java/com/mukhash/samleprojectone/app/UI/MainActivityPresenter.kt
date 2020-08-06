package com.mukhash.samleprojectone.app.UI

import kz.azatserzhanov.common.base.BasePresenter

class MainActivityPresenter : BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {
    override fun load() {
        view?.showSomeView()
    }
}