package com.example.mwang.procastinator.utils;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mwang.procastinator.dao.AuthorizationDao;
import com.example.mwang.procastinator.models.access.Authorization;
import com.example.mwang.procastinator.models.access.User;

@Database(entities = {Authorization.class,User.class},version = 1)
public abstract class ProcastinatorRoomDatabase extends RoomDatabase {

    public abstract AuthorizationDao authorizationDao();



    private static ProcastinatorRoomDatabase INSTANCE;
    public static ProcastinatorRoomDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (ProcastinatorRoomDatabase.class){
                if (INSTANCE==null){
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(),
                    ProcastinatorRoomDatabase.class,"procastinator_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
