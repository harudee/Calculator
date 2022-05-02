package com.cos.calculator.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history_table")
public class History {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "expression")
    public String expression;

    @ColumnInfo(name = "answer")
    public String answer;

}
