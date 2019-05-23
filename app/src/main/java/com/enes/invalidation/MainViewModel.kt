package com.enes.invalidation

import androidx.databinding.BaseObservable
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class MainViewModel :ViewModel(){


    val liveDataPagedList = MediatorLiveData<PagedList<PersonViewModel>>()


    /**
     * this method gets an age upper limit, assume that, this is a parameter for the internet service call
     *
     * @param fakeParameter  is max age for api call ;)
     */
    fun fetchData(fakeParameter:Int) {

        val fakeFactory = FakeDataSourceFactory(fakeParameter)

        val paged = LivePagedListBuilder<Int, PersonViewModel>(fakeFactory, 10).build()


        liveDataPagedList.addSource(paged){
            liveDataPagedList.value = it
        }
    }


    /**
     * Assume that, this class is a view class for a data from Internet Service, It is baseObservable because I don't
     * want to use liveData here because recyclerView does not have a lifeCycle
     */
    data class PersonViewModel(var age:Int, val fullName:String, val identifier:Int):BaseObservable(){
        fun getAgeString() = "is $age years old"
    }

    data class PersonRemoteModel(val age:Int, val firstName:String, val lastName:String, val identifier: Int)


}