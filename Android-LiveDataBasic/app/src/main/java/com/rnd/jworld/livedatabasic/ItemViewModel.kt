package com.rnd.jworld.livedatabasic

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rnd.jworld.livedatabasic.db.DBhelper
import kotlin.collections.ArrayList

class ItemViewModel(application:Application) : ViewModel() {

//    val itemList : MutableLiveData<List<Item>> by lazy{
//        MutableLiveData<List<Item>>()
//    }

    var dbHelper : DBhelper
    var itemList : MutableLiveData<List<Item>>

    init {
        itemList = MutableLiveData<List<Item>>()
        dbHelper = DBhelper(application,null)
        loadItems()
    }
//
//    val dbHelper : DBhelper by lazy{
//        DBhelper(application,null)
//    }

    fun getItems(): MutableLiveData<List<Item>> {
        return itemList
    }

    fun loadItems(){
        itemList.value = dbHelper.loadItems()
    }

    fun addItem(title:String,date:Long){

        // item 추가
        val addedId = dbHelper.addItem(title,date)

        // 기존 data 가져옴
        val oldItems = itemList.value
        val clonedItems = ArrayList<Item>()

        for ((index, value) in oldItems!!.withIndex()) {
            clonedItems.add(value)
        }

        clonedItems.add(Item(addedId,title,date))
        itemList.value = clonedItems

    }

    fun removeItem(id:Long){
        dbHelper.removeItem(id)

        // 기존 data 가져옴
        val oldItems = itemList.value
        val clonedItems = ArrayList<Item>()

        for ((index, value) in oldItems!!.withIndex()) {
            clonedItems.add(value)
        }
        var ckIndex = -1
        for ((index, value) in clonedItems!!.withIndex()) {
            val item = clonedItems[index]
            if(item.id == id){
                ckIndex = index;
            }
            clonedItems.add(value)
        }

        if(ckIndex != -1){
            clonedItems.removeAt(ckIndex)
        }
        itemList.value = clonedItems

    }

//    class Factory(val application: Application) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return ItemViewModel(application) as T
//        }
//    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ItemViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}