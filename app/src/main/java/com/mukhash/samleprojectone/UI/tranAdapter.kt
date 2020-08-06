package com.mukhash.samleprojectone.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mukhash.samleprojectone.R
import com.mukhash.samleprojectone.domain.entities.pastTranslit
import com.mukhash.samleprojectone.repositories.daos.Dao
import kotlinx.android.synthetic.main.translit_item.view.*

class tranAdapter(
    private val avatarClickListener: (position: Int) -> Unit,
    private val avatarClickListener2: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val tranList = mutableListOf<pastTranslit>()
    override fun getItemCount() = tranList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tranViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.translit_item, parent, false)
        return tranViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as tranViewHolder).bind(
            tranList[position],
            avatarClickListener,
            avatarClickListener2
        )
    }

    fun addItems(list: List<pastTranslit>?) {
        tranList.clear()
        if (list != null) {
            tranList.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun addItem(translit: pastTranslit) {
        tranList.add(0, translit)
        notifyItemInserted(0)
    }

    fun deleteFromDb(dao: Dao?, position: Int) {
        if (dao?.getTranlslits()?.contains(tranList[position])!!) {
            dao?.deleteTranslit(tranList[position])
        }
    }

    fun delete(position: Int) {
        if (position < tranList.size) {
            tranList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class tranViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val kirilTextView = itemView.kirilTextView
        private val latinTextView = itemView.latinTextView
        private val deleteLinearLayout = itemView.deleteLinearLayout
        private val toggle = itemView.toggle

        fun bind(
            translit: pastTranslit,
            avatarClickListener: (position: Int) -> Unit,
            avatarClickListener2: (position: Int) -> Unit
        ) {
            latinTextView.text = translit.latinText
            kirilTextView.text = translit.kirilText

            deleteLinearLayout.setOnLongClickListener {
                avatarClickListener(adapterPosition)
                true
            }
            toggle.setOnClickListener {
                avatarClickListener2(adapterPosition)
            }
        }

    }
}

