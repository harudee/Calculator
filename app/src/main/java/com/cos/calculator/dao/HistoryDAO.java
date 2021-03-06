package com.cos.calculator.dao;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import com.cos.calculator.model.History;
import java.util.List;

@Dao
public interface HistoryDAO {

    @Query("SELECT * FROM history_table")
    List<History> getAll();

    /*@Query("SELECT * FROM history_table WHERE result LIKE :result LIMIT 1")
    History findByResult(String result);*/

    @Query("DELETE FROM history_table")
    void deleteAll();

    @Insert
    void insertAll(History... histories);

    @Delete
    void delete(History history);

}

