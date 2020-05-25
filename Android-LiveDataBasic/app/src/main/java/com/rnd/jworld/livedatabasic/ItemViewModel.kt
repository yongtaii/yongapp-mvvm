package com.rnd.jworld.livedatabasic

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rnd.jworld.livedatabasic.db.DBhelper
import kotlin.collections.ArrayList

class ItemViewModel(application:Application) : ViewModel() {

    var dbHelper : DBhelper
    // LiveData vs MutableLiveData
    // 일반적인 LiveData형은 변경할 수 없고 오로지 데이터의 변경값만을 소비하는데 반해
    // MutableLiveData는 데이터를 UI Thread와 Background Thread에서 선택적으로 바꿀 수 있습니다.
    var itemList : MutableLiveData<List<Item>>

    // setValue : Main Thread 에서 실행
    // postValue : Background Thread에서 실행

    init {
        itemList = MutableLiveData<List<Item>>()
        dbHelper = DBhelper(application,null)
        loadItems()
    }

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

        if(ckIndex != -1){
            clonedItems.removeAt(ckIndex)
        }
        itemList.value = clonedItems

    }

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