package com.example.mwang.procastinator.services;

import com.example.mwang.procastinator.models.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventService {

    @GET("all_events")
    Call<List<Event>> getAllEvents();

    @GET("toggle_event/{event}")
    Call<Event> toggleEventStatus(@Path("event") int event_id);


    @POST("new_event")
    Call<JSONObject> updateEvents(@Body JSONArray jsonArray);

    @GET("delete_event/{event}")
    Call<List<Event>> deleteEvent(@Path("event") int event);
}
