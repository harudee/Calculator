package com.cos.calculator;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cos.calculator.dao.HistoryDAO;
import com.cos.calculator.model.History;


@Database(entities = {History.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDAO historyDAO();

}
