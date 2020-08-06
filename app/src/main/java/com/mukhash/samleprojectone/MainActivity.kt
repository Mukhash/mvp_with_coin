package com.mukhash.samleprojectone

import android.os.Bundle
import com.mukhash.samleprojectone.app.UI.MainActivityContract
import com.mukhash.samleprojectone.app.UI.MainActivityPresenter
import com.mukhash.samleprojectone.UI.TranFragment
import kz.azatserzhanov.common.base.BaseActivity
import kz.azatserzhanov.common.contract.AppRouterContract
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), MainActivityContract.View, AppRouterContract {

    private val presenter: MainActivityPresenter by viewModel()

    override fun onCreate(savedInstantState: Bundle?) {
        super.onCreate(savedInstantState)
        setContentView(R.layout.frame_layout)

        showTranslit()
    }

    override fun showTranslit() {
        replaceFragment(TranFragment.create())
    }

    override fun showSomeView() {

    }
}