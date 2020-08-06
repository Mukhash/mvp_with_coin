package kz.azatserzhanov.common.base

interface MvpPresenter<V : MvpView> {

    fun attach(view: V)

    fun detach()
}