package com.rnd.jworld.livedatabasic.db

class DBsetting private constructor() {

    // DB 설정값
    companion object{
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "maindb.db"
    }
    // TABLE 설정값
    class DBEntry private constructor() {
        companion object{
            val _ID = "_id"
            val TABLE_NAME = "item"
            val COLUMN_TITLE = "title"
            val COLUMN_DATE = "date"
        }
    }

}