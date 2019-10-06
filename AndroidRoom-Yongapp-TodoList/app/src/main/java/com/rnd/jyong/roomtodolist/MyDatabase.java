package com.rnd.jyong.roomtodolist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase INSTANCE;
    public static final String DB_NAME = "mydatabase";
    public static final String TABLE_NAME_TODO = "todo";

    public abstract TodoDao todoDao();

    public static MyDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, MyDatabase.DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }

}
