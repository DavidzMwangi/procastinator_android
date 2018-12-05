package com.example.mwang.procastinator.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.mwang.procastinator.models.access.Authorization;

@Dao
public interface AuthorizationDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Authorization authorization);

    @Query("DELETE FROM authorization_table")
    void deleteAll();

    @Query("SELECT * from authorization_table ORDER BY id desc LIMIT 1")
    LiveData<Authorization> getAuthorization();

    @Query("SELECT * from authorization_table ORDER BY id desc LIMIT 1")
    Authorization getSavedAuthorization();
}
