package com.rnd.jworld.livedatabasic

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel(application:Application) : ViewModel() {

    val items : MutableLiveData<List<Item>> by lazy{
        MutableLiveData<List<Item>>()
    }

    fun loadItems(){

    }

    fun addItem(title:String,data:Long){

    }

    fun removeItem(id:Long){

    }

}