package com.mukhash.samleprojectone.app.UI

import kz.azatserzhanov.common.base.MvpPresenter
import kz.azatserzhanov.common.base.MvpView

interface MainActivityContract {

    interface View: MvpView {
        fun showSomeView()
    }

    interface Presenter : MvpPresenter<View> {
        fun load()
    }
}