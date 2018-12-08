package com.example.mwang.procastinator.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;



@Entity(tableName = "events_table")
public class Event {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    public String name;



    @ColumnInfo(name = "event_date")
    @SerializedName("event_date")
    public String event_date;



    @ColumnInfo(name = "event_time")
    @SerializedName("event_time")
    public String event_time;


    @ColumnInfo(name = "reminder_date")
    @SerializedName("reminder_date")
    public String reminder_date;


    @ColumnInfo(name = "reminder_time")
    @SerializedName("reminder_time")
    public String reminder_time;


    @ColumnInfo(name = "is_complete")
    @SerializedName("is_complete")
    public int is_complete;


    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
