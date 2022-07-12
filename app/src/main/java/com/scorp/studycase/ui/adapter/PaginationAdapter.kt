package com.scorp.studycase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scorp.studycase.data.Person
import com.scorp.studycase.databinding.ItemListBinding
import javax.inject.Inject

class PaginationAdapter @Inject constructor() :
    RecyclerView.Adapter<PaginationAdapter.PeopleViewHolder>() {
    private var persons = ArrayList<Person>()

    fun setListData(newPersons: MutableList<Person>) {
        persons.clear()
        persons.addAll(newPersons)
        notifyDataSetChanged()

    }

    inner class PeopleViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) {
            "${person.fullName} (${person.id})".also { binding.txtPerson.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(persons[position])
    }


    override fun getItemCount(): Int {
        return persons.size
    }
}

