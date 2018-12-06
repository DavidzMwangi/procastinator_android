package com.example.mwang.procastinator.services;

import com.example.mwang.procastinator.models.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EventService {

    @GET("all_events")
    Call<List<Event>> getAllEvents();
}
