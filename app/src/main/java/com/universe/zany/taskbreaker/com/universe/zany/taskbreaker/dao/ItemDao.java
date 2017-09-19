package com.universe.zany.taskbreaker.com.universe.zany.taskbreaker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.universe.zany.taskbreaker.core.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItems(Item... items);

    @Query("SELECT * FROM Items WHERE task_id = :taskId")
    LiveData<List<Item>> getItemByTaskId(int taskId);

    @Query("SELECT * FROM Items WHERE id = :itemId")
    LiveData<Item> getItemById(int itemId);

    @Update
    void updateItems(Item... items);

    @Delete
    void deleteItems(Item... items);
}
