package com.enes.invalidation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PersonPagedListAdapter(private val layoutId:Int) :
        PagedListAdapter<MainViewModel.PersonViewModel, PersonPagedListAdapter.PersonViewHolder>(object :
                DiffUtil.ItemCallback<MainViewModel.PersonViewModel>() {
            override fun areItemsTheSame(oldItem: MainViewModel.PersonViewModel, newItem: MainViewModel.PersonViewModel) =
                    oldItem.identifier == newItem.identifier

            override fun areContentsTheSame(oldItem: MainViewModel.PersonViewModel, newItem: MainViewModel.PersonViewModel) =
                    oldItem.fullName == newItem.fullName && oldItem.age == newItem.age

        })
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), layoutId, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(currentList?.getOrNull(position))
    }


    class PersonViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(personViewModel: MainViewModel.PersonViewModel?) {
            personViewModel?.let {
                binding.setVariable(BR.vm,personViewModel)
            }
        }
    }
}