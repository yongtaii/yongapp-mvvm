package com.rnd.jworld.livedatabasic.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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


}