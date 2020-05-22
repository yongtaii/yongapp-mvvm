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

        // DB에서 제거
        val isDelteedInDB = dbHelper.removeItem(id)

        // DB에서 제거 안되었으면 return
        if(!isDelteedInDB) return

        // 기존 데이터 가져옴
        val oldItems = itemList.value
        val clonedItems = ArrayList<Item>()

        // 기존 리스트를 clonedItems로 복사
        for (i in oldItems!!.indices) {
            clonedItems.add(oldItems.get(i))
        }

        var ckIndex = -1
        // 리스트에서 해당 ID를 찾아 삭제
        for (i in clonedItems.indices) {
            val item = clonedItems.get(i)
            if(item.id == id){
                ckIndex = i;
//                clonedItems.removeAt(i)
            }
        }

//        for ((index, value) in oldItems!!.withIndex()) {
//            clonedItems.add(value)
//        }

//        for ((index, value) in clonedItems!!.withIndex()) {
//            val item = clonedItems[index]
//            if(item.id == id){
//                ckIndex = index;
//                clonedItems.remove(value)
//            }
//            clonedItems.add(value)
//        }


//        for (i in clonedItems.indices) {
//            val item = clonedItems.get(i)
//            if(item.id == id){
////                ckIndex = index;
//                clonedItems.removeAt(i)
//            }
//        }

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