package com.rnd.jworld.livedatabasic.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rnd.jworld.livedatabasic.Item
import java.util.*

class DBhelper(context: Context, factory:SQLiteDatabase.CursorFactory?)
    : SQLiteOpenHelper(context,DBsetting.DATABASE_NAME,factory,DBsetting.DATABASE_VERSION) {

    //DB 테이블 생성
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = "CREATE TABLE " + DBsetting.DBEntry.TABLE_NAME + " ( " +
                DBsetting.DBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBsetting.DBEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                DBsetting.DBEntry.COLUMN_DATE + " INTEGER NOT NULL);"
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }
    // DB VERSION 수정될 경우 UPGRADE
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + DBsetting.DBEntry.TABLE_NAME)
        onCreate(db)
    }

    // ContentValues : DB에 데이터를 넣기위한 매개체가 된다
    // ContentValues : ContentResolver가 데이터를 처리하는 수단
   fun addItem(title:String, date:Long) : Long{
        val values = ContentValues()
        values.put(DBsetting.DBEntry.COLUMN_TITLE, title)
        values.put(DBsetting.DBEntry.COLUMN_DATE, date)
        val db = this.writableDatabase
        val id = db.insert(DBsetting.DBEntry.TABLE_NAME, null, values)
        db.close()

        // 열 추가, PrimaryKey (ID)를 리턴
        return id
    }

    fun removeItem(id:Long) : Boolean{
        val db = this.writableDatabase
        val isDeleted = db.delete(DBsetting.DBEntry.TABLE_NAME, DBsetting.DBEntry._ID + "=?", arrayOf(id.toString())).toLong()
        db.close()
        // 정상적으로 삭제되면 리턴 true
        return Integer.parseInt("$isDeleted") != -1
    }

    fun loadItems() : List<Item>{
        val itemList = ArrayList<Item>()
        val db = this.writableDatabase
        // Cursor : 테이블의 행을 하나하나 이동하며 참조하는듯 Data를 처리
        val cursor = db.query(
            DBsetting.DBEntry.TABLE_NAME, arrayOf(
                DBsetting.DBEntry._ID,
                DBsetting.DBEntry.COLUMN_TITLE,
                DBsetting.DBEntry.COLUMN_DATE
            ),
            null, null, null, null, null
        )
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(DBsetting.DBEntry._ID))
            val title = cursor.getString(cursor.getColumnIndex(DBsetting.DBEntry.COLUMN_TITLE))
            val date = cursor.getLong(cursor.getColumnIndex(DBsetting.DBEntry.COLUMN_DATE))
            val item = Item(id,title,date)

            itemList.add(item)
        }

        cursor.close()
        db.close()
        return itemList
    }


}