package com.example.mwang.procastinator.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.mwang.procastinator.models.Event;
import com.example.mwang.procastinator.models.access.Authorization;
import com.example.mwang.procastinator.repositories.AuthorizationRepository;
import com.example.mwang.procastinator.repositories.EventRepository;
import com.example.mwang.procastinator.utils.NetworkResponse;

import java.util.List;

public class EventViewModel  extends AndroidViewModel {
    private EventRepository eventRepository;
    private AuthorizationRepository authorizationRepository;
    public MutableLiveData<NetworkResponse> monitor;
    public LiveData<List<Event>> inCompleteEventsList;
    public LiveData<List<Event>> completeEventsList;
    public LiveData<Authorization> mAuth;
    public EventViewModel(@NonNull Application application) {
        super(application);

        eventRepository=new EventRepository(application);
        authorizationRepository=new AuthorizationRepository(application);
        inCompleteEventsList=eventRepository.allInCompleteEvents();
        completeEventsList=eventRepository.allCompleteEvents();
        mAuth=authorizationRepository.getAuth();
    }

    public void getEventsOnline(String token){
        eventRepository.getEventsOnline(token);

    }
}
