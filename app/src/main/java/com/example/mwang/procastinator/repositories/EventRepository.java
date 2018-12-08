package com.example.mwang.procastinator.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.mwang.procastinator.dao.EventsDao;
import com.example.mwang.procastinator.models.Event;
import com.example.mwang.procastinator.services.EventService;
import com.example.mwang.procastinator.utils.CoreUtils;
import com.example.mwang.procastinator.utils.NetworkResponse;
import com.example.mwang.procastinator.utils.ProcastinatorRoomDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventRepository {
    EventsDao eventsDao;
   private LiveData<List<Event>> inCompleteEventsList;
   private LiveData<List<Event>> completeEventsList;
    public MutableLiveData<NetworkResponse> monitor;
    

    public EventRepository(Application application){
        ProcastinatorRoomDatabase procastinatorRoomDatabase=ProcastinatorRoomDatabase.getDatabase(application);
        eventsDao=procastinatorRoomDatabase.eventsDao();
        inCompleteEventsList=eventsDao.getAllEvents(0);
        completeEventsList=eventsDao.getAllEvents(1);
        monitor=new MutableLiveData<>();
    }
    
    public LiveData<List<Event>> allInCompleteEvents(){
        return  inCompleteEventsList;
    }
    public LiveData<List<Event>> allCompleteEvents(){
        return  completeEventsList;
    }

    public void getEventsOnline(String token) {
        Call<List<Event>> call=CoreUtils.getAuthRetrofitClient(token).create(EventService.class).getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.body()!=null){

                    for (Event event:response.body()){
                        eventsDao.insert(event);

                        Log.e("ererer",""+ event.is_complete);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });

    }
}
