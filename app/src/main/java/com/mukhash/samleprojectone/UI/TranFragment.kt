package com.mukhash.samleprojectone.UI

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.mukhash.samleprojectone.R
import com.mukhash.samleprojectone.presenter.TranPresenter
import com.mukhash.samleprojectone.contract.TranContract
import com.mukhash.samleprojectone.domain.entities.Word
import com.mukhash.samleprojectone.domain.entities.pastTranslit
import com.facebook.stetho.Stetho
import io.reactivex.Observable.fromCallable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.latinTextView
import kotlinx.android.synthetic.main.translit_item.*
import kz.azatserzhanov.common.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class TranFragment : BaseFragment<TranContract.View, TranContract.Presenter>(),
    TranContract.View, AdapterView.OnItemSelectedListener {

    companion object {
        fun create() = TranFragment()
        var language: Int? = null
    }

    private val presenterImpl: TranPresenter by viewModel()
    override val presenter: TranContract.Presenter
        get() = presenterImpl

    private var tranAdapter: tranAdapter? = null

    private var wordList: List<Word> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.activity_main, container, false)

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tranAdapter = tranAdapter(
            avatarClickListener = { position ->
                createDialog().show {
                    positiveButton(text = "Удалить") {
                        tranAdapter?.delete(position)
                    }
                    negativeButton(text = "Отмена") {
                        createDialog().dismiss()
                    }
                }
            },
            avatarClickListener2 = { position ->
                if (toggle.isChecked) {
                    fromCallable() {
                        presenter.insertTranslit(position, tranAdapter?.tranList!!.toList())
                    }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                } else {
                    fromCallable() {
                        presenter.deleteFromDb(tranAdapter?.tranList!!.toList(), position)
                    }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        )

         wordList = presenter.getWords()
             .subscribeOn(Schedulers.io())
             .blockingGet()

         fromCallable() {
             presenter.insertWords(wordList)
         }
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(
                 { Log.d("insertWords", wordList.toString()) },
                 { Log.d("insertWords", "Error") }
             )

        createRV()

        fromCallable() {
            tranAdapter?.addItems(presenter.getDao().getTranlslits())
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {Log.d("adapter", "Success")},
                {Log.d("adapter", "Error")}
            )

        setupViews()

        createDialog()

        Stetho.initializeWithDefaults(context)
    }

    override fun showLatinText(latinText: String) {
        latinTextView.text = latinText
    }

    override fun showFreq(result: String) {
        textViewFreq.text = result
    }

    override fun showTranslits() {
    }

    private fun setupViews() {
        button.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                presenter.translit(editText.text.toString())
                presenter.getCharFreqs(latinTextView.text.toString())
                tranAdapter?.addItem(
                    pastTranslit(
                        kirilText = editText.text.toString(),
                        latinText = latinTextView.text.toString()
                    )
                )
            }
        }
        fillSpinner(spinner)
        spinner.onItemSelectedListener = this
        button_translate.setOnClickListener {
            fromCallable() {
                if (editText.text.isNotEmpty() && language == 0) {
                    presenter.searchKzRu(editText.text.toString())
                } else {
                    presenter.searchKzEn(editText.text.toString())
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ translation: String ->
                    latinTextView.text = translation
                },
                    { Log.d("Translate", "Error") })
        }

        toggle_theme.setOnClickListener {
            setupDark()
        }
    }

    private fun createRV() {
        val manager = LinearLayoutManager(context)
        recyclerView.apply {
            layoutManager = manager
            adapter = tranAdapter
        }
    }

    private fun createDialog(): MaterialDialog {
        val mDialog = context?.let {
            MaterialDialog(it)
                .title(text = "Удалить из списка?")
                .message(text = "Вы удалите из списка, но не из избранных?")
        }
        return mDialog!!
    }

    private fun getDark() {
        constraintLayout.setBackgroundColor(parseColor("#121212"))
        textView4.setTextColor(parseColor("#ECEFF1"))
        textView5.setTextColor(parseColor("#ECEFF1"))
        textView3.setTextColor(parseColor("#ECEFF1"))
        editText.setBackgroundResource(R.drawable.edittextbackground_darkmode)
        imageView.setImageResource(R.drawable.gear)
        textViewFreq.setTextColor(parseColor("#ECEFF1"))
    }

    private fun getLight() {
        constraintLayout.setBackgroundColor(parseColor("#ffffff"))
        textView4.setTextColor(parseColor("#455A64"))
        textView5.setTextColor(parseColor("#455A64"))
        textView3.setTextColor(parseColor("#455A64"))
        editText.setBackgroundResource(R.drawable.edittextbackground)
        imageView.setImageResource(R.drawable.wheel)
        textViewFreq.setTextColor(parseColor("#455A64"))
    }

    private fun setupDark() {
        if (toggle_theme.isChecked) {
            getDark()
        } else {
            getLight()
        }
    }

    private fun fillSpinner(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this!!.context!!,
            R.array.languages,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        language = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
