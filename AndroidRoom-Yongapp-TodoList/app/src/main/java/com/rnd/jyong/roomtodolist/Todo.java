package com.rnd.jyong.roomtodolist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = MyDatabase.TABLE_NAME_TODO)
public class Todo {

    @PrimaryKey(autoGenerate = true)
    public int todo_id;

    public String name;

    public String description;

    @ColumnInfo(name = "category")
    public String category;

    @Ignore
    public String priority;


}
