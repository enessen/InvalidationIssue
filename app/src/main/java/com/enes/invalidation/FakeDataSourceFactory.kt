package com.enes.invalidation

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import kotlin.random.Random


/**
 *
 * this is  a data source. Assume this data source gets value from internet not inited fake value
 *
 */
class FakeDataSourceFactory(fakeParameter: Int) : DataSource.Factory<Int, MainViewModel.PersonViewModel>() {

    val fakeData =  mutableListOf<MainViewModel.PersonRemoteModel>()


    init {
        val random = Random(fakeParameter)
        for (i in 1..100) {
            fakeData.add(MainViewModel.PersonRemoteModel(random.nextInt(fakeParameter), "FirstName$i", "LastName$i", i))
        }
    }



    override fun create(): DataSource<Int, MainViewModel.PersonViewModel> {
        return object : ItemKeyedDataSource<Int, MainViewModel.PersonViewModel>(){
            override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<MainViewModel.PersonViewModel>) {
                //I am mapping my remote object to view object. So It creates a new object. I know this is not the place to do here but
                //I am not fully using MVVM clean architecture for this example, I should have done this on a task, I know...
                callback.onResult(fakeData.map {
                    MainViewModel.PersonViewModel(it.age,it.firstName+" "+it.lastName,it.identifier)
                })
            }

            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<MainViewModel.PersonViewModel>) {
                //No Need
                callback.onResult(emptyList())
            }

            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<MainViewModel.PersonViewModel>) {
                //No Need
            }

            override fun getKey(item: MainViewModel.PersonViewModel)= item.identifier

        }
    }

}