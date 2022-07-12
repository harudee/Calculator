package com.cos.calculator;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cos.calculator.dao.HistoryDAO;
import com.cos.calculator.model.History;

@Database(entities = {History.class}, version = 1)
public abstract class HistoryDB extends RoomDatabase {

    private static HistoryDB INSTANCE = null;

    public abstract HistoryDAO historyDAO();

    public static HistoryDB getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HistoryDB.class, "history_table.db").build();
        }

        return INSTANCE;
    }

    public static void destroyInstance(){ INSTANCE = null; }
}
