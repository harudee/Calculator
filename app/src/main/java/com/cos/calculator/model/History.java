package com.cos.calculator.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history_table")
public class History {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "expression")
    public String expression;

    @ColumnInfo(name = "result")
    public String result;


}
