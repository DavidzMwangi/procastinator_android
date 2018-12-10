package com.example.mwang.procastinator.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;


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

    @ColumnInfo(name = "description")
    @SerializedName("description")
    public String description;

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


    @ColumnInfo(name = "is_synced")
    @SerializedName("is_synced")
    public int is_synced;

    @ColumnInfo(name = "has_update")
    @SerializedName("has_update")
    public int has_update;


    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }


    public JSONObject getJsonObject() throws JSONException {
        Gson gson=new Gson();
        return new JSONObject(gson.toJson(this));
    }

}
