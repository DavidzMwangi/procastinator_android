package com.example.mwang.procastinator.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.mwang.procastinator.models.Event;

import java.util.List;

@Dao
public interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);

    @Query("DELETE FROM events_table")
    void deleteAll();

    @Query("SELECT * from events_table where is_complete = :isComplete")
    LiveData<List<Event>> getAllEvents(int isComplete);




}
