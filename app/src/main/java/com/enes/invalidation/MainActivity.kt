package com.enes.invalidation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.enes.invalidation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var binding : ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.fetchData(50)
        }


        val pagedListAdapter = PersonPagedListAdapter(R.layout.item_person)



        viewModel.liveDataPagedList.observe(this, Observer {
            pagedListAdapter.submitList(it)
            binding.srRefresh.isRefreshing = false
        })


        binding.btIncrease.setOnClickListener {
            pagedListAdapter.currentList?.forEach {
                it.age += 1
                it.notifyChange()
            }
        }



        binding.srRefresh.setOnRefreshListener {
            pagedListAdapter.currentList?.dataSource?.invalidate() ?: run {
                binding.srRefresh.isRefreshing = false
            }
        }

        binding.rvData.adapter = pagedListAdapter

    }


}
